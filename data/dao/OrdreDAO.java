package gestion.data.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.Set;

import javax.swing.JOptionPane;

import gestion.compta.Compte;
import gestion.compta.Ordre;
import gestion.compta.Placement;
import gestion.data.DAOtableau;
import gestion.data.DataCenter;

public class OrdreDAO extends DAOtableau<Ordre> {
	
	public OrdreDAO(DataCenter instance){
		super(instance);
	}
	
	/*
	public OrdreDAO(Connection conn, Placement place, Set<Compte> comptesCourants){
		super(conn, place, comptesCourants);
	}
	*/

	public boolean create(Ordre obj){
		// doit mettre à jour le id_ordre de obj !!
		try{
			String query="INSERT INTO ordres(id_placement,id_compte,coursunit,adduc,dimuc,addeur,dimeur,note)";
			query+="VALUES(?,?,?,?,?,?,?,?)";
			PreparedStatement state = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
			state.setInt(1, obj.getPlace().getIndex());
			state.setInt(2, obj.getCompte().getIndex());
			state.setFloat(3, obj.getCoursUnit());
			state.setFloat(4, obj.getAddUC());
			state.setFloat(5, obj.getDimUC());
			state.setFloat(6, obj.getAddEUR());
			state.setFloat(7, obj.getDimEUR());
			state.setString(8, obj.getNote());
			int nb_rows = state.executeUpdate();
			System.out.println("Insertion de "+nb_rows+" ligne(s)");
			
			// MàJ de id_trans (qui doit être à 0 jusque là)
			ResultSet genKey = state.getGeneratedKeys();
			if (genKey.next()){
				obj.setIdOrdre(genKey.getInt(1));
				System.out.println("OrdreDAO.create -- clée :"+genKey.getInt(1));
			};
			state.close();
			return (nb_rows !=0);
		} catch (SQLException e){
			JOptionPane jop = new JOptionPane();
			jop.showMessageDialog(null, e.getMessage(),"ERREUR dans create - OrdreDAO!",JOptionPane.ERROR_MESSAGE);
			return false;
		} catch (Exception e){
			e.printStackTrace();
			return false;
		}
	};
	
	public boolean update(Ordre obj){
		try{
			String query="UPDATE ordres SET id_placement = ?, "
					+ "id_compte = ?,"
					+ "coursunit = ?,"
					+ "adduc = ?,"
					+ "dimuc = ?,"
					+ "addeur = ?,"
					+ "dimeur = ?,"
					+ "note = ?"
					+ " WHERE id_ordre = ?";
			PreparedStatement state = conn.prepareStatement(query);
			state.setInt(1, obj.getPlace().getIndex());
			state.setInt(2, obj.getCompte().getIndex());
			state.setFloat(3, obj.getCoursUnit());
			state.setFloat(4, obj.getAddUC());
			state.setFloat(5, obj.getDimUC());
			state.setFloat(6, obj.getAddEUR());
			state.setFloat(7, obj.getDimEUR());
			state.setString(8, obj.getNote());
			state.setFloat(9, obj.getIdOrdre());
			int nb_rows = state.executeUpdate();
			state.close();
			return true;
		} catch (SQLException e){
			JOptionPane jop = new JOptionPane();
			jop.showMessageDialog(null, e.getMessage(),"ERREUR dans update - OrdreDAO!",JOptionPane.ERROR_MESSAGE);
			return false;
		} catch (Exception e){
			e.printStackTrace();
			return false;
		}
	};
	
	public boolean delete(Ordre obj){
		try{
			String query="DELETE FROM ordres WHERE id_ordre = ?";
			PreparedStatement state = conn.prepareStatement(query);
			state.setInt(1, obj.getIdOrdre());
			int nb_rows = state.executeUpdate();
			System.out.println("Suppression de "+nb_rows+" ligne(s)");
			state.close();
			return true;
		} catch (SQLException e){
			JOptionPane jop = new JOptionPane();
			jop.showMessageDialog(null, e.getMessage(),"ERREUR dans delete - OrdreDAO!",JOptionPane.ERROR_MESSAGE);
			return false;
		} catch (Exception e){
			e.printStackTrace();
			return false;
		}	
	};
	
	public Ordre find(int index){
		try{
			String query="SELECT id_placement,id_compte,coursunit,adduc,dimuc,addeur,dimeur,note"
					+ " FROM transactions WHERE id_ordre = ? ORDER BY coursunit ";
			PreparedStatement state = conn.prepareStatement(query,ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			state.setInt(1, index);
			ResultSet res = state.executeQuery();
			res.first();
			Placement place = DataCenter.getPlacementDAO().find(res.getInt("id_placement"));
			Compte compte = DataCenter.getCompteDAO().find(res.getInt("id_compte"));
			Ordre ordre = new Ordre(
					place,
					compte,
					res.getFloat("coursunit"),
					res.getFloat("adduc"),
					res.getFloat("dimuc"),
					res.getFloat("addeur"),
					res.getFloat("dimeur"),
					res.getString("note"));
			ordre.setIdOrdre(index);
			int i = 1;
			while(res.next())
				i++;
			res.close();
			state.close();
			return ordre;
		} catch (SQLException e){
			e.printStackTrace();
			JOptionPane jop = new JOptionPane();
			jop.showMessageDialog(null, e.getMessage(),"ERREUR dans find - OrdreDAO !",JOptionPane.ERROR_MESSAGE);
			return null;
		} catch (Exception e){
			e.printStackTrace();
			return null;
		}
	};

	/*
	public LinkedList<Ordre> getAll(){
		LinkedList<Ordre> data = new LinkedList<Ordre>();
		try{
			String query="SELECT id_ordre, id_placement,id_compte,"
					+ "coursunit,adduc,dimuc,addeur,dimeur,note"
					+ " FROM ordres;";
			PreparedStatement state = conn.prepareStatement(query);
			ResultSet res = state.executeQuery();
			int i = 0;
			while(res.next()){
				i++;
				Placement place = DataSQL.getPlacementDAO().find(res.getInt("id_placement"));
				Compte compte = DataSQL.getCompteDAO().find(res.getInt("id_compte"));
				Ordre ordre = new Ordre(
						place,
						compte,
						res.getFloat("coursunit"),
						res.getFloat("adduc"),
						res.getFloat("dimuc"),
						res.getFloat("addeur"),
						res.getFloat("dimeur"),
						res.getString("note"));
				// update of id_ordre, according to the database.
				ordre.setIdOrdre(res.getInt("id_ordre"));
				data.add(ordre);
			}
			System.out.println("OrdreDAO.getAll() : "+i+" ligne(s) trouvées.");
			res.close();
			state.close();
			return data;
		} catch (SQLException e){
			JOptionPane jop = new JOptionPane();
			jop.showMessageDialog(null, e.getMessage(),"ERREUR dans getAll - OrdreDAO!",JOptionPane.ERROR_MESSAGE);
			return null;
		} catch (Exception e){
			e.printStackTrace();
			return null;
		}	
	};
	*/
	
	public Ordre newElement(){
		// Fournit juste un ordre, avec id_ordre par défaut (=0).
		Ordre ordre = new Ordre(
				dataCenter.getPlaceCourant(), 
				Compte.values()[0],
				new Float(15.0d),
				new Float(1), 
				new Float(0), 
				new Float(15d), 
				new Float(0),
				"Ordre par défaut");
		this.create(ordre);
		return ordre;
	}

	// Attention ! cette méthode est relative à "placeCourant"
	// écrire une méthode statique getEverything() pour obtenir (abs.) toutes les trans ?
	@Override
	public LinkedList<Ordre> getData() {
		LinkedList<Ordre> data = new LinkedList<Ordre>();
		if (dataCenter.getPlaceCourant()==null){
			return data;
		}
		try{
			String query="SELECT id_ordre, id_compte,coursunit,adduc,dimuc,addeur,dimeur,note"
					+ " FROM ordres "
					+ " WHERE id_placement = ? ORDER BY coursunit DESC ";
			PreparedStatement state = conn.prepareStatement(query);
			state.setInt(1, dataCenter.getPlaceCourant().getIndex());
			ResultSet res = state.executeQuery();
			int i = 0;
			while(res.next()){
				i++;
				Compte compte = DataCenter.getCompteDAO().find(res.getInt("id_compte"));
				Ordre ordre = new Ordre(
						dataCenter.getPlaceCourant(),
						compte,
						res.getFloat("coursunit"),
						res.getFloat("adduc"),
						res.getFloat("dimuc"),
						res.getFloat("addeur"),
						res.getFloat("dimeur"),
						res.getString("note"));
				// update of id_ordre, according to the database.
				ordre.setIdOrdre(res.getInt("id_ordre"));
				data.add(ordre);
			}
			System.out.println("OrdreDAO.getData() : "+i+" ligne(s) trouvées.");
			res.close();
			state.close();
			return data;
		} catch (SQLException e){
			e.printStackTrace();
			JOptionPane jop = new JOptionPane();
			jop.showMessageDialog(null, e.getMessage(),"ERREUR dans OrdreDAO.getData() !",JOptionPane.ERROR_MESSAGE);
			return null;
		} catch (Exception e){
			e.printStackTrace();
			return null;
		}	
	}
}
