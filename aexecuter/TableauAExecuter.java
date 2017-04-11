package gestion.aexecuter;

import java.util.Vector;

import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableModel;

import gestion.util.NormEURRenderer;
import gestion.util.NormUCRenderer;

public class TableauAExecuter extends JTable {

	public TableauAExecuter(TableModel arg0) {
		super(arg0);
		// Les colonnes : {"Placement", "Volume (UC)", "Cours ordre", "Dernier cours", "Date cours"}
		
		// Colonnes au format monétaire (deux décimales)
		this.getColumn("Cours ordre").setCellRenderer(new NormEURRenderer());
		this.getColumn("Dernier cours").setCellRenderer(new NormEURRenderer());
		// Colonne au format UC (quatre décimales)
		this.getColumn("Volume (UC)").setCellRenderer(new NormUCRenderer());
	}

}
