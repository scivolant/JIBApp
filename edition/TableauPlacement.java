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
import gestion.util.ButtonDeleteEditor;
import gestion.util.TableauCommun;

/* 
 * Classe fille de TableauCommun, spécialisée à Ordre (sans panneau synthèse)
 * 
 */

public class TableauPlacement extends TableauCommun<Placement>{
    private JComboBox<GestionType> comboType;
    private JComboBox<SourceQuote> comboSource;
	
	public TableauPlacement(){
		super(new PlacementModel());
		
		// Pour mémoire, liste des colonnes :
		// {"Nom", "Type", "ISIN", "Code MàJ", "Source", "Suppr."}
	    
		// Combo box avec les types dispos
		LinkedList<GestionType> listeType = DataCenter.getGestionTypesDAO().getData();
	    comboType = new JComboBox<GestionType>(listeType.toArray(new GestionType[listeType.size()]));
	    this.tableau.getColumn("Type").setCellEditor(new DefaultCellEditor(comboType));
	    
		// Combo box avec les sources dispos
		LinkedList<SourceQuote> listeSource = DataCenter.getSourceQuoteDAO().getData();
	    comboSource = new JComboBox<SourceQuote>(listeSource.toArray(new SourceQuote[listeSource.size()]));
	    this.tableau.getColumn("Source").setCellEditor(new DefaultCellEditor(comboSource));
	    
	    this.tableau.getColumn("Suppr.").setCellRenderer(new ButtonRenderer());
	    this.tableau.getColumn("Suppr.").setCellEditor(new ButtonDeleteEditor(new JCheckBox()));
	    
	}
	
	public JTable getTableau(){
		return this.tableau;
	}
	
	// redéfinition action effectuée par le bouton "Svg/MàJ"
	public void svgMaJTableau(){
		// MàJ des combos :
		LinkedList<GestionType> listeType = DataCenter.getGestionTypesDAO().getData();
	    comboType = new JComboBox<GestionType>(listeType.toArray(new GestionType[listeType.size()]));
	    this.tableau.getColumn("Type").setCellEditor(new DefaultCellEditor(comboType));
	    
		LinkedList<SourceQuote> listeSource = DataCenter.getSourceQuoteDAO().getData();
	    comboSource = new JComboBox<SourceQuote>(listeSource.toArray(new SourceQuote[listeSource.size()]));
	    this.tableau.getColumn("Source").setCellEditor(new DefaultCellEditor(comboSource));
	    
		// Sauvegarde des données modifiées (comporte un "fireTableDataChanged" dans model.updateData())
		int i = 0;
		for (Placement obj : model.getData()){
			if (model.getDAOtableau().update(obj)){
				i++;						
			} else {};
		};
		System.out.println("TableauPlacement.SvgListener : Svg de "+i+" ligne(s).");
		model.updateData();
	}
}