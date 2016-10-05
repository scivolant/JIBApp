package gestionSuivi.fenetre;

import java.awt.GridLayout;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JPanel;

import gestionSuivi.placement.GestionTypes;

import javax.swing.BoxLayout;

import javax.swing.JButton;
import javax.swing.JLabel;

import javax.swing.JCheckBox;

public class FenetreAccueil extends JPanel {
	private JPanel panDroite = new JPanel();
	
	public FenetreAccueil(){
		super();
		BorderLayout bl = new BorderLayout(); 
		this.setLayout(bl);

		this.add(new JLabel("Répartition des sommes :"), BorderLayout.NORTH);
		
		int nbLigne = GestionTypes.values().length;
		System.out.println(nbLigne);
		JPanel tableauRepartition = new JPanel();
		GridLayout gl = new GridLayout(nbLigne+2,3);
		gl.setHgap(-5);
		tableauRepartition.setLayout(gl);
		// Première ligne
		tableauRepartition.add(new JLabel("Type"));
		tableauRepartition.add(new JLabel("Euros"));
		tableauRepartition.add(new JLabel("%"));
		// Lignes suivantes
		for (int i=0; i<nbLigne; i++){
			tableauRepartition.add(new JLabel(GestionTypes.values()[i].toString()));
			tableauRepartition.add(new JLabel("bla"));
			tableauRepartition.add(new JLabel("bli"));
		}
		
		this.add(tableauRepartition, BorderLayout.CENTER);
		
		// Définition du panneau droit
		panDroite.setLayout(new BorderLayout());
		JLabel hautD = new JLabel("Somme totale");
		JLabel labelDG = new JLabel("Répartition : ");
		JLabel labelDD = new JLabel("Obligations...");
		panDroite.add(labelDG, BorderLayout.WEST);
		panDroite.add(labelDD, BorderLayout.EAST);
		panDroite.add(hautD, BorderLayout.NORTH);
		
		// Ajout des deux panneaux...
		//this.add(panGauche);
		//this.add(panDroite);
		this.setVisible(true);
	}
}
