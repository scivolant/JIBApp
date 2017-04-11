package gestion.data.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.LinkedList;

import javax.swing.JOptionPane;

import gestion.compta.Compte;
import gestion.compta.GestionType;
import gestion.compta.Transaction;
import gestion.data.DAO;
import gestion.data.DataCenter;

public class GestionTypesDAO extends DAO<GestionType> {
	
	public GestionTypesDAO(DataCenter instance){
		super(instance);
	}

	@Override
	public boolean create(GestionType obj) {
		try{
			String query="INSERT INTO types(name) VALUES(?)";
			PreparedStatement state = conn.prepareStatement(query);
			state.setString(1, obj.toString());
			int nb_rows = state.executeUpdate();
			state.close();
			return true;
		} catch (SQLException e){
			JOptionPane jop = new JOptionPane();
			jop.showMessageDialog(null, e.getMessage(),"ERREUR dans create - GestionTypesDAO!",JOptionPane.ERROR_MESSAGE);
			return false;
		} catch (Exception e){
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public boolean update(GestionType obj) {
		try{
			String query="UPDATE types SET name = ? WHERE id_type = ?";
			PreparedStatement state = conn.prepareStatement(query,ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			state.setString(1, obj.toString());
			state.setInt(2, obj.getIdType());
			int nb_rows = state.executeUpdate();
			state.close();
			return true;
		} catch (SQLException e){
			JOptionPane jop = new JOptionPane();
			jop.showMessageDialog(null, e.getMessage(),"ERREUR dans update - GestionTypesDAO!",JOptionPane.ERROR_MESSAGE);
			return false;
		} catch (Exception e){
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public boolean delete(GestionType obj) {
		try{
			String query="DELETE FROM types WHERE id_type = ?";
			PreparedStatement state = conn.prepareStatement(query,ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			state.setInt(1, obj.getIdType());
			int nb_rows = state.executeUpdate();
			System.out.println("Suppression de "+nb_rows+" ligne(s)");
			state.close();
			return true;
		} catch (SQLException e){
			JOptionPane jop = new JOptionPane();
			jop.showMessageDialog(null, e.getMessage(),"ERREUR dans delete - GestionTypesDAO!",JOptionPane.ERROR_MESSAGE);
			return false;
		} catch (Exception e){
			e.printStackTrace();
			return false;
		}	
	}

	@Override
	public GestionType find(int index) {
		try{
			String query="SELECT id_type, name FROM types WHERE id_type = ?";
			PreparedStatement state = conn.prepareStatement(query,ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			state.setInt(1, index);
			ResultSet res = state.executeQuery();
			res.first();
			GestionType type = new GestionType(res.getInt("id_type"),res.getString("name"));
			res.close();
			state.close();
			return type;
		} catch (SQLException e){
			JOptionPane jop = new JOptionPane();
			jop.showMessageDialog(null, e.getMessage(),"ERREUR dans find - GestionTypesDAO!",JOptionPane.ERROR_MESSAGE);
			return null;
		} catch (Exception e){
			e.printStackTrace();
			return null;
		}	
	}
	
	// renvoie le type par d�faut (en esp�rant qu'il existe !)
	// NB : il vient avec un indice qui existe d�j� dans la base.
	public GestionType newElement(){
		GestionType type = GestionType.defaultEntry();
		this.create(type);
		return type;
	}
	
	// renvoie un �l�ment de GestionTypes (soit le premier venu, soit on en cr�e un nouveau, que l'on inclus dans la base de donn�es)
	public GestionType anyElement(){
		try{
			String query="SELECT id_type, name FROM types ORDER BY id_type LIMIT 1";
			PreparedStatement state = conn.prepareStatement(query,ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			ResultSet res = state.executeQuery();
			GestionType type;
			if (res.first()){
				type = this.find(res.getInt("id_type"));
			} else {
				type = this.newElement();
			}
			res.close();
			state.close();
			return type;
		} catch (SQLException e){
			JOptionPane jop = new JOptionPane();
			jop.showMessageDialog(null, e.getMessage(),"ERREUR dans GestionTypesDAO.find !",JOptionPane.ERROR_MESSAGE);
			return null;
		} catch (Exception e){
			e.printStackTrace();
			return null;
		}
	}
	
	public LinkedList<GestionType> getData() {
		LinkedList<GestionType> data = new LinkedList<GestionType>();
		try{
			String query="SELECT id_type, name FROM types ORDER BY id_type";
			PreparedStatement state = conn.prepareStatement(query,ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			ResultSet res = state.executeQuery();
			while(res.next()){
				GestionType type = new GestionType(
						res.getInt("id_type"),
						res.getString("name")
						);
				data.add(type);
			}
			System.out.println("GestionTypesDAO.getData() : "+data.size()+" ligne(s) trouv�es.");
			res.close();
			state.close();
			return data;
		} catch (SQLException e){
			JOptionPane jop = new JOptionPane();
			jop.showMessageDialog(null, e.getMessage(),"ERREUR dans GestionTypesDAO.getData !",JOptionPane.ERROR_MESSAGE);
			return null;
		} catch (Exception e){
			e.printStackTrace();
			return null;
		}
	};

}
