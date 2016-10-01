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
import javax.swing.BoxLayout;

import javax.swing.JButton;
import javax.swing.JLabel;

import javax.swing.JCheckBox;

public class FenetreAccueil extends JPanel {
	private JPanel panGauche = new JPanel();
	private JPanel panDroite = new JPanel();
	
	private JComboBox combo;
	private JLabel label = new JLabel();
	
	public FenetreAccueil(){
		super();
		GridLayout gl = new GridLayout(); 
		this.setLayout(gl);
		
		/*
		// =============================
		// Définition du panneau gauche
		// =============================
		panGauche.setLayout(new GridLayout(2,1));
		
		// Comptes à sélectionner :
		JPanel hautG = new JPanel();
		JLabel labelGG = new JLabel("Comptes : ");
		JPanel panCompte = new JPanel();
		panCompte.setLayout(new BoxLayout(panCompte, BoxLayout.PAGE_AXIS));
		String[] tabCompte = {"Compte 1", "Compte 2", "Compte 3"};
		JCheckBox[] tab_check = new JCheckBox[tabCompte.length];
		for (int i = 0; i<tabCompte.length; i++){
			tab_check[i]=new JCheckBox(tabCompte[i]);
			panCompte.add(tab_check[i]);
		}
		hautG.add(labelGG);
		hautG.add(panCompte);
		
		// Examen de placements...
		label.setText("Examiner : ");
		String[] tab = {"","Placement 1","Placement 2", "Placement 3"};
		combo = new JComboBox(tab);
		combo.setPreferredSize(new Dimension(200,20));
		// Ajout du Listener
		//combo.addActionListener(new ItemAction());
		
		JPanel basG = new JPanel();
		basG.add(label);
		basG.add(combo);
		
		panGauche.add(hautG);
		panGauche.add(basG);
		*/

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
		this.add(panDroite);
		this.setVisible(true);
	}
}
	
	/*
	class ItemAction implements ActionListener{
		public void actionPerformed(ActionEvent e){
			System.out.println("Examiner "+combo.getSelectedItem());
			if (combo.getSelectedItem().equals(""))
				gestionSuivi.setContent(gestionSuivi.fenetrePlacement);
		}
	}
*/
