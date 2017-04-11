package gestion.edition;

import java.util.LinkedList;

import javax.swing.DefaultCellEditor;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JTable;

import gestion.accueil.ButtonRenderer;
import gestion.compta.GestionType;
import gestion.compta.SourceQuote;
import gestion.data.quotation.Transfer;
import gestion.util.ButtonDeleteEditor;
import gestion.util.TableauCommun;

/* 
 * Classe fille de TableauCommun, sp�cialis�e � Ordre (sans panneau synth�se)
 * 
 */

public class TableauSourceQuote extends TableauCommun<SourceQuote>{
	
	public TableauSourceQuote(){
		super(new SourceQuoteModel());
		
		// Pour m�moire, liste des colonnes :
		// {"Nom", "URL", "Transf", "Suppr."}
	    
		// Combo box avec les comptes dispos
	    JComboBox<Transfer> combo = new JComboBox<Transfer>(Transfer.values());
	    
	    this.tableau.getColumn("Transf.").setCellEditor(new DefaultCellEditor(combo));
	    
	    /*
	    // normalise l'affichage en Euros
	    this.tableau.getColumn("Cours cible").setCellRenderer(new NormEURRenderer());
	    	    
	    this.tableau.getColumn("Achat (UC)").setCellRenderer(new NormUCRenderer());
	    this.tableau.getColumn("Vente (UC)").setCellRenderer(new NormUCRenderer());
	    
	    // D�finition des �diteurs
	    this.tableau.getColumn("Cours cible").setCellEditor(new FloatEditor(4));
	    
	    this.tableau.getColumn("Achat (UC)").setCellEditor(new FloatEditor(4));
	    this.tableau.getColumn("Vente (UC)").setCellEditor(new FloatEditor(4));
	    
	    // Dans ce tableau, c'est le total en EUR qui est calcul� (sur la base du cours estim�
	    this.tableau.getColumn("Add (�)").setCellRenderer(new ComputationEURRenderer());
	    this.tableau.getColumn("Dim (�)").setCellRenderer(new ComputationEURRenderer());
	    */
	    
	    this.tableau.getColumn("Suppr.").setCellRenderer(new ButtonRenderer());
	    this.tableau.getColumn("Suppr.").setCellEditor(new ButtonDeleteEditor(new JCheckBox()));
	    
	}
	
	public JTable getTableau(){
		return this.tableau;
	}
}