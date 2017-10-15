package gestion.util;

import java.sql.Date;
import java.text.NumberFormat;
import java.util.LinkedList;
import java.util.Locale;

import javax.swing.JOptionPane;
import javax.swing.table.AbstractTableModel;

import gestion.data.Dao;

public abstract class ZModel<T> extends AbstractTableModel {
	  protected LinkedList<T> data;
	  protected Class[] listeClass;
	  protected String[] title;
	  protected Dao<T> daoT;
	  
	  // Model général qui se spécialise en TransModel et OrdreModel. Comporte :
	  // _ fonctions d'ajout et de suppression de données
	  // _ fonctions de conversion de String en Date et en Float
	  // _ fonction de MàJ des données (getData, utilise les valeurs courantes de comptes et placement)
	  
	  public ZModel(Class[] listeClass, String[]  title, Dao<T> daoT){
		  super();
		  this.data=daoT.getData();
		  this.listeClass=listeClass;
		  this.title=title;
		  this.daoT=daoT;
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
		  boolean test = daoT.delete(data.get(position));
		  if (test){
			  data.remove(position);
		  } else {
			  JOptionPane jop = new JOptionPane();
			  jop.showMessageDialog(null, "removeRow dans ZModel a échoué","ERREUR",JOptionPane.ERROR_MESSAGE);
		  }

		  // Pour avertir le tableau que les données ont changé.
		  this.fireTableDataChanged();
	  }
	  
	  // prend un élément non initialisé
	  public void addRow(T obj){
		  boolean test = daoT.create(obj);
		  if (test){
			  	data.addFirst(obj);
			  	// Pour avertir le tableau que les données ont changé.
				this.fireTableDataChanged();
		  } else {
				JOptionPane jop = new JOptionPane();
				jop.showMessageDialog(null, "addRow dans ZModel a échoué","ERREUR",JOptionPane.ERROR_MESSAGE);
		  }
	  }
	  
	  // créer un nouvel élément
	  public void addRow(){
		  T obj = daoT.newElement();
		  if (obj == null){
				JOptionPane jop = new JOptionPane();
				jop.showMessageDialog(null, "addRow dans ZModel a échoué","ERREUR",JOptionPane.ERROR_MESSAGE);
		  } else {
			  	data.addFirst(obj);
		  } 
		  // Pour avertir le tableau que les données ont changé.
		  this.fireTableDataChanged();
	  }
	  
	  public abstract boolean isCellEditable(int row, int col);
	  
	  public abstract Object getValueAt(int row, int column);

	  public abstract void setValueAt(Object value, int row, int col);
	  
	  public LinkedList<T> getData(){
		  return this.data;
	  }
	  
	  public Dao<T> getDaoT(){
		  return this.daoT;
	  }
	  
	  // mise à jour des données
	  // à partir de la base de données
	  public void updateData(){
		  this.data = daoT.getData();
		  this.fireTableDataChanged();
	  }
	  
	  // fonction utilisée pour les champs numériques comme "Add (€)"
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
	  
	  // fonction utilisée pour la date dans TransModel
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
