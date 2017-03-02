package gestion.data.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.Set;

import javax.swing.JOptionPane;

import gestion.compta.Compte;
import gestion.compta.Placement;
import gestion.compta.Transaction;
import gestion.data.DAOtableau;
import gestion.data.DataCenter;

public class TransacDAO extends DAOtableau<Transaction> {
	
	public TransacDAO(DataCenter instance){
		super(instance);
	}

	@Override
	public boolean create(Transaction obj){
		try{
			String query="INSERT INTO transactions(id_placement,id_compte,transac_date,coursunit,adduc,dimuc,addeur,dimeur)";
			query+="VALUES(?,?,?,?,?,?,?,?)";
			PreparedStatement state = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
			state.setInt(1, obj.getPlace().getIndex());
			state.setInt(2, obj.getCompte().getIndex());
			state.setDate(3, obj.getDate());
			state.setFloat(4, obj.getCoursUnit());
			state.setFloat(5, obj.getAddUC());
			state.setFloat(6, obj.getDimUC());
			state.setFloat(7, obj.getAddEUR());
			state.setFloat(8, obj.getDimEUR());
			int nb_rows = state.executeUpdate();
			
			// MàJ de id_trans (qui doit être à 0 jusque là)
			ResultSet genKey = state.getGeneratedKeys();
			if (genKey.next()){
				obj.setIdTrans(genKey.getInt(1));
			};
			state.close();
			return (nb_rows !=0);
		} catch (SQLException e){
			JOptionPane jop = new JOptionPane();
			jop.showMessageDialog(null, e.getMessage(),"ERREUR dans TransactionDAO.create !",JOptionPane.ERROR_MESSAGE);
			return false;
		} catch (Exception e){
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public boolean update(Transaction obj) {
		try{
			String query="UPDATE transactions SET id_placement = ?, "
					+ "id_compte = ?,"
					+ "transac_date = ?,"
					+ "coursunit = ?,"
					+ "adduc = ?,"
					+ "dimuc = ?,"
					+ "addeur = ?,"
					+ "dimeur = ?"
					+ " WHERE id_transac = ?";
			PreparedStatement state = conn.prepareStatement(query);
			state.setInt(1, obj.getPlace().getIndex());
			state.setInt(2, obj.getCompte().getIndex());
			state.setDate(3, obj.getDate());
			state.setFloat(4, obj.getCoursUnit());
			state.setFloat(5, obj.getAddUC());
			state.setFloat(6, obj.getDimUC());
			state.setFloat(7, obj.getAddEUR());
			state.setFloat(8, obj.getDimEUR());
			state.setFloat(9, obj.getIdTrans());
			int nb_rows = state.executeUpdate();
			state.close();
			return true;
		} catch (SQLException e){
			JOptionPane jop = new JOptionPane();
			jop.showMessageDialog(null, e.getMessage(),"ERREUR dans TransactionDAO.update !",JOptionPane.ERROR_MESSAGE);
			return false;
		} catch (Exception e){
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public boolean delete(Transaction obj) {
		try{
			String query="DELETE FROM transactions WHERE id_transac = ?";
			PreparedStatement state = conn.prepareStatement(query);
			state.setInt(1, obj.getIdTrans());
			int nb_rows = state.executeUpdate();
			System.out.println("Suppression de "+nb_rows+" ligne(s)");
			state.close();
			return true;
		} catch (SQLException e){
			JOptionPane jop = new JOptionPane();
			jop.showMessageDialog(null, e.getMessage(),"ERREUR dans TransactionDAO.delete !",JOptionPane.ERROR_MESSAGE);
			return false;
		} catch (Exception e){
			e.printStackTrace();
			return false;
		}	
	}

	@Override
	public Transaction find(int index) {
		// TODO Auto-generated method stub
		try{
			String query="SELECT id_placement,id_compte,transac_date,coursunit,adduc,dimuc,addeur,dimeur"
					+ " FROM transactions WHERE id_transac = ? ORDER BY transac_date ";
			PreparedStatement state = conn.prepareStatement(query,ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			state.setInt(1, index);
			ResultSet res = state.executeQuery();
			res.first();
			Placement place = DataCenter.getPlacementDAO().find(res.getInt("id_placement"));
			Compte compte = DataCenter.getCompteDAO().find(res.getInt("id_compte"));
			Transaction transac = new Transaction(
					res.getDate("transac_date"),
					place,
					compte,
					res.getFloat("coursunit"),
					res.getFloat("adduc"),
					res.getFloat("dimuc"),
					res.getFloat("addeur"),
					res.getFloat("dimeur"));
			transac.setIdTrans(index);
			res.close();
			state.close();
			return transac;
		} catch (SQLException e){
			e.printStackTrace();
			JOptionPane jop = new JOptionPane();
			jop.showMessageDialog(null, e.getMessage(),"ERREUR dans find - TransactionDAO!",JOptionPane.ERROR_MESSAGE);
			return null;
		} catch (Exception e){
			e.printStackTrace();
			return null;
		}	
	}
	
	// Une transaction pleinement initialisée, avec id_trans par défaut (=0).
	public Transaction newElement(){
		Transaction transac = Transaction.defaultEntry();
		this.create(transac);
		return transac;
	}

	// Attention ! cette méthode est relative à "placeCourant"
	@Override
	public LinkedList<Transaction> getData() {
		LinkedList<Transaction> data = new LinkedList<Transaction>();
		if (dataCenter.getPlaceCourant()==null){
			return data;
		}
		try{
			String query="SELECT id_transac, id_compte,transac_date,coursunit,adduc,dimuc,addeur,dimeur"
					+ " FROM transactions "
					+ " WHERE id_placement=? ORDER BY transac_date ;";
			PreparedStatement state = conn.prepareStatement(query);
			state.setInt(1, dataCenter.getPlaceCourant().getIndex());
			ResultSet res = state.executeQuery();
			int i = 0;
			while(res.next()){
				i++;
				Compte compte = DataCenter.getCompteDAO().find(res.getInt("id_compte"));
				Transaction transac = new Transaction(
						res.getDate("transac_date"),
						dataCenter.getPlaceCourant(),
						compte,
						res.getFloat("coursunit"),
						res.getFloat("adduc"),
						res.getFloat("dimuc"),
						res.getFloat("addeur"),
						res.getFloat("dimeur"));
				
				transac.setIdTrans(res.getInt("id_transac"));
				data.add(transac);
			}
			System.out.println("TransacDAO.getData() : "+i+" ligne(s) trouvées.");
			res.close();
			state.close();
			return data;
		} catch (SQLException e){
			e.printStackTrace();
			JOptionPane jop = new JOptionPane();
			jop.showMessageDialog(null, e.getMessage(),"ERREUR dans TransactionDAO.getAll(place)!",JOptionPane.ERROR_MESSAGE);
			return null;
		} catch (Exception e){
			e.printStackTrace();
			return null;
		}
	}
	
	
	public static LinkedList<Transaction> getAll() {
		LinkedList<Transaction> data = new LinkedList<Transaction>();
		try{
			String query="SELECT id_placement, id_transac, id_compte,transac_date,coursunit,adduc,dimuc,addeur,dimeur"
					+ " FROM transactions ORDER BY transac_date  ;";
			PreparedStatement state = conn.prepareStatement(query);
			ResultSet res = state.executeQuery();
			int i = 0;
			while(res.next()){
				i++;
				Placement place = DataCenter.getPlacementDAO().find(res.getInt("id_placement"));
				Compte compte = DataCenter.getCompteDAO().find(res.getInt("id_compte"));
				Transaction transac = new Transaction(
						res.getDate("transac_date"),
						place,
						compte,
						res.getFloat("coursunit"),
						res.getFloat("adduc"),
						res.getFloat("dimuc"),
						res.getFloat("addeur"),
						res.getFloat("dimeur"));
				
				transac.setIdTrans(res.getInt("id_transac"));
				data.add(transac);
			}
			System.out.println("TransacDAO.getAll() : "+i+" ligne(s) trouvées.");
			res.close();
			state.close();
			return data;
		} catch (SQLException e){
			e.printStackTrace();
			JOptionPane jop = new JOptionPane();
			jop.showMessageDialog(null, e.getMessage(),"ERREUR dans TransactionDAO.getAll() !",JOptionPane.ERROR_MESSAGE);
			return null;
		} catch (Exception e){
			e.printStackTrace();
			return null;
		}
	}

	public float totalUC(Placement place){
		float totalUC = 0f;
		try{
			String query="SELECT SUM(adduc - dimuc) FROM transactions WHERE id_placement = ?";
			PreparedStatement state = conn.prepareStatement(query);
			state.setInt(1, place.getIndex());
			ResultSet res = state.executeQuery();
			
			res.next();
			totalUC = res.getFloat(1);
			state.close();
			return totalUC;
		} catch (SQLException e){
			JOptionPane jop = new JOptionPane();
			jop.showMessageDialog(null, e.getMessage(),"ERREUR dans TransactionDAO.totalUC("+place.getName()+") !",JOptionPane.ERROR_MESSAGE);
			return totalUC;
		} catch (Exception e){
			e.printStackTrace();
			return totalUC;
		}
	}

	public float totalEUR(Placement place) {
		float totalEUR = 0f;
		try{
			String query="SELECT SUM(addeur - dimeur) FROM transactions WHERE id_placement = ?";
			PreparedStatement state = conn.prepareStatement(query);
			state.setInt(1, place.getIndex());
			ResultSet res = state.executeQuery();
			
			res.next();
			totalEUR = res.getFloat(1);
			state.close();
			return totalEUR;
		} catch (SQLException e){
			JOptionPane jop = new JOptionPane();
			jop.showMessageDialog(null, e.getMessage(),"ERREUR dans TransactionDAO.totalEUR("+place.getName()+") !",JOptionPane.ERROR_MESSAGE);
			return totalEUR;
		} catch (Exception e){
			e.printStackTrace();
			return totalEUR;
		}
	}
}
