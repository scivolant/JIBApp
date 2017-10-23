package gestion.sqliteImport;


import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Hashtable;
import java.util.Map;
import java.util.Set;

import org.apache.commons.math3.fraction.BigFractionFormat;

import gestion.compta.Compte;
import gestion.compta.Cours;
import gestion.compta.GestionType;
import gestion.compta.Placement;
import gestion.compta.SourceQuote;
import gestion.compta.Transaction;
import gestion.data.DataCenter;

public class BasicImportReader extends AbstractImportReader {

	
	/**
	 * Simplest import reader possible: 
	 * creates new Comptes, SourceQuotes, Placements, Transactions, Cours 
	 * without checking whether they already exist in the database.
	 * 
	 * Based on http://www.sqlitetutorial.net/sqlite-java/sqlite-jdbc-driver/
	 */

	@Override
	public void importReader(String path) {
		Connection conn = null;

        try {
            // db parameters
            String url = "jdbc:sqlite:"+path;
            // create a connection to the database
            conn = DriverManager.getConnection(url);
            
            Map<String,Compte> mapCompte = createComptes(conn);
            
            Map<String,Placement> mapPlacement = createPlacements(conn);
            
            createTransactions(conn, mapCompte, mapPlacement);
            
            createCours(conn, mapPlacement);
            
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException e) {
    			e.printStackTrace();
            }
        }
        
	}

	public String toString(){
		return "BasicImportReader";
	}
	
	private Map<String,Compte> createComptes(Connection conn) {
		/**
		 * A method that 
		 * 	1. creates Comptes directly in the database;
		 *  2. returns a map from the id (as in kmmAccounts) to the (updated) Compte object.
		 * 
		 * NB:
		 * _ It does not check whether these Comptes already exist.
		 * _ Yields a Map from the id of the parent account to its representation in the DB.
		 */
		Map<String,Compte> mapCompte = new Hashtable<String,Compte>();
		
        // recovers all "comptes":
        String query0 = "(select distinct(parentId) as idParent from kmmAccounts where isStockAccount = 'Y') as a";
		String query="select idParent,  b.accountName from "
				+ query0 +" , kmmAccounts as b where b.id = idParent";
		try {
			PreparedStatement state = conn.prepareStatement(query);
			ResultSet res = state.executeQuery();
			while(res.next()){
				// create a new entry in mapCompte
				mapCompte.put(res.getString("idParent"), initializeCompte(res.getString("accountName")));
				// NB: in initializeCompte, a new Compte is created in the permanent DB assoc. to JIBApp.
			}
			state.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return mapCompte;
	}
	
	private Compte initializeCompte(String name) {
		/**
		 * Creates a new Compte, includes it in the (permanent) database of JIBApp,
		 * updating id_compte (via CompteDAO().create),
		 * and returns the updated Compte.
		 */
		Compte compte = new Compte(name);
		DataCenter.getCompteDAO().create(compte);
		return compte;
	}
	
	private Map<String,Placement> createPlacements(Connection conn) {
		/**
		 * A method that 
		 * 	1. creates Placements directly in the database;
		 *  2. returns a map from the id (as in kmmSecurities) to the (updated) Placement object.
		 * 
		 * NB:
		 * _ It does not check whether these Placements already exist.
		 * _ Yields a Map from the id (as in kmmSecurities, E00....) 
		 *   to its representation in the DB.
		 */
		Map<String,Placement> mapPlacement = new Hashtable<String,Placement>();
		
        // Create all "comptes":
       String query="select id, name, symbol, type from kmmSecurities";
		try {
			PreparedStatement state = conn.prepareStatement(query);
			ResultSet res = state.executeQuery();
			while(res.next()){
				// create a new entry in mapPlacement
				mapPlacement.put(res.getString("id"), initializePlacement(
						res.getString("name"),
						res.getString("symbol"),
						res.getInt("type")
						));
				// NB: in initializePlacement, a new Placement is created in the permanent DB assoc. to JIBApp.
			}
			state.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return mapPlacement;
	}
	
	private Placement initializePlacement(String name, String symbol, int typeInt) {
		/**
		 * Creates a new Placement, includes it in the (permanent) database of JIBApp,
		 * updating id_placement (via PlacementDAO().create),
		 * and returns the updated Placement.
		 */
		GestionType type;
		SourceQuote sq;
		
		// pour retrouver la source : parse de la forme de symbol...
		if (symbol.matches("(\\w+):(\\w+):(\\w+)")) {
			// si xxx:yyy:zzz, alors il s'agit d'un ETF, typeInt = 3
			typeInt = 3;
			// et par ailleurs sourceQuotes est "FT ETF"
			sq = DataCenter.getSourceQuoteDAO().find(1);
		} else {
			if (symbol.matches("(\\w+):EU")) {
				// il s'agit d'un OPCVM mis à jour grâce à "FT ordinaire"
				sq = DataCenter.getSourceQuoteDAO().find(2);
			} else {
				if (symbol.matches("(\\w+).PA")) {
					// il s'agit d'un OPCVM mis à jour grâce à "Yahoo"
					sq = DataCenter.getSourceQuoteDAO().find(3);
				} else {
					// pas d'idée particulière :
					sq = DataCenter.getSourceQuoteDAO().find(4);
				}
			}
		}
		
		switch(typeInt) {
			case 0 :
				// case of "Stock" in kmmSecurities
				type = DataCenter.getGestionTypesDAO().find(5);
				break;
			case 1 :
				// case of "Mutual Fund" in kmmSecurities
				type = DataCenter.getGestionTypesDAO().find(2);
				break;
			case 2 :
				// case of "Bond" in kmmSecurities
				type = DataCenter.getGestionTypesDAO().find(3);
				break;
			case 3 :
				// case of a symbol of the form "(\\w+):(\\w+):(\\w+)"
				type = DataCenter.getGestionTypesDAO().find(1);
			default :
				// should not happen...
				type = DataCenter.getGestionTypesDAO().find(2);
				break;
		}
		
		Placement place = new Placement(name,
							symbol,
							type,
							symbol,
							symbol,
							sq
							);
		DataCenter.getPlacementDAO().create(place);
		return place;
	}
	
	private void createTransactions(Connection conn, Map<String,Compte> mapCompte, Map<String,Placement> mapPlacement) {
		/**
		 * A method that creates Transactions directly in the database;
		 * 
		 * NB:
		 * _ It does not check whether these Transactions already exist.
		 * _ Uses the maps mapCompte and mapPlacement to identify the relevant objects.
		 */
		
        // Create all "comptes":
       String query="select a.currencyId as id_placement, b.parentId as id_compte,"
       		+ " balDate as transac_date, shares, value from kmmBalances as a,"
       		+ " kmmAccounts as b where b.id = a.id and b.isStockAccount = 'Y'";
		try {
			PreparedStatement state = conn.prepareStatement(query);
			ResultSet res = state.executeQuery();
			while(res.next()){
				Float shareFloat, valueFloat;
				try {
					BigFractionFormat ff = new BigFractionFormat();
					shareFloat = ff.parse(res.getString("shares")).floatValue();
					valueFloat = ff.parse(res.getString("value")).floatValue();
				} catch (Exception e) {
					System.out.println("BasicImportReader.createTransactions -- pb dans l'éval. shares et value");
					System.out.println("shares = "+res.getString("shares"));
					System.out.println("value = "+res.getString("value"));
					shareFloat = 0f;
					valueFloat = 0f;
				}

				Float coursUnit = 0f;
				try {
					coursUnit = valueFloat/shareFloat;
				} catch (ArithmeticException e) {
					System.out.println("BasicImportReader.createTransactions -- division par 0 !");
				}
				
				// computation of addEur and dimEur
				Float addEur, dimEur;
				if (valueFloat >= 0) {
					addEur = valueFloat;
					dimEur = 0f;
				} else {
					addEur = 0f;
					dimEur = -valueFloat;					
				}
				
				// computation of addUC and dimUC
				Float addUC, dimUC;
				if (shareFloat >= 0) {
					addUC = shareFloat;
					dimUC = 0f;
				} else {
					addUC = 0f;
					dimUC = -shareFloat;					
				}
				
				Transaction transac = new Transaction(
						Date.valueOf(res.getString("transac_date")),
						mapPlacement.get(res.getString("id_placement")),
						mapCompte.get(res.getString("id_compte")),
						coursUnit,
						addUC,
						dimUC,
						addEur,
						dimEur
						);
				
				DataCenter.getTransacDAO().create(transac);
			}
			state.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	private void createCours(Connection conn, Map<String,Placement> mapPlacement) {
		/**
		 * A method that creates Cours directly in the database;
		 * 
		 * NB:
		 * _ It does not check whether these Cours already exist.
		 * _ Uses mapPlacement to identify the relevant Placements.
		 */

		Set<String> setIdPlacement = mapPlacement.keySet();

		try {
			String query="select fromId, priceDate, price from kmmPrices where fromId = ?";
			PreparedStatement state = conn.prepareStatement(query);

			for (String idPlacement : setIdPlacement) {
				Placement place = mapPlacement.get(idPlacement);

				state.setString(1,idPlacement);
				ResultSet res = state.executeQuery();

				while(res.next()){
					Float coursUnit;
					try {
						BigFractionFormat ff = new BigFractionFormat();
						coursUnit = ff.parse(res.getString("price")).floatValue();
					} catch (Exception e) {
						System.out.println("BasicImportReader.createCours -- pb dans l'éval. price");
						coursUnit = 0f;
					}
					Cours cours = new Cours(
							Date.valueOf(res.getString("priceDate")),
							place,
							coursUnit
							);
					DataCenter.getCoursDAO().create(cours);
				}		
			}
			state.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
