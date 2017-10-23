package gestion.edition;

import gestion.compta.GestionType;
import gestion.compta.SourceQuote;
import gestion.data.DataCenter;
import gestion.data.quotation.Transfer;
import gestion.util.ZModel;

public class SourceQuoteModel extends ZModel<SourceQuote> {
	  
	  public SourceQuoteModel(){
			super(
				new Class[] {String.class, String.class, Transfer.class, String.class},
				new String[] {"Nom", "URL", "Transf.", "Suppr."},
				DataCenter.getSourceQuoteDAO()
					);
	  }
	  
	  public Object getValueAt(int row, int column){
		  switch(column){
		  	case 0:
		  		return data.get(row).getName();
		  	case 1:
		  		return data.get(row).getUrl();
		  	case 2:
		  		return data.get(row).getTransf();
		  	default:
		  		return "-";
		  }
	  }
	  
	  public void setValueAt(Object value, int row, int col){
		  if (!this.getColumnName(col).equals("Suppr.")){
			  switch(col){
			  	case 0:
			  		data.get(row).setName((String)value);
			  		break;
			  	case 1:
			  		data.get(row).setUrl((String)value);
			  		break;
			  	case 2:
			  		data.get(row).setTransf((Transfer)value);
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
