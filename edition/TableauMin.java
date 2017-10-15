package gestion.edition;

import javax.swing.JCheckBox;
import javax.swing.JTable;

import gestion.accueil.ButtonRenderer;
import gestion.util.ButtonDeleteEditor;
import gestion.util.TableauCommun;
import gestion.util.ZModel;

/* 
 * Classe fille de TableauCommun, sans panneau synthèse
 * Utilisé pour Compte et Type en part.
 * 
 */

public class TableauMin<T> extends TableauCommun<T>{
	
	public TableauMin(ZModel<T> model){
		super(model);
	    
	    this.tableau.getColumn("Suppr.").setCellRenderer(new ButtonRenderer());
	    this.tableau.getColumn("Suppr.").setCellEditor(new ButtonDeleteEditor(new JCheckBox()));
	    
	}
	
	public JTable getTableau(){
		return this.tableau;
	}
}