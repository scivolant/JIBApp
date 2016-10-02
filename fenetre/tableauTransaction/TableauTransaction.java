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
	private Compte[] listeCompte = Compte.values();
	private JButton nouvelleLigne = new JButton("Ajouter une ligne");
	private JButton sauvegarde = new JButton("Sauvegarde");
	
	public TableauTransaction(Placement place){
		super(new BorderLayout());
		this.place = place;
		
		DataInterf dataInterf = new DataIni();
		
		// Chercher s'il y a une sauvegarde pour le placement concerné :
	    ObjectInputStream ois;
	    Object[][] data;
	    try {	
	      //On récupère maintenant les données !
	      ois = new ObjectInputStream(
	              new BufferedInputStream(
	                new FileInputStream(
	                  new File(dataInterf.nameSvg(place)))));
	            
	      try {
	        data = (Object[][])ois.readObject();
		    System.err.println("Fichier retrouvé et lu... "+place.getName());
	      } catch (ClassNotFoundException e) {
		    System.err.println("Problème : ClassNotFoundException");
	        e.printStackTrace();
	    	data = dataInterf.defaultData();
	      }
		  
	    ois.close();
	        	
	    } catch (FileNotFoundException e) {
    	  System.err.println("Pas de sauvegarde trouvée, on prend les valeurs par défaut à la place !");
    	  data = dataInterf.defaultData();
	    } catch (IOException e) {
	      System.err.println("Autre problème IO...");
	      e.printStackTrace();
    	  data = dataInterf.defaultData();
	    }
		
	    //Les titres des colonnes
	    String  title[] = {"Date", "Compte", "Prix unit.", "Add (UC)", "Dim (UC)", "Add (€)", "Dim (€)", "Suppr."};
	    
	    JComboBox combo = new JComboBox(listeCompte);
	    
	    ZModel model = new ZModel(data, title);
	    this.tableau = new JTable(model);
	    this.tableau.setRowHeight(30);
	    
	    this.tableau.getColumn("Compte").setCellEditor(new DefaultCellEditor(combo));
	    
	    this.tableau.getColumn("Suppr.").setCellRenderer(new ButtonRenderer());
	    this.tableau.getColumn("Suppr.").setCellEditor(new ButtonEditor(new JCheckBox()));
	    
		class AddListener implements ActionListener{
			public void actionPerformed(ActionEvent event){		
				((ZModel)tableau.getModel()).addRow(dataInterf.defaultLine());
			}
		}
		
		class SvgListener implements ActionListener{
			Object[][] data;
			
			public SvgListener(Object[][] data){
				super();
				this.data=data;
			}
			
			public void actionPerformed(ActionEvent event){
				DataInterf dataInterf = new DataIni();
			    ObjectOutputStream oos;
			    try {	
			      //On envoie maintenant les données !
			      oos = new ObjectOutputStream(
			              new BufferedOutputStream(
			                new FileOutputStream(
			                  new File(dataInterf.nameSvg(place)))));
			            
			      try {
			        oos.writeObject(data);
			        System.out.println("Svg effectuée ! "+place.getName());
			      } catch (IOException e) {
			        e.printStackTrace();
			      }
				
			      oos.close();
			        	
			    } catch (FileNotFoundException e) {
			      e.printStackTrace();
			    } catch (IOException e) {
			      e.printStackTrace();
			    }
			}
		}
	    
	    nouvelleLigne.addActionListener(new AddListener());
	    sauvegarde.addActionListener(new SvgListener(data));
	    
	    GridLayout gl = new GridLayout(1,2);
	    gl.setHgap(5);
	    JPanel pan = new JPanel(gl);
	    pan.add(nouvelleLigne);
	    pan.add(sauvegarde);
	    
	    this.add(new JScrollPane(tableau), BorderLayout.CENTER);
	    this.add(pan, BorderLayout.SOUTH);
	    

	}
}
