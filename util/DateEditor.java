package gestion.util;

import java.awt.Component;
import java.text.SimpleDateFormat;

import javax.swing.DefaultCellEditor;
import javax.swing.JFormattedTextField;
import javax.swing.JTable;

public class DateEditor extends DefaultCellEditor {
	
	public DateEditor(){
		super(new JFormattedTextField(new SimpleDateFormat("yyyy-MM-dd")));
	};

	 public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column){
	        JFormattedTextField editor = (JFormattedTextField) super.getTableCellEditorComponent(table, value, isSelected, row, column);
	        return editor;
	}

}
