package gestion;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedList;

import javax.swing.BoxLayout;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import gestion.accueil.FenetreAccueil;
import gestion.aexecuter.FenetreAExecuter;
import gestion.compta.Compte;
import gestion.data.DataCenter;
import gestion.edition.FenetreEdition;
import gestion.operation.FenetreOperation;


public class GestionSuivi {
	private JFrame fg;
	private JPanel content;
	private FenetreAccueil fenetreAccueil;
	private FenetreOperation fenetreOperation;
	
	private String[] listContent;
	
	private Color secondColor = Color.lightGray;
	
	public GestionSuivi(){
		JFrame fg = new JFrame();
		content = new JPanel();
		fg.setTitle("Gestion & suivi");
		fg.setSize(700, 600);
		fg.setLocationRelativeTo(null);
		fg.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		CardLayout cl = new CardLayout();
		content.setLayout(cl);
		
		DataCenter dataCenter = DataCenter.getInstance();
		
		// Cr�ation des diff�rentes fen�tres et ajout dans "content"
		fenetreAccueil = new FenetreAccueil();
		fenetreOperation = new FenetreOperation();
				
		listContent=new String[] {"Accueil", "Op�rations et cours", "� ex�cuter", "Edition"};
		
		content.add(fenetreAccueil, listContent[0]);
		content.add(fenetreOperation, listContent[1]);
		content.add(new FenetreAExecuter(), listContent[2]);
		content.add(new FenetreEdition(), listContent[3]);
		
		// ===============================
		// Choix de la page � afficher (haut de la fen�tre)
		// ===============================
		JLabel label = new JLabel("S�lection de la page : ");
		JComboBox<String> combo = new JComboBox<String>(listContent);
		combo.setPreferredSize(new Dimension(200,20));
		// Gestion de la s�lection de la page
		combo.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				cl.show(content, listContent[combo.getSelectedIndex()]);
			}
		});
		
		JPanel panHaut = new JPanel();
		panHaut.add(label);
		panHaut.add(combo);
		panHaut.setBackground(secondColor);
		
		// ===============================
		// Choix des comptes � consid�rer (c�t� gauche de la fen�tre)
		// ===============================
		LinkedList<Compte> listCompte = dataCenter.getCompteDAO().getData();
		int n = listCompte.size();
		
		JPanel panCompte = new JPanel();
		panCompte.setLayout(new BoxLayout(panCompte, BoxLayout.PAGE_AXIS));
		// initialise avec BoutonCompte. NB : listener int�gr� !
		JCheckBox[] tab_check = new BoutonCompte[n];
		panCompte.add(new JLabel("Choix de comptes"));
		// cr�ation des CheckBoxes pour les diff�rents comptes
		// + ajout de ceux ci � panCompte
		for (int i = 0; i<n; i++){
			tab_check[i]=new BoutonCompte(listCompte.get(i));
			tab_check[i].setBackground(secondColor);
			panCompte.add(tab_check[i]);
		}
		panCompte.setBackground(secondColor);
		
		/*
		 * Initialisation finale des �l�ments
		 */
		try{
			// si possible, on s�lectionne �l�ment
			fenetreOperation.getCombo().setSelectedIndex(0);
			fenetreOperation.updateFenetrePlacement();
		} catch (Exception e){
			// sinon, on ne fait rien
		}
		// Recherche des ordres � effectuer :
		fenetreAccueil.updateOrdres();
		
		// Positionnement des diff�rents �l�ments
		fg.getContentPane().setLayout(new BorderLayout());
		fg.getContentPane().add(panHaut, BorderLayout.NORTH);
		fg.getContentPane().add(content, BorderLayout.CENTER);
		fg.getContentPane().add(panCompte, BorderLayout.WEST);
		
		fg.add(content);
		fg.setVisible(true);
	}
	
	public JFrame getFrame(){
		return this.fg;
	}
	
	public static void main(String[] args){
		new GestionSuivi();
	}
}
