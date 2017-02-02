package gestionSuivi.fenetrePlacement;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.DefaultCellEditor;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import gestionSuivi.compte.Compte;
import gestionSuivi.data.DataIni;
import gestionSuivi.data.DataInterf;
import gestionSuivi.placement.Placement;

/* 
 * Comporte également les fonctions de sauvegarde du tableau (dans un fichier donné par dataInterf.nameSvg(place) )
 * 
 */

public class TableauOrdres extends JPanel{
	private Placement place;
	private JTable tableau;
	private JButton nouvelleLigne = new JButton("Ajouter une ligne");
	private JButton sauvegarde = new JButton("Svg/MàJ");
	
	public TableauOrdres(Placement place){
		super(new BorderLayout());
		this.place = place;
		
		DataInterf dataInterf = DataIni.getInstance();
		
		// Chercher la sauvegarde pour le placement concerné :
		Object[][] data = dataInterf.lireDataOrdres(place);
		
	    //Les titres des colonnes
	    String  title[] = {"Compte", "Cours cible", "Achat (UC)", "Vente (UC)", "Add (€)", "Dim (€)", "Notes", "Suppr."};
	    
	    JComboBox combo = new JComboBox(Compte.getNames());
	    
	    // initialise le modèle 
	    // puis lui in qu'il n'est pas de type "Transactions" (seconde ligne).
	    ZModel model = new ZModel(data, title);
	    model.setIsTransactions(false);
	    
	    this.tableau = new JTable(model);
	    this.tableau.setRowHeight(30);
	    
	    
	    
	    this.tableau.getColumn("Compte").setCellEditor(new DefaultCellEditor(combo));
	    
	    // normalise l'affichage en Euros
	    this.tableau.getColumn("Cours cible").setCellRenderer(new NormEURRenderer());
	    	    
	    this.tableau.getColumn("Achat (UC)").setCellRenderer(new NormUCRenderer());
	    this.tableau.getColumn("Vente (UC)").setCellRenderer(new NormUCRenderer());
	    
	    // Dans ce tableau, c'est le total en EUR qui est calculé (sur la base du cours estimé
	    this.tableau.getColumn("Add (€)").setCellRenderer(new ComputationEURRenderer());
	    this.tableau.getColumn("Dim (€)").setCellRenderer(new ComputationEURRenderer());
	    
	    this.tableau.getColumn("Suppr.").setCellRenderer(new ButtonRenderer());
	    this.tableau.getColumn("Suppr.").setCellEditor(new ButtonEditor(new JCheckBox()));
	    
		class AddListener implements ActionListener{
			public void actionPerformed(ActionEvent event){		
				((ZModel)tableau.getModel()).addRow(dataInterf.defaultLineOrdres());
			}
		}
		
		class SvgListenerOrdres implements ActionListener{
			ZModel model;
			
			public SvgListenerOrdres(ZModel model){
				super();
				this.model=model;
			}
			
			public void actionPerformed(ActionEvent event){
				// Sauvegarde des données modifiées
				dataInterf.svgDataOrdres(place, model);
			}
		}
	    
	    nouvelleLigne.addActionListener(new AddListener());
	    sauvegarde.addActionListener(new SvgListenerOrdres(model));
	    
	    GridLayout gl1 = new GridLayout(1,3);
	    gl1.setHgap(5);
	    JPanel pan1 = new JPanel(gl1);
	    pan1.add(nouvelleLigne);
	    pan1.add(sauvegarde);
	    
	    this.add(new JScrollPane(tableau), BorderLayout.CENTER);
	    this.add(pan1, BorderLayout.SOUTH);
	}
	
	public JTable getTableau(){
		return this.tableau;
	}
}