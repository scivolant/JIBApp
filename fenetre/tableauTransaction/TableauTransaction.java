package gestionSuivi.fenetre.tableauTransaction;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.LinkedList;
import java.util.List;

import javax.swing.DefaultCellEditor;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
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
	    
	    GridLayout gl = new GridLayout(1,2);
	    gl.setHgap(5);
	    JPanel pan = new JPanel(gl);
	    pan.add(nouvelleLigne);
	    pan.add(sauvegarde);
	    
	    this.add(new JScrollPane(tableau), BorderLayout.CENTER);
	    this.add(pan, BorderLayout.SOUTH);
	    

	}
}
