package gestionSuivi.fenetreAccueil;

import java.awt.Component;
import java.text.NumberFormat;
import java.util.Locale;

import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;

public class NumberRenderer extends JLabel implements TableCellRenderer {

	@Override
   public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean isFocus, int row, int col) {
	  // Choix du formatage du texte dans la cellule
	  NumberFormat nf = NumberFormat.getInstance(Locale.FRENCH);
	  nf.setMaximumFractionDigits(2);
	  setText(nf.format(value));
	  setHorizontalAlignment(JLabel.RIGHT);
	  return this;
   }

}
