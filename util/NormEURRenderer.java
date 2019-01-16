package gestion.util;

import java.awt.Component;

import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;

/**
 * 
 * @author Trivy
 * Classe en charge de l'affichage des sommes en euros.
 * _ impose deux décimales
 */

public class NormEURRenderer extends JLabel implements TableCellRenderer {

	@Override
	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean isFocus, int row,
			int column) {
		String text;
		try{
			float updatedVal=(Float)table.getModel().getValueAt(row, column);
			text = String.format("%.2f",updatedVal);
		} catch (Exception e){
			text = "-";
		}
		this.setText(text);
		return this;
	}
}
