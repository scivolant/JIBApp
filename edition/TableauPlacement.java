package gestion.edition;

import java.util.LinkedList;

import javax.swing.DefaultCellEditor;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JTable;

import gestion.accueil.ButtonRenderer;
import gestion.compta.GestionType;
import gestion.compta.Placement;
import gestion.compta.SourceQuote;
import gestion.data.DataCenter;
import gestion.operation.TableauCommun;
import gestion.util.ButtonDeleteEditor;

/* 
 * Classe fille de TableauCommun, spécialisée à Ordre (sans panneau synthèse)
 * 
 */

public class TableauPlacement extends TableauCommun<Placement>{
	
	public TableauPlacement(){
		super(new PlacementModel());
		
		// Pour mémoire, liste des colonnes :
		// {"Nom", "Menmo", "Type", "ISIN", "Code MàJ", "Source", "Suppr."}
	    
		LinkedList<GestionType> listeType = DataCenter.getGestionTypesDAO().getData();
		// Combo box avec les comptes dispos
	    JComboBox<GestionType> comboType = new JComboBox<GestionType>(listeType.toArray(new GestionType[listeType.size()]));
	    
	    this.tableau.getColumn("Type").setCellEditor(new DefaultCellEditor(comboType));
	    
		LinkedList<SourceQuote> listeSource = DataCenter.getSourceQuoteDAO().getData();
		// Combo box avec les comptes dispos
	    JComboBox<SourceQuote> comboSource = new JComboBox<SourceQuote>(listeSource.toArray(new SourceQuote[listeSource.size()]));
	    
	    this.tableau.getColumn("Source").setCellEditor(new DefaultCellEditor(comboSource));
	    
	    this.tableau.getColumn("Suppr.").setCellRenderer(new ButtonRenderer());
	    this.tableau.getColumn("Suppr.").setCellEditor(new ButtonDeleteEditor(new JCheckBox()));
	    
	}
	
	public JTable getTableau(){
		return this.tableau;
	}
}