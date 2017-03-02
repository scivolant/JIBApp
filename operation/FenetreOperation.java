package gestion.operation;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashSet;
import java.util.LinkedList;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

import gestion.compta.Placement;
import gestion.data.DataCenter;
import gestion.operation.transaction.BoiteDialogueCreationLignes;
import gestion.operation.transaction.TableauTransaction;

public class FenetreOperation extends JPanel {
	protected JComboBox<Placement> combo;
	protected TableauTransaction tabTrans = new TableauTransaction();
	protected TableauOrdres tabOrdres = new TableauOrdres();
	protected TableauCours tabCours = new TableauCours();
	protected JButton predefBouton;
	protected HashSet<Integer> predefSet;

	public FenetreOperation(){
		super();
		
		JTabbedPane tabbedPane = new JTabbedPane();
		
		// Combo de sélection de placement
		LinkedList<Placement> listePlacement = DataCenter.getPlacementDAO().getAll();
		Placement[] vectPlacement = listePlacement.toArray(new Placement[listePlacement.size()]);
		combo = new JComboBox<Placement>(vectPlacement);
		combo.setPreferredSize(new Dimension(200,30));
		
		// Gestion de la sélection de la page
		combo.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				updateFenetrePlacement();
			}
		});
		
		/*
		// si le placement est LyxorETFEuro (ind = 3) ou LyxorETFDJ (ind = 6), 
		// alors on ajoute un bouton avec une opération prédéfinie...
		*/
		predefSet = new HashSet<Integer>();
		predefSet.add(3);
		predefSet.add(6);
		
	    predefBouton = new JButton("Achats prédéf.");
	    predefBouton.setEnabled(false);
	    predefBouton.addActionListener(new ActionListener(){
	      public void actionPerformed(ActionEvent arg0){
	    	  BoiteDialogueCreationLignes boite = new BoiteDialogueCreationLignes(tabTrans, predefSet);
	      }
	    });
	    
		// "Toolbar" pour pouvoir créer des opérations prédéfinies.
	    GridLayout gl = new GridLayout(1,3);
	    gl.setHgap(5);
		JPanel tool = new JPanel(gl);
		tool.add(predefBouton);
		JLabel label0 = new JLabel("Placement sélectionné :");
		tool.add(label0);
		tool.add(combo);

		// Ajout des onglets pour la zone centrale
		tabbedPane.addTab("Transactions",tabTrans);
		tabbedPane.addTab("Ordres",tabOrdres);
		tabbedPane.addTab("Cours",tabCours);
		
		this.setLayout(new BorderLayout());
		this.add(tool, BorderLayout.NORTH);
		this.add(tabbedPane, BorderLayout.CENTER);
	}
	
	public void updateFenetrePlacement(){
		DataCenter dataCenter = DataCenter.getInstance();
		Placement place = (Placement)combo.getSelectedItem();
		dataCenter.updatePlacement(place);
		predefBouton.setEnabled(predefSet.contains(place.getIndex()));
		
		
		// MàJ des données
		tabTrans.updateTableau();
		tabOrdres.getModel().updateData();
		tabCours.getModel().updateData();
	}
	
	public JComboBox<Placement> getCombo(){
		return combo;
	}

}
