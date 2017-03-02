package gestion.edition;

import javax.swing.JCheckBox;
import javax.swing.JTable;

import gestion.accueil.ButtonRenderer;
import gestion.operation.TableauCommun;
import gestion.util.ButtonDeleteEditor;
import gestion.util.ZModel;

/* 
 * Classe fille de TableauCommun, spécialisée à Ordre (sans panneau synthèse)
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