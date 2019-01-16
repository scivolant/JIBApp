package gestion.operation;

import java.awt.Component;

import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;

/*
 *  Classe responsable de l'évaluation des variations totales en € (à partir du cours cible).
 */

public class ComputationEURRenderer extends JLabel implements TableCellRenderer {

	@Override
	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean isFocus, int row,
			int column) {
		try {
			// Attention ! la colonne "Cours cible" est notée en dur...
			float updatedVal= ((Float)table.getModel().getValueAt(row, (column-2)))*((Float)table.getModel().getValueAt(row, 1));
			table.setValueAt(updatedVal, row, column);
			this.setText(String.format("%.2f",updatedVal));
		} catch (ArithmeticException e){
			this.setText("Problème arithmétique (?!?)");
		}
		return this;
	}
}
