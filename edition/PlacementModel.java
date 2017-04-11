package gestion.edition;

import gestion.compta.Compte;
import gestion.compta.GestionType;
import gestion.compta.Placement;
import gestion.compta.SourceQuote;
import gestion.data.DataCenter;
import gestion.util.ZModel;

public class PlacementModel extends ZModel<Placement> {
	  
	  public PlacementModel(){
			super(
				new Class[] {String.class, GestionType.class, String.class, String.class, SourceQuote.class, String.class},
				new String[] {"Nom", "Type", "ISIN", "Code M‡J", "Source", "Suppr."},
				DataCenter.getPlacementDAO()
					);
	  }
	  
	  public Object getValueAt(int row, int column){
		  switch(column){
		  	case 0:
		  		return data.get(row).getName();
		  	case 1:
		  		return data.get(row).getType();
		  	case 2:
		  		return data.get(row).getISIN();
		  	case 3:
		  		return data.get(row).getCodeMaJ();
		  	case 4:
		  		return data.get(row).getSource();
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
			  	case 1:
			  		data.get(row).setType((GestionType)value);
			  		break;
			  	case 2:
			  		data.get(row).setISIN((String)value);
			  		break;
			  	case 3:
			  		data.get(row).setCodeMaJ((String)value);
			  		break;
			  	case 4:
			  		data.get(row).setSource((SourceQuote)value);
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
