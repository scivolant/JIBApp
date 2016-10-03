package gestionSuivi.fenetre.tableauTransaction;

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

public class TableauTransaction extends JPanel{
	private Placement place;
	private JTable tableau;
	private ZModel model;
	private JButton nouvelleLigne = new JButton("Ajouter une ligne");
	private JButton sauvegarde = new JButton("Sauvegarde");
	
	public TableauTransaction(Placement place){
		super(new BorderLayout());
		this.place = place;
		
		DataInterf dataInterf = new DataIni();
		
		// Chercher la sauvegarde pour le placement concerné :
		Object[][] data = dataInterf.lireData(place);
		
	    //Les titres des colonnes
	    String  title[] = {"Date", "Compte", "Prix unit.", "Add (UC)", "Dim (UC)", "Add (€)", "Dim (€)", "Suppr."};
	    
		Compte[] listeCompte = Compte.values();
	    JComboBox combo = new JComboBox(listeCompte);
	    
	    ZModel model = new ZModel(data, title);
	    this.tableau = new JTable(model);
	    this.tableau.setRowHeight(30);
	    
	    this.tableau.getColumn("Prix unit.").setCellRenderer(new ComputationRenderer());
	    
	    this.tableau.getColumn("Compte").setCellEditor(new DefaultCellEditor(combo));
	    
	    this.tableau.getColumn("Suppr.").setCellRenderer(new ButtonRenderer());
	    this.tableau.getColumn("Suppr.").setCellEditor(new ButtonEditor(new JCheckBox()));
	    
		class AddListener implements ActionListener{
			public void actionPerformed(ActionEvent event){		
				((ZModel)tableau.getModel()).addRow(dataInterf.defaultLine());
			}
		}
		
		class SvgListener implements ActionListener{
			ZModel model;
			
			public SvgListener(ZModel model){
				super();
				this.model=model;
			}
			
			public void actionPerformed(ActionEvent event){
				DataInterf dataInterf = new DataIni();
				dataInterf.svgData(place, model);
			}
		}
	    
	    nouvelleLigne.addActionListener(new AddListener());
	    sauvegarde.addActionListener(new SvgListener(model));
	    
	    GridLayout gl1 = new GridLayout(1,2);
	    gl1.setHgap(5);
	    JPanel pan1 = new JPanel(gl1);
	    pan1.add(nouvelleLigne);
	    pan1.add(sauvegarde);
	    
	    GridLayout gl2 = new GridLayout(1,6);
	    gl2.setHgap(5);
	    JPanel pan2 = new JPanel(gl2);
	    pan2.add(new JLabel("Prix moyen : "));
	    pan2.add(new JLabel(String.format("%.4f",dataInterf.prixMoyen(place, model))));
	    pan2.add(new JLabel("Total UC : "));
	    pan2.add(new JLabel(String.format("%.4f",dataInterf.totalUC(place, model))));
	    pan2.add(new JLabel("Total EUR : "));
	    pan2.add(new JLabel(String.format("%.2f",dataInterf.totalEUR(place, model))));
	    
	    JPanel pan = new JPanel(new GridLayout(2,1));
	    pan.add(pan2);
	    pan.add(pan1);
	    
	    this.add(new JScrollPane(tableau), BorderLayout.CENTER);
	    this.add(pan, BorderLayout.SOUTH);
	    

	}
}
