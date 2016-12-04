package gestionSuivi.fenetrePlacement;

import java.awt.Component;

import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;

/**
 * 
 * @author Trivy
 * Classe en charge de l'affichage du nbr UC.
 * _ impose quatre décimales
 */

public class NormUCRenderer extends JLabel implements TableCellRenderer {

	@Override
	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean isFocus, int row,
			int column) {
		float updatedVal=(float)table.getModel().getValueAt(row, column);
		this.setText(String.format("%.4f",updatedVal));
		return this;
	}
}
