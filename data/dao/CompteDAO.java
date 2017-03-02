package gestion.data.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.LinkedList;

import javax.swing.JOptionPane;

import gestion.compta.Compte;
import gestion.compta.GestionType;
import gestion.data.DAO;
import gestion.data.DataCenter;

public class CompteDAO extends DAO<Compte> {
	
	public CompteDAO(DataCenter dataCenter){
		super(dataCenter);
	}

	public boolean create(Compte obj){
		try{
			String query="INSERT INTO comptes(name) VALUES(?)";
			PreparedStatement state = conn.prepareStatement(query);
			state.setString(1, obj.getName());
			int nb_rows = state.executeUpdate();
			state.close();
			return true;
		} catch (SQLException e){
			JOptionPane jop = new JOptionPane();
			jop.showMessageDialog(null, e.getMessage(),"ERREUR dans CompteDAO.create !",JOptionPane.ERROR_MESSAGE);
			return false;
		} catch (Exception e){
			e.printStackTrace();
			return false;
		}
	};
	
	public boolean update(Compte obj){
		try{
			String query="UPDATE comptes SET name = ? WHERE id_compte = ?";
			PreparedStatement state = conn.prepareStatement(query,ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			state.setString(1, obj.getName());
			state.setInt(2, obj.getIndex());
			int nb_rows = state.executeUpdate();
			state.close();
			return true;
		} catch (SQLException e){
			JOptionPane jop = new JOptionPane();
			jop.showMessageDialog(null, e.getMessage(),"ERREUR dans CompteDAO.update !",JOptionPane.ERROR_MESSAGE);
			return false;
		} catch (Exception e){
			e.printStackTrace();
			return false;
		}
	};
	
	public boolean delete(Compte obj){
		try{
			String query="DELETE FROM comptes WHERE id_compte = ?";
			PreparedStatement state = conn.prepareStatement(query,ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			state.setInt(1, obj.getIndex());
			int nb_rows = state.executeUpdate();
			System.out.println("Suppression de "+nb_rows+" ligne(s)");
			state.close();
			return true;
		} catch (SQLException e){
			JOptionPane jop = new JOptionPane();
			jop.showMessageDialog(null, e.getMessage(),"ERREUR dans CompteDAO.delete !",JOptionPane.ERROR_MESSAGE);
			return false;
		} catch (Exception e){
			e.printStackTrace();
			return false;
		}
	};
	
	public Compte find(int index){
		try{
			String query="SELECT name"
					+ " FROM comptes WHERE id_compte = ?";
			PreparedStatement state = conn.prepareStatement(query,ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			state.setInt(1, index);
			ResultSet res = state.executeQuery();
			res.first();
			Compte compte = new Compte(
					res.getString("name")
					);
			compte.setIndex(index);
			res.close();
			state.close();
			return compte;
		} catch (SQLException e){
			e.printStackTrace();
			JOptionPane jop = new JOptionPane();
			jop.showMessageDialog(null, e.getMessage(),"ERREUR dans CompteDAO.find() !",JOptionPane.ERROR_MESSAGE);
			return null;
		} catch (Exception e){
			e.printStackTrace();
			return null;
		}	
	}
	
	public LinkedList<Compte> getData() {
		LinkedList<Compte> data = new LinkedList<Compte>();
		try{
			String query="SELECT id_compte, name FROM comptes ORDER BY id_compte";
			PreparedStatement state = conn.prepareStatement(query,ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			ResultSet res = state.executeQuery();
			while(res.next()){
				Compte compte = new Compte(
						res.getString("name")
						);
				compte.setIndex(res.getInt("id_compte"));
				data.add(compte);
			}
			System.out.println("CompteDAO.getData() : "+data.size()+" ligne(s) trouvées.");
			res.close();
			state.close();
			return data;
		} catch (SQLException e){
			JOptionPane jop = new JOptionPane();
			jop.showMessageDialog(null, e.getMessage(),"ERREUR dans CompteDAO.getData !",JOptionPane.ERROR_MESSAGE);
			return null;
		} catch (Exception e){
			e.printStackTrace();
			return null;
		}
	};

	public Compte newElement() {
		Compte compte = Compte.values()[0];
		this.create(compte);
		return compte;
	};

}
