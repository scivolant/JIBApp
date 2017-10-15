package gestion.operation.transaction;

import java.sql.Date;
import java.util.LinkedList;

import gestion.compta.Compte;
import gestion.compta.Placement;
import gestion.compta.Transaction;
import gestion.data.DataCenter;
import gestion.util.ZModel;

public class TransModel extends ZModel<Transaction> {
	  
	  public TransModel(){
			super(
				new Class[] {Date.class, Compte.class, float.class, float.class, float.class, float.class, float.class, String.class},
				new String[] {"Date", "Compte", "Cours", "Add (UC)", "Dim (UC)", "Add (€)", "Dim (€)", "Suppr."},
				DataCenter.getTransacDAO()
					);
	  }
	  
	  public Object getValueAt(int row, int column){
		  switch(column){
		  	case 0:
		  		return data.get(row).getDate();
		  	case 1:
		  		return data.get(row).getCompte();
		  	case 2:
		  		return data.get(row).getCoursUnit();
		  	case 3:
		  		return data.get(row).getAddUC();
		  	case 4:
		  		return data.get(row).getDimUC();
		  	case 5:
		  		return data.get(row).getAddEUR();
		  	case 6:
		  		return data.get(row).getDimEUR();
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
			  		data.get(row).setDate(this.convertStringToDate(value));
			  		break;
			  	case 1:
			  		data.get(row).setCompte((Compte)value);
			  		break;
			  	case 2:
			  		data.get(row).setCoursUnit((float)value);
			  		break;
			  	case 3:
			  		data.get(row).setAddUC(this.convertStringToFloat(value));
			  		break;
			  	case 4:
			  		data.get(row).setDimUC(this.convertStringToFloat(value));
			  		break;
			  	case 5:
			  		data.get(row).setAddEUR(this.convertStringToFloat(value));
			  		break;
			  	case 6:
			  		data.get(row).setDimEUR(this.convertStringToFloat(value));
			  		break;
			  	default:
			  }
			  fireTableRowsUpdated(row,row);
		  }
	  }
	  
	  public boolean isCellEditable(int row, int col){
		  if (this.getColumnName(col).equals("Cours") ){
			  return false; 
		  }
		  else return true;
	  }
	  
}
