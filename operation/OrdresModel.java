package gestion.operation;

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

public class OrdresModel extends ZModel<Ordre> {
	  
	  public OrdresModel(){
			super(
				new Class[] {Compte.class, float.class, float.class, float.class, float.class, float.class, String.class, String.class},
				new String[] {"Compte", "Cours cible", "Achat (UC)", "Vente (UC)", "Add (€)", "Dim (€)", "Note", "Suppr."},
				DataCenter.getOrdreDAO()
					);
	  }
	  
	  public Object getValueAt(int row, int column){
		  switch(column){
		  	case 0:
		  		return data.get(row).getCompte();
		  	case 1:
		  		return data.get(row).getCoursUnit();
		  	case 2:
		  		return data.get(row).getAddUC();
		  	case 3:
		  		return data.get(row).getDimUC();
		  	case 4:
		  		return data.get(row).getAddEUR();
		  	case 5:
		  		return data.get(row).getDimEUR();
		  	case 6:
		  		return data.get(row).getNote();
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
			  		data.get(row).setCompte((Compte)value);
			  		break;
			  	case 1:
			  		data.get(row).setCoursUnit(this.convertStringToFloat(value));
			  		break;
			  	case 2:
			  		data.get(row).setAddUC(this.convertStringToFloat(value));
			  		break;
			  	case 3:
			  		data.get(row).setDimUC(this.convertStringToFloat(value));
			  		break;
			  	case 4:
			  		data.get(row).setAddEUR((Float)value);
			  		break;
			  	case 5:
			  		data.get(row).setDimEUR((Float)value);
			  		break;
			  	case 6:
			  		data.get(row).setNote((String)value);
			  		break;
			  	default:
			  }
			  fireTableRowsUpdated(row,row);
		  }
	  }
	  
	  public Class getColumnClass(int col){
		  return listeClass[col];
	  }
	  
	  public boolean isCellEditable(int row, int col){
		  if (this.getColumnName(col).equals("Add (€)") ){
			  return false; 
		  }
		  if (this.getColumnName(col).equals("Dim (€)") ){
			  return false; 
		  }
		  else return true;
	  }
	  
}
