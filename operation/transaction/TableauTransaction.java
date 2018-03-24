package gestion.operation.transaction;

import java.awt.BorderLayout;

import javax.swing.ComboBoxModel;
import javax.swing.DefaultCellEditor;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JTable;

import gestion.accueil.ButtonRenderer;
import gestion.compta.Compte;
import gestion.compta.Transaction;
import gestion.data.DataCenter;
import gestion.util.ButtonDeleteEditor;
import gestion.util.DateEditor;
import gestion.util.FloatEditor;
import gestion.util.NormEURRenderer;
import gestion.util.NormUCRenderer;
import gestion.util.TableauCommun;

/* 
 * Classe fille de TableauCommun, spécialisée à Transaction (avec un panneau synthèse)
 * 
 */

public class TableauTransaction extends TableauCommun<Transaction>{
	private PanSynthese panSynthese;
	private JComboBox<Compte> combo;
	
	public TableauTransaction(){
		super(new TransModel());
		
		DataCenter dataSql = DataCenter.getInstance();
		
	    //Pour mémoire, les titres des colonnes (vrai title dans "TransModel")
	    //String  title[] = {"Date", "Compte", "Cours", "Add (UC)", "Dim (UC)", "Add (€)", "Dim (€)", "Suppr."};
	    
		// Combo box avec les comptes dispos
		Compte[] listeCompte = DataCenter.getComptesCourants();
	    combo = new JComboBox<Compte>(listeCompte);

	    	    
	    this.tableau.getColumn("Compte").setCellEditor(new DefaultCellEditor(combo));
	    
	    this.tableau.getColumn("Cours").setCellRenderer(new ComputationRenderer());
	    
	    // définition des éditeurs...
	    this.tableau.getColumn("Add (€)").setCellEditor(new FloatEditor(2));
	    this.tableau.getColumn("Dim (€)").setCellEditor(new FloatEditor(2));
	    
	    this.tableau.getColumn("Add (UC)").setCellEditor(new FloatEditor(4));
	    this.tableau.getColumn("Dim (UC)").setCellEditor(new FloatEditor(4));
	    
	    this.tableau.getColumn("Date").setCellEditor(new DateEditor());
	    
	    // normalise l'affichage en Euros
	    this.tableau.getColumn("Add (€)").setCellRenderer(new NormEURRenderer());
	    this.tableau.getColumn("Dim (€)").setCellRenderer(new NormEURRenderer());
	    
	    // normalise l'affichage en UC
	    this.tableau.getColumn("Add (UC)").setCellRenderer(new NormUCRenderer());
	    this.tableau.getColumn("Dim (UC)").setCellRenderer(new NormUCRenderer());
	    
	    this.tableau.getColumn("Suppr.").setCellRenderer(new ButtonRenderer());
	    this.tableau.getColumn("Suppr.").setCellEditor(new ButtonDeleteEditor(new JCheckBox()));
	    
	    panSynthese = new PanSynthese(dataSql.getDataPanSynthese());
	    
	    pan.add(panSynthese, BorderLayout.NORTH);
	}
	
	public JTable getTableau(){
		return this.tableau;
	}
	
	public void updateTableau(){
		// Updates the TableauTransaction using fresh data from DataBase.
		
		//System.out.println("TableauTransaction.updateTableau() appelé");
		DataCenter dataCenter = DataCenter.getInstance();
		
		// Mise à jour du panneau synthèse
		this.panSynthese.setData(dataCenter.getDataPanSynthese());
		
		// Mise à jour des données du tableau
		// NB : à partir de la base de données
		((TransModel)tableau.getModel()).updateData();
	}
	
	public void svgMaJTableau(){
		// Sauvegarde des données modifiées du tableau
		int i = 0;
		TransModel model = (TransModel)tableau.getModel();
		for (Transaction obj : model.getData()){
			if (model.getDaoT().update(obj)){
				i++;		
			} else {};
		};
		System.out.println("TableauTransaction.svgMaJTableau : Svg de "+i+" ligne(s).");
		model.updateData();

		
		// Mise à jour du panneau synthèse
		DataCenter dataCenter = DataCenter.getInstance();
		this.panSynthese.setData(dataCenter.getDataPanSynthese());
	}
	
	public void updateCombo(){
		Compte[] comptes = DataCenter.getComptesCourants();
		ComboBoxModel<Compte> model = new DefaultComboBoxModel<Compte>(comptes);
		this.combo.setModel(model);
	}

}
