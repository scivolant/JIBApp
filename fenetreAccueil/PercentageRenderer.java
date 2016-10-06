package gestionSuivi.fenetreAccueil;

import java.awt.Component;
import java.text.NumberFormat;

import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;

public class PercentageRenderer extends JLabel implements TableCellRenderer {
   public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean isFocus, int row, int col) {
	   // Choix du formatage du texte dans la cellule
	   NumberFormat percentFormat = NumberFormat.getPercentInstance();
	   percentFormat.setMaximumFractionDigits(2);
      setText(percentFormat.format(value));
      return this;
   }
}
