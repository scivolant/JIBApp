package gestion.operation;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JPanel;
import javax.swing.JTable;

import gestion.accueil.ButtonRenderer;
import gestion.compta.Cours;
import gestion.data.DataCenter;
import gestion.util.ButtonDeleteEditor;
import gestion.util.DateEditor;
import gestion.util.FloatEditor;
import gestion.util.NormEURRenderer;
import gestion.util.TableauCommun;

/* 
 * Classe fille de TableauCommun, sp�cialis�e � Transaction (avec un panneau synth�se)
 * 
 */

public class TableauCours extends TableauCommun<Cours>{
	private JPanel panCours;
	
	public TableauCours(){
		super(new CoursModel());
		
		DataCenter dataSql = DataCenter.getInstance();
		
	    //Pour m�moire, les titres des colonnes (vrai title dans "CoursModel")
	    //String  title[] = {"Date", "Cours", "Suppr."};
	    
	    // d�finition des �diteurs...
	    this.tableau.getColumn("Date").setCellEditor(new DateEditor());
	    
	    this.tableau.getColumn("Cours").setCellRenderer(new NormEURRenderer());
	    this.tableau.getColumn("Cours").setCellEditor(new FloatEditor(2));
	    
	    this.tableau.getColumn("Suppr.").setCellRenderer(new ButtonRenderer());
	    this.tableau.getColumn("Suppr.").setCellEditor(new ButtonDeleteEditor(new JCheckBox()));
		
	    // Bouton M�J des cours
	    JButton boutonMaJCours = new JButton("Recherche dernier cours (en ligne)");
	    boutonMaJCours.addActionListener(new ActionListener(){
	    	public void actionPerformed(ActionEvent event){
	    		// appuyer sur le bouton met � jour le placement courant
	    		dataSql.dernierCoursMaJ();
				model.updateData();
	    	}
	    });
	    
	    // Panneau de mise � jour des cours en ligne
	    panCours = new JPanel();
	    //panCours.setLayout(new BoxLayout(panCours, BoxLayout.LINE_AXIS));
	    panCours.add(boutonMaJCours);
	    
	    pan.add(panCours,BorderLayout.NORTH);
	}
	
	public JTable getTableau(){
		return this.tableau;
	}
}
