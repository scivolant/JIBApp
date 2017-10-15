package gestion.operation;

import java.sql.Date;

import gestion.compta.Cours;
import gestion.data.DataCenter;
import gestion.util.ZModel;

public class CoursModel extends ZModel<Cours> {
	  
	  public CoursModel(){
			super(
				new Class[] {Date.class, float.class, String.class},
				new String[] {"Date", "Cours", "Suppr."},
				DataCenter.getCoursDAO()
					);
	  }
	  
	  public Object getValueAt(int row, int column){
		  switch(column){
		  	case 0:
		  		return data.get(row).getDate();
		  	case 1:
		  		return data.get(row).getCoursUnit();
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
			  		data.get(row).setCoursUnit(this.convertStringToFloat(value));
			  		break;
			  	default:
			  }
			  fireTableRowsUpdated(row,row);
		  }
	  }
	  
	  public boolean isCellEditable(int row, int col){
		  return true;
	  }
	  
}
