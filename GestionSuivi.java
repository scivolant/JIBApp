package gestionSuivi;

import gestionSuivi.fenetrePlacement.FenetrePlacement;
import gestionSuivi.placement.Placement;
import gestionSuivi.compte.Compte;
import gestionSuivi.fenetreAccueil.FenetreAccueil;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JComboBox;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;


public class GestionSuivi {
	private JFrame fg;
	private JPanel content;
	private JPanel fenetreAccueil;
	
	private String[] listContent;
	private JPanel[] tab_fenPlacement;
	private Compte[] listCompte = Compte.values();
	
	private int indice = 0;
	private Color secondColor = Color.lightGray;
	
	public GestionSuivi(){
		JFrame fg = new JFrame();
		JPanel content = new JPanel();
		fg.setTitle("Gestion des placements");
		fg.setSize(700, 600);
		fg.setLocationRelativeTo(null);
		fg.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		CardLayout cl = new CardLayout();
		content.setLayout(cl);
		
		// Création des différentes fenêtres et ajout dans "content"
		JPanel fenetreAccueil = new FenetreAccueil();
		
		listContent=new String[(Placement.values().length+1)];
			listContent[0]="Accueil";
			int index =0;
			for (String name:Placement.getNames()){
				listContent[++index]=name;
			}
		
		tab_fenPlacement = new JPanel[listContent.length];
		
		content.add(fenetreAccueil, listContent[0]);
		for (int i=1; i< listContent.length; i++){
			tab_fenPlacement[i]= new FenetrePlacement(Placement.values()[i-1]);
			content.add(tab_fenPlacement[i], listContent[i]);
		}
		
		// ===============================
		// Choix de la page à afficher (haut de la fenêtre)
		// ===============================
		JLabel label = new JLabel("Sélection de la page : ");
		JComboBox combo = new JComboBox(listContent);
		combo.setPreferredSize(new Dimension(200,20));
		// Gestion de la sélection de la page
		combo.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				//System.out.println("Examiner "+combo.getSelectedIndex());
				cl.show(content, listContent[combo.getSelectedIndex()]);
			}
		});
		
		JPanel panHaut = new JPanel();
		panHaut.add(label);
		panHaut.add(combo);
		panHaut.setBackground(secondColor);
		
		// ===============================
		// Choix des comptes à considérer (côté gauche de la fenêtre)
		// ===============================
		JPanel panCompte = new JPanel();
		panCompte.setLayout(new BoxLayout(panCompte, BoxLayout.PAGE_AXIS));
		JCheckBox[] tab_check = new JCheckBox[listCompte.length];
		panCompte.add(new JLabel("Choix de comptes"));
		for (int i = 0; i<listCompte.length; i++){
			tab_check[i]=new JCheckBox(listCompte[i].toString());
			tab_check[i].setBackground(secondColor);
			panCompte.add(tab_check[i]);
		}
		panCompte.setBackground(secondColor);
		
		// Positionnement des différents éléments
		fg.getContentPane().setLayout(new BorderLayout());
		fg.getContentPane().add(panHaut, BorderLayout.NORTH);
		fg.getContentPane().add(content, BorderLayout.CENTER);
		fg.getContentPane().add(panCompte, BorderLayout.WEST);
		
		fg.add(content);
		fg.setVisible(true);
	}
	

	
	public static void main(String[] args){
		new GestionSuivi();
	}
}
