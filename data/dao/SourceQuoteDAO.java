package gestion.data.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;

import javax.swing.JOptionPane;

import gestion.compta.Student;
import gestion.compta.Placement;
import gestion.compta.SourceQuote;
import gestion.data.Dao;
import gestion.data.DataCenter;
import gestion.data.quotation.Transfer;

public class SourceQuoteDAO extends Dao<SourceQuote> {
	
	public SourceQuoteDAO(DataCenter instance){
		super(instance);
	}

	public boolean create(SourceQuote obj){
		try{
			String query="INSERT INTO sourcequotes(name,url,id_transf)";
			query+="VALUES(?,?,?)";
			PreparedStatement state = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
			state.setString(1, obj.getName());
			state.setString(2, obj.getUrl());
			state.setInt(3, obj.getTransf().getIdTransf());
			int nb_rows = state.executeUpdate();
			
			// MàJ de id_trans (qui doit être à 0 jusque là)
			ResultSet genKey = state.getGeneratedKeys();
			if (genKey.next()){
				obj.setIdSource(genKey.getInt(1));
			};
			state.close();
			return (nb_rows !=0);
		} catch (SQLException e){
			JOptionPane jop = new JOptionPane();
			jop.showMessageDialog(null, e.getMessage(),"ERREUR dans SourceQuoteDAO.create !",JOptionPane.ERROR_MESSAGE);
			return false;
		} catch (Exception e){
			e.printStackTrace();
			return false;
		}
	};
	
	public boolean update(SourceQuote obj){
		try{
			String query="UPDATE sourcequotes SET name = ?, "
					+ " url = ?,"
					+ " id_transf = ?"
					+ " WHERE id_source = ?;";
			PreparedStatement state = conn.prepareStatement(query);
			state.setString(1, obj.getName());
			state.setString(2, obj.getUrl());
			state.setInt(3, obj.getTransf().getIdTransf());
			state.setInt(4, obj.getIdSource());
			int nb_rows = state.executeUpdate();
			state.close();
			return true;
		} catch (SQLException e){
			JOptionPane jop = new JOptionPane();
			jop.showMessageDialog(null, e.getMessage(),"ERREUR dans SourceQuoteDAO.update !",JOptionPane.ERROR_MESSAGE);
			return false;
		} catch (Exception e){
			e.printStackTrace();
			return false;
		}
	};
	
	public boolean delete(SourceQuote obj){
		try{
			String query="DELETE FROM sourcequotes WHERE id_source = ?";
			PreparedStatement state = conn.prepareStatement(query);
			state.setInt(1, obj.getIdSource());
			int nb_rows = state.executeUpdate();
			System.out.println("Suppression de "+nb_rows+" ligne(s)");
			state.close();
			return true;
		} catch (SQLException e){
			JOptionPane jop = new JOptionPane();
			jop.showMessageDialog(null, e.getMessage(),"ERREUR dans SourceQuoteDAO.delete !",JOptionPane.ERROR_MESSAGE);
			return false;
		} catch (Exception e){
			e.printStackTrace();
			return false;
		}
	};
	
	public SourceQuote find(int index){
		try{
			String query="SELECT name,url,id_transf"
					+ " FROM sourcequotes WHERE id_source = ?";
			PreparedStatement state = conn.prepareStatement(query,ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			state.setInt(1, index);
			ResultSet res = state.executeQuery();
			SourceQuote source;
			if (res.next()){
				source = new SourceQuote(
						res.getString("name"),
						res.getString("url"),
						Transfer.values()[res.getInt("id_transf")-1]
						);
				source.setIdSource(index);
			} else {
				source = null;
			}
			res.close();
			state.close();
			return source;
		} catch (SQLException e){
			e.printStackTrace();
			JOptionPane jop = new JOptionPane();
			jop.showMessageDialog(null, e.getMessage(),"ERREUR dans SourceQuoteDAO.find() !",JOptionPane.ERROR_MESSAGE);
			return null;
		} catch (Exception e){
			e.printStackTrace();
			return null;
		}	
	};
	
	// Renvoie un placement (pleinement initialisé)
	public SourceQuote newElement(){
		SourceQuote source = SourceQuote.defaultEntry();
		this.create(source);
		return source;
	}
	
	// Renvoie un placement existant (pleinement initialisé) ou en créé un nouveau
	public SourceQuote anyElement(){
		try{
			String query="SELECT id_source, name FROM sourcequotes ORDER BY id_source LIMIT 1";
			PreparedStatement state = conn.prepareStatement(query,ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			ResultSet res = state.executeQuery();
			SourceQuote source;
			if (res.first()){
				source = this.find(res.getInt("id_source"));
			} else {
				source = this.newElement();
			}
			res.close();
			state.close();
			return source;
		} catch (SQLException e){
			JOptionPane jop = new JOptionPane();
			jop.showMessageDialog(null, e.getMessage(),"ERREUR dans GestionTypesDAO.find !",JOptionPane.ERROR_MESSAGE);
			return null;
		} catch (Exception e){
			e.printStackTrace();
			return null;
		}
	}
	
	public LinkedList<SourceQuote> getData() {
		LinkedList<SourceQuote> data = new LinkedList<SourceQuote>();
		try{
			String query="SELECT id_source,name,url,id_transf"
					+ " FROM sourcequotes ORDER BY id_source ";
			PreparedStatement state = conn.prepareStatement(query,ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			ResultSet res = state.executeQuery();
			int i =0;
			while(res.next()){
				i++;
				SourceQuote source = new SourceQuote(
						res.getString("name"),
						res.getString("url"),
						// passer d'un indice SQL (à partir de 1) à indice vecteur (à partir de 0)
						Transfer.values()[res.getInt("id_transf")-1]
						);
				source.setIdSource(res.getInt("id_source"));
				data.add(source);
			}
			System.out.println("SourceQuoteDAO.getData : "+i+" ligne(s) trouvée(s).");
			res.close();
			state.close();
			return data;
		} catch (SQLException e){
			e.printStackTrace();
			JOptionPane jop = new JOptionPane();
			jop.showMessageDialog(null, e.getMessage(),"ERREUR dans SourceQuoteDAO.getData() !",JOptionPane.ERROR_MESSAGE);
			return null;
		} catch (Exception e){
			e.printStackTrace();
			return null;
		}
	};
	
	/*
	// Commande qui renvoie toujours tout
	public static LinkedList<SourceQuote> getAll(){
		return null;
	};
	*/

}
