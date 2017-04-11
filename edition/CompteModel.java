package gestion.edition;

import java.sql.Date;
import java.util.LinkedList;

import javax.swing.JOptionPane;
import javax.swing.table.AbstractTableModel;

import gestion.compta.Compte;
import gestion.compta.Ordre;
import gestion.compta.Placement;
import gestion.compta.Transaction;
import gestion.data.DataCenter;
import gestion.util.ZModel;

public class CompteModel extends ZModel<Compte> {
	  
	  public CompteModel(){
			super(
				new Class[] {String.class,String.class},
				new String[] {"Nom", "Suppr."},
				DataCenter.getCompteDAO()
				);
	  }
	  
	  public Object getValueAt(int row, int column){
		  switch(column){
		  	case 0:
		  		return data.get(row).getName();
		  	default:
		  		return "-";
		  }
	  }
	  
	  public void setValueAt(Object value, int row, int col){
		  // la sortie de FloatEditor est un String, 
		  // que l'on convertit avec convertStringToFloat (dans ZModel)
		  if (!this.getColumnName(col).equals("Suppr.")){
			  switch(col){
			  	case 0:
			  		data.get(row).setName((String)value);
			  		break;
			  	default:
			  }
		  }
	  }
	  
	  public Class getColumnClass(int col){
		  return listeClass[col];
	  }
	  
	  public boolean isCellEditable(int row, int col){
		  return true;
	  }
	  
}
