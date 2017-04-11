package gestion.accueil;

import javax.swing.table.DefaultTableModel;

public class ZModel2 extends DefaultTableModel {
	  
	  public ZModel2(Object[][] data, String[] title){
		  super(data, title);
	  }
	  
	  /*
	  public String getColumnName(int col) {
		  return this.title[col];
	  }
	  
	  public int getColumnCount(){
		  return this.title.length;
	  }
	  
	  public int getRowCount(){
		  return this.data.length;
	  }
	  
	  public Object getValueAt(int row, int column){
		  return this.data[row][column];
	  }
	  
	  public void setValueAt(Object value, int row, int col){
		  this.data[row][col]=value;
	  }
	  
	  public Class getColumnClass(int col){
		  return this.data[0][col].getClass();
	  }
	  */
	  
	  public boolean isCellEditable(int row, int col){
		  return false;
	  }
}
