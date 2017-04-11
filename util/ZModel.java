package gestion.util;

import java.sql.Date;
import java.text.NumberFormat;
import java.util.LinkedList;
import java.util.Locale;

import javax.swing.JOptionPane;
import javax.swing.table.AbstractTableModel;

import gestion.data.DAO;
import gestion.data.DAOtableau;

public abstract class ZModel<T> extends AbstractTableModel {
	  protected LinkedList<T> data;
	  protected Class[] listeClass;
	  protected String[] title;
	  protected DAO<T> daoTableau;
	  
	  // Model g�n�ral qui se sp�cialise en TransModel et OrdreModel. Comporte :
	  // _ fonctions d'ajout et de suppression de donn�es
	  // _ fonctions de conversion de String en Date et en Float
	  // _ fonction de M�J des donn�es (getData, utilise les valeurs courantes de comptes et placement)
	  
	  public ZModel(Class[] listeClass, String[]  title, DAO<T> daoTableau){
		  super();
		  this.data=daoTableau.getData();
		  this.listeClass=listeClass;
		  this.title=title;
		  this.daoTableau=daoTableau;
	  }
	  
	  public String getColumnName(int col) {
		  return this.title[col];
	  }
	  
	  public int getColumnCount(){
		  return this.title.length;
	  }
	  
	  public int getRowCount(){
		  return this.data.size();
	  }
	  
	  public Class getColumnClass(int col){
		  return listeClass[col];
	  }
	  
	  public void removeRow(int position){
		  boolean test = daoTableau.delete(data.get(position));
		  if (test){
			  data.remove(position);
		  } else {
			  JOptionPane jop = new JOptionPane();
			  jop.showMessageDialog(null, "removeRow dans ZModel a �chou�","ERREUR",JOptionPane.ERROR_MESSAGE);
		  }

		  // Pour avertir le tableau que les donn�es ont chang�.
		  this.fireTableDataChanged();
	  }
	  
	  // prend un �l�ment non initialis�
	  public void addRow(T obj){
		  boolean test = daoTableau.create(obj);
		  if (test){
			  	data.add(obj);
		  } else {
				JOptionPane jop = new JOptionPane();
				jop.showMessageDialog(null, "addRow dans ZModel a �chou�","ERREUR",JOptionPane.ERROR_MESSAGE);
		  }
		  // Pour avertir le tableau que les donn�es ont chang�.
		  this.fireTableDataChanged();
	  }
	  
	  // cr�er un nouvel �l�ment
	  public void addRow(){
		  T obj = daoTableau.newElement();
		  if (obj == null){
				JOptionPane jop = new JOptionPane();
				jop.showMessageDialog(null, "addRow dans ZModel a �chou�","ERREUR",JOptionPane.ERROR_MESSAGE);
		  } else {
			  	data.add(obj);
		  } 
		  // Pour avertir le tableau que les donn�es ont chang�.
		  this.fireTableDataChanged();
	  }
	  
	  public abstract boolean isCellEditable(int row, int col);
	  
	  public abstract Object getValueAt(int row, int column);

	  public abstract void setValueAt(Object value, int row, int col);
	  
	  public LinkedList<T> getData(){
		  return this.data;
	  }
	  
	  public DAO<T> getDAOtableau(){
		  return this.daoTableau;
	  }
	  
	  // mise � jour des donn�es
	  public void updateData(){
		  this.data = daoTableau.getData();
		  this.fireTableDataChanged();
	  }
	  
	  // fonction utilis�e pour les champs num�riques comme "Add (�)"
	  public float convertStringToFloat(Object value){
		  NumberFormat format = NumberFormat.getInstance(Locale.FRANCE);
		  float val = 0.f;
		  try{
			  val = format.parse((String)value).floatValue();
		  } catch (Exception e){
			  e.printStackTrace();
			  JOptionPane jop = new JOptionPane();
			  jop.showMessageDialog(null, e.getMessage(),"ERREUR ds ZModel.convertStringToFloat",JOptionPane.ERROR_MESSAGE);
		  }
		  return val;
	  }
	  
	  // fonction utilis�e pour la date dans TransModel
	  public static Date convertStringToDate(Object value){
		  Date date = new Date(0);
		  try{
			  date = Date.valueOf((String)value);
		  } catch (IllegalArgumentException e){
			  e.printStackTrace();
			  JOptionPane jop = new JOptionPane();
			  jop.showMessageDialog(null, "Date pas au format yyyy-mm-dd ? "+e.getMessage(),"ERREUR ds ZModel.convertStringToDate",JOptionPane.ERROR_MESSAGE);
		  } catch (Exception e){
			  e.printStackTrace();
			  JOptionPane jop = new JOptionPane();
			  jop.showMessageDialog(null, e.getMessage(),"ERREUR ds ZModel.convertStringToDate",JOptionPane.ERROR_MESSAGE);
		  }
		  return date; 
	  }
}
