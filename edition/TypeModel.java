package gestion.edition;

import gestion.compta.GestionType;
import gestion.data.DataCenter;
import gestion.util.ZModel;

public class TypeModel extends ZModel<GestionType> {
	  
	  public TypeModel(){
			super(
				new Class[] {String.class,String.class},
				new String[] {"Nom", "Suppr."},
				DataCenter.getGestionTypesDAO()
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
