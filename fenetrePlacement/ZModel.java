package gestionSuivi.fenetrePlacement;

import javax.swing.table.AbstractTableModel;

public class ZModel extends AbstractTableModel {
	  private Object[][] data;
	  private String[] title;
	  
	  public ZModel(Object[][] data, String[] title){
		  super();
		  this.data=data;
		  this.title=title;
	  }
	  
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
		  if (!this.getColumnName(col).equals("Suppr."))
		  this.data[row][col]=value;
	  }
	  
	  public Class getColumnClass(int col){
		  return this.data[0][col].getClass();
	  }
	  
	  public void removeRow(int position){
		  int indice = 0, indice2 = 0;
		  int nbRow = this.getRowCount()-1, nbCol = this.getColumnCount();
		  Object temp[][] = new Object[nbRow][nbCol];
		  
		  for (Object[] value: this.data){
			  if (indice != position){
				  temp[indice2++]=value;
			  }
			  indice++;
		  }
		  this.data = temp;
		  temp=null;
		  // Pour avertir le tableau que les données ont changé.
		  this.fireTableDataChanged();
	  }
	  
	  public void addRow(Object[] data){
		  int indice = 0, nbRow = this.getRowCount(), nbCol = this.getColumnCount();
		  if (nbRow == 0){
			  Object temp[][] = {data};
			  this.data=temp;
		  }
		  else {
			  Object temp[][] = this.data;
			  this.data=new Object[nbRow+1][nbCol];
			  
			  for (Object[] value: temp){
				  this.data[indice++]=value;
			  }
			  this.data[indice] = data;
			  temp=null;
		  }
		  // Pour avertir le tableau que les données ont changé.
		  this.fireTableDataChanged();
	  }
	  
	  public boolean isCellEditable(int row, int col){
		  if (!this.getColumnName(col).equals("Prix unit.") )
			  return true; 
		  else return false;
	  }
	  
	  public Object[][] getData(){
		  return this.data;
	  }
}
