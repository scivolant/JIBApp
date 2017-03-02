package gestion.operation.transaction;

import java.awt.Component;

import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;

/*
 *  Classe responsable de la mise à jour des prix unitaires (calculé à partir de l'augmentation de UC et augmentation €)
 *  Attention ! Il a l'air un peu fragile...
 */

public class ComputationRenderer extends JLabel implements TableCellRenderer {

	@Override
	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean isFocus, int row,
			int column) {
		try {
			float updatedVal= (((float)table.getModel().getValueAt(row, (column+3))-(float)table.getModel().getValueAt(row, (column+4))))/((float)table.getModel().getValueAt(row, (column+1))-(float)table.getModel().getValueAt(row, (column+2)));
			table.setValueAt(updatedVal, row, column);
			this.setText(String.format("%.2f",updatedVal));
		} catch (ArithmeticException e){
			this.setText("Division par zero !");
		}
		return this;
	}
}
