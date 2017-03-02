package gestion.operation;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedList;

import javax.swing.DefaultCellEditor;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import gestion.accueil.ButtonRenderer;
import gestion.compta.Compte;
import gestion.compta.Ordre;
import gestion.compta.Placement;
import gestion.compta.Transaction;
import gestion.data.DataCenter;
import gestion.data.dao.OrdreDAO;
import gestion.util.ButtonDeleteEditor;
import gestion.util.FloatEditor;
import gestion.util.NormEURRenderer;
import gestion.util.NormUCRenderer;

/* 
 * Classe fille de TableauCommun, spécialisée à Ordre (sans panneau synthèse)
 * 
 */

public class TableauOrdres extends TableauCommun<Ordre>{
	
	public TableauOrdres(){
		super(new OrdresModel());
	    
		LinkedList<Compte> listeCompte = DataCenter.getCompteDAO().getData();
		// Combo box avec les comptes dispos
	    JComboBox<Compte> combo = new JComboBox<Compte>(listeCompte.toArray(new Compte[listeCompte.size()]));
	    
	    this.tableau.getColumn("Compte").setCellEditor(new DefaultCellEditor(combo));
	    
	    // normalise l'affichage en Euros
	    this.tableau.getColumn("Cours cible").setCellRenderer(new NormEURRenderer());
	    	    
	    this.tableau.getColumn("Achat (UC)").setCellRenderer(new NormUCRenderer());
	    this.tableau.getColumn("Vente (UC)").setCellRenderer(new NormUCRenderer());
	    
	    // Définition des éditeurs
	    this.tableau.getColumn("Cours cible").setCellEditor(new FloatEditor(4));
	    
	    this.tableau.getColumn("Achat (UC)").setCellEditor(new FloatEditor(4));
	    this.tableau.getColumn("Vente (UC)").setCellEditor(new FloatEditor(4));
	    
	    // Dans ce tableau, c'est le total en EUR qui est calculé (sur la base du cours estimé
	    this.tableau.getColumn("Add (€)").setCellRenderer(new ComputationEURRenderer());
	    this.tableau.getColumn("Dim (€)").setCellRenderer(new ComputationEURRenderer());
	    
	    this.tableau.getColumn("Suppr.").setCellRenderer(new ButtonRenderer());
	    this.tableau.getColumn("Suppr.").setCellEditor(new ButtonDeleteEditor(new JCheckBox()));
	    
	}
	
	public JTable getTableau(){
		return this.tableau;
	}
}