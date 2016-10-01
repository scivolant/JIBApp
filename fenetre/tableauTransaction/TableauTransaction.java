package gestionSuivi.fenetre.tableauTransaction;

import gestionSuivi.compte.Compte;
import gestionSuivi.placement.Placement;
import gestionSuivi.fenetre.tableauTransaction.ZModel;
import gestionSuivi.fenetre.tableauTransaction.ButtonEditor;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.DefaultCellEditor;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

public class TableauTransaction extends JPanel{
	private JTable tableau;
	private Compte[] listeCompte = Compte.values();
	private JButton nouvelleLigne = new JButton("Ajouter une ligne");
	private Object[] ligneDefault= {"16/08/1983", listeCompte[0], new Float(15.0d), new Float(1), new Float(0), new Float(15d), new Float(0), "-"};
	
	public TableauTransaction(){
		super();
		
		
		//String[] comboData=gestionSuivi.placement.Placement.getNames();
		
		// Des données pour le tableau (afin de fixer les idées)
		Object[][] data = {
	      {"16/08/1983", listeCompte[0], new Float(15.0d), new Float(1), new Float(0), new Float(15d), new Float(0), "-"},
	      {"01/01/2001", listeCompte[0], new Float(15.0d), new Float(0), new Float(2), new Float(0), new Float(30),"-"},
	      {"02/02/2002", listeCompte[0], new Float(15.0d), new Float(3), new Float(0), new Float(45d), new Float(0),"-"},
	      {"13/12/2013", listeCompte[0], new Float(15.0d), new Float(0), new Float(4), new Float(0d), new Float(60d),"-"}		
		};
		
	    //Les titres des colonnes
	    String  title[] = {"Date", "Compte", "Prix unitaire", "Add UC", "Dim UC", "Add €", "Dim €", "Suppr."};
	    
	    JComboBox combo = new JComboBox(listeCompte);
	    
	    ZModel model = new ZModel(data, title);
	    this.tableau = new JTable(model);
	    this.tableau.setRowHeight(30);
	    
	    this.tableau.getColumn("Compte").setCellEditor(new DefaultCellEditor(combo));
	    
	    this.tableau.getColumn("Suppr.").setCellRenderer(new ButtonRenderer());
	    this.tableau.getColumn("Suppr.").setCellEditor(new ButtonEditor(new JCheckBox()));
	    
		class AddListener implements ActionListener{
			public void actionPerformed(ActionEvent event){		
				((ZModel)tableau.getModel()).addRow(ligneDefault);
			}
		}
	    
	    nouvelleLigne.addActionListener(new AddListener());
	    
	    tableau = new JTable(data, title);
	    this.add(new JScrollPane(tableau), BorderLayout.CENTER);
	    this.add(nouvelleLigne, BorderLayout.SOUTH);
	    

	}
}
