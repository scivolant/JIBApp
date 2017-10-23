package gestion.operation;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashSet;
import java.util.LinkedList;

import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

import gestion.BoutonCompte;
import gestion.compta.Placement;
import gestion.data.DataCenter;
import gestion.operation.transaction.BoiteDialogueCreationLignes;
import gestion.operation.transaction.TableauTransaction;
import gestion.util.FenetreCommun;

public class FenetreOperation extends FenetreCommun{
	protected JComboBox<Placement> comboPlacement;
	protected TableauTransaction tabTrans = new TableauTransaction();
	protected TableauOrdres tabOrdres = new TableauOrdres();
	protected TableauCours tabCours = new TableauCours();
	protected JButton predefBouton;
	protected HashSet<Integer> predefSet;
	protected BoutonCompteListener boutonCompteListener = new BoutonCompteListener();

	public BoutonCompteListener getBoutonCompteListener() {
		return boutonCompteListener;
	}

	public TableauTransaction getTabTrans() {
		return tabTrans;
	}

	public TableauOrdres getTabOrdres() {
		return tabOrdres;
	}

	public TableauCours getTabCours() {
		return tabCours;
	}

	public FenetreOperation(){
		super();
		
		JTabbedPane tabbedPane = new JTabbedPane();
		
		// Combo de sélection de placement
		LinkedList<Placement> listePlacement = DataCenter.getPlacementDAO().getData();
		Placement[] vectPlacement = listePlacement.toArray(new Placement[listePlacement.size()]);
		comboPlacement = new JComboBox<Placement>(vectPlacement);
		comboPlacement.setPreferredSize(new Dimension(200,30));
		
		// Gestion de la sélection de la page
		comboPlacement.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				DataCenter dataCenter = DataCenter.getInstance();
				Placement place = (Placement)comboPlacement.getSelectedItem();
				dataCenter.updatePlacement(place);
				updateFenetre();
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
		tool.add(comboPlacement);

		// Ajout des onglets pour la zone centrale
		tabbedPane.addTab("Transactions",tabTrans);
		tabbedPane.addTab("Ordres",tabOrdres);
		tabbedPane.addTab("Cours",tabCours);
		
		this.setLayout(new BorderLayout());
		this.add(tool, BorderLayout.NORTH);
		this.add(tabbedPane, BorderLayout.CENTER);
	}
	
	@Override
	public void updateFenetre(){
		/*
		 * Redéfini la méthode updateFenetre de FenetreCommun.
		 */
		
		// Opération prédéfinie disponible ou non...
		Placement place = DataCenter.getInstance().getPlaceCourant();
		try {
			predefBouton.setEnabled(predefSet.contains(place.getIndex()));
		} catch (NullPointerException e) {
			predefBouton.setEnabled(false);
		}
		
		
		// MàJ des données
		tabTrans.updateTableau();
		tabOrdres.getModel().updateData();
		tabCours.getModel().updateData();
		
		// MàJ du combo dans tabTrans et tabOrdres
		tabTrans.updateCombo();
		tabOrdres.updateCombo();
		
		// MàJ du combo dans FenetreOperation
		updateComboPlacement();
	}
	
	public JComboBox<Placement> getComboPlacement(){
		return comboPlacement;
	}
	
	// ActionListener pour les boutons de sélection de compte
	class BoutonCompteListener implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			// First, notify DataCenter to update compteCourants
			DataCenter dataCenter = DataCenter.getInstance();
			dataCenter.updateComptesCourants(((BoutonCompte)e.getSource()).getCompte(), ((JCheckBox)e.getSource()).isSelected());
			
			// Update FenetreOperation
			updateFenetre();		
		}
	}
	
	// Mise à jour du combo des placements (et de l'entrée sélectionnée)
	public void updateComboPlacement(){
		
		DataCenter dataCenter = DataCenter.getInstance();
		LinkedList<Placement> listePlacement = DataCenter.getPlacementDAO().getData();
		int size = listePlacement.size();
		Placement[] vectPlacement = listePlacement.toArray(new Placement[size]);
		
		ComboBoxModel<Placement> model = new DefaultComboBoxModel<Placement>(vectPlacement);
		this.comboPlacement.setModel(model);
		
		try {
			int indPlaceCourant = dataCenter.getPlaceCourant().getIndex();
			for (int index = 0; index < size ; index++){
				if (vectPlacement[index].getIndex()== indPlaceCourant){
					this.comboPlacement.setSelectedIndex(index);
					System.out.println("== FenetreOperation.updateComboPlacement(), trouvé = "+index);
					break;
				}
			}
		} catch (NullPointerException e) {
			// pas de PlaceCourant dispo
			// ne rien faire
		}
	}

}
