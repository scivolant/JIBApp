package gestion.data.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;

import javax.swing.JOptionPane;

import gestion.compta.GestionType;
import gestion.compta.Placement;
import gestion.compta.SourceQuote;
import gestion.data.Dao;
import gestion.data.DataCenter;

public class PlacementDAO extends Dao<Placement> {
	
	public PlacementDAO(DataCenter instance){
		super(instance);
	}

	public boolean create(Placement obj){
		try{
			String query="INSERT INTO placements(name,mnemo,id_type,isin,codemaj, id_source)";
			query+="VALUES(?,?,?,?,?,?)";
			PreparedStatement state = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
			state.setString(1, obj.getName());
			state.setString(2, obj.getMnemo());
			state.setInt(3, obj.getType().getIdType());
			state.setString(4, obj.getISIN());
			state.setString(5, obj.getCodeMaJ());
			state.setInt(6, obj.getSource().getIdSource());
			int nb_rows = state.executeUpdate();
			
			// MàJ de id_placement (qui doit être à 0 jusque là)
			ResultSet genKey = state.getGeneratedKeys();
			if (genKey.next()){
				obj.setIndex(genKey.getInt(1));
			};
			state.close();
			return (nb_rows !=0);
		} catch (SQLException e){
			JOptionPane jop = new JOptionPane();
			jop.showMessageDialog(null, e.getMessage(),"ERREUR dans PlacementDAO.create !",JOptionPane.ERROR_MESSAGE);
			return false;
		} catch (Exception e){
			e.printStackTrace();
			return false;
		}
	};
	
	public boolean update(Placement obj){
		try{
			String query="UPDATE placements SET name = ?, "
					+ " mnemo = ?,"
					+ " id_type = ?,"
					+ " isin = ?,"
					+ " codemaj = ?, "
					+ " id_source = ? "
					+ " WHERE id_placement = ?;";
			PreparedStatement state = conn.prepareStatement(query);
			state.setString(1, obj.getName());
			state.setString(2, obj.getMnemo());
			state.setInt(3, obj.getType().getIdType());
			state.setString(4, obj.getISIN());
			state.setString(5, obj.getCodeMaJ());
			state.setInt(6, obj.getSource().getIdSource());
			state.setInt(7, obj.getIndex());
			int nb_rows = state.executeUpdate();
			state.close();
			return true;
		} catch (SQLException e){
			JOptionPane jop = new JOptionPane();
			jop.showMessageDialog(null, e.getMessage(),"ERREUR dans PlacementDAO.update !",JOptionPane.ERROR_MESSAGE);
			return false;
		} catch (Exception e){
			e.printStackTrace();
			return false;
		}
	};
	
	public boolean delete(Placement obj){
		try{
			String query="DELETE FROM placements WHERE id_placement = ?";
			PreparedStatement state = conn.prepareStatement(query);
			state.setInt(1, obj.getIndex());
			int nb_rows = state.executeUpdate();
			System.out.println("Suppression de "+nb_rows+" ligne(s)");
			state.close();
			return true;
		} catch (SQLException e){
			JOptionPane jop = new JOptionPane();
			jop.showMessageDialog(null, e.getMessage(),"ERREUR dans PlacementDAO.delete !",JOptionPane.ERROR_MESSAGE);
			return false;
		} catch (Exception e){
			e.printStackTrace();
			return false;
		}
	};
	
	public Placement find(int index){
		try{
			String query="SELECT name,mnemo,id_type,isin,codemaj,id_source"
					+ " FROM placements WHERE id_placement = ?";
			PreparedStatement state = conn.prepareStatement(query,ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			state.setInt(1, index);
			ResultSet res = state.executeQuery();
			res.first();
			GestionType type = DataCenter.getGestionTypesDAO().find(res.getInt("id_type"));
			SourceQuote source = DataCenter.getSourceQuoteDAO().find(res.getInt("id_source"));
			Placement place = new Placement(
					res.getString("name"),
					res.getString("mnemo"),
					type,
					res.getString("isin"),
					res.getString("codemaj"),
					source
					);
			place.setIndex(index);
			res.close();
			state.close();
			return place;
		} catch (SQLException e){
			e.printStackTrace();
			JOptionPane jop = new JOptionPane();
			jop.showMessageDialog(null, e.getMessage(),"ERREUR dans PlacementDAO.find() !",JOptionPane.ERROR_MESSAGE);
			return null;
		} catch (Exception e){
			e.printStackTrace();
			return null;
		}	
	};
	
	public Placement newElement(){
		// Renvoie un placement 
		// avec un id_placement correctement initialisé
		// lequel est fourni par create(place)
		Placement place = Placement.defaultEntry();
		this.create(place);
		return place;
	}
	
	public LinkedList<Placement> getData() {
		// Renvoie les placements adaptés au contexte
		// À adapter à comptesCourants
		LinkedList<Placement> data = new LinkedList<Placement>();
		try{
			String query="SELECT id_placement,name,mnemo,id_type,isin,codemaj,id_source"
					+ " FROM placements ORDER BY name ";
			PreparedStatement state = conn.prepareStatement(query,ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			ResultSet res = state.executeQuery();
			int i = 0;
			while(res.next()){
				i++;
				GestionType type = DataCenter.getGestionTypesDAO().find(res.getInt("id_type"));
				SourceQuote source = DataCenter.getSourceQuoteDAO().find(res.getInt("id_source"));
				Placement place = new Placement(
						res.getString("name"),
						res.getString("mnemo"),
						type,
						res.getString("isin"),
						res.getString("codemaj"),
						source
						);
				place.setIndex(res.getInt("id_placement"));
				data.add(place);
			}
			System.out.println("PlacementDAO.getData(): "+i+" ligne(s) trouvée(s)"); 
			res.close();
			state.close();
			return data;
		} catch (SQLException e){
			e.printStackTrace();
			JOptionPane jop = new JOptionPane();
			jop.showMessageDialog(null, e.getMessage(),"ERREUR dans PlacementDAO.getData() !",JOptionPane.ERROR_MESSAGE);
			return null;
		} catch (Exception e){
			e.printStackTrace();
			return null;
		}
	};
	
	// Commande qui renvoie toujours tout
	public static LinkedList<Placement> getAll() {
		LinkedList<Placement> data = new LinkedList<Placement>();
		try{
			String query="SELECT id_placement,name,mnemo,id_type,isin,codemaj,id_source"
					+ " FROM placements ORDER BY name";
			PreparedStatement state = conn.prepareStatement(query,ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			ResultSet res = state.executeQuery();
			while(res.next()){
				GestionType type = DataCenter.getGestionTypesDAO().find(res.getInt("id_type"));
				SourceQuote source = DataCenter.getSourceQuoteDAO().find(res.getInt("id_source"));
				Placement place = new Placement(
						res.getString("name"),
						res.getString("mnemo"),
						type,
						res.getString("isin"),
						res.getString("codemaj"),
						source
						);
				place.setIndex(res.getInt("id_placement"));
				data.add(place);
			}
			res.close();
			state.close();
			return data;
		} catch (SQLException e){
			e.printStackTrace();
			JOptionPane jop = new JOptionPane();
			jop.showMessageDialog(null, e.getMessage(),"ERREUR dans PlacementDAO.getAll() !",JOptionPane.ERROR_MESSAGE);
			return null;
		} catch (Exception e){
			e.printStackTrace();
			return null;
		}
	};

}
