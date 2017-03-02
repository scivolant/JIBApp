package gestion.edition;

import java.awt.BorderLayout;

import javax.swing.JPanel;
import javax.swing.JTabbedPane;

import gestion.data.DataCenter;

public class FenetreEdition extends JPanel {
	
	/*
	 * 
	 * Fenetre pour éditer les différents éléments de compta
	 * 
	 * 
	 */

	public FenetreEdition(){
		super();
		
		JTabbedPane tabbedPane = new JTabbedPane();
		TableauMin tabCompte = new TableauMin(new CompteModel());
		TableauMin tabType = new TableauMin(new TypeModel());
		TableauPlacement tabPlacement = new TableauPlacement();
		TableauSourceQuote tabSourceQuote = new TableauSourceQuote();
		
		tabbedPane.addTab("Compte",tabCompte);
		tabbedPane.addTab("Type",tabType);
		tabbedPane.addTab("Placement",tabPlacement);
		tabbedPane.addTab("Source MàJ",tabSourceQuote);
		
		this.setLayout(new BorderLayout());
		this.add(tabbedPane, BorderLayout.CENTER);
	}

}
