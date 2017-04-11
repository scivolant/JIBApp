package gestion.util;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import gestion.compta.Placement;
import gestion.data.DAOtableau;
import gestion.data.DataCenter;

/* 
 * Classe-mère implémentant les 
 * _ fonctions de sauvegarde du tableau (boutons + listeners)
 * _ fonction d'ajout de ligne
 * 
 */

public class TableauCommun<T> extends JPanel{
	protected Placement place;
	protected JTable tableau;
	protected JPanel pan;
	protected ZModel<T> model;
	
	public TableauCommun(ZModel<T> model){
		super(new BorderLayout());
		this.model = model;
	    this.tableau = new JTable(model);
	    this.tableau.setRowHeight(30);
	    
		JButton nouvelleLigne = new JButton("Ajouter une ligne");
		JButton sauvegarde = new JButton("Svg/MàJ");
	    
		class AddListener implements ActionListener{
			public void actionPerformed(ActionEvent event){		
				((ZModel<T>)tableau.getModel()).addRow();
			}
		}
		
		class SvgListener implements ActionListener{
			ZModel<T> model;
			
			public SvgListener(ZModel<T> model){
				super();
				this.model=model;
			}
			
			public void actionPerformed(ActionEvent event){
				svgMaJTableau();
			}
		}
	    
	    nouvelleLigne.addActionListener(new AddListener());
	    sauvegarde.addActionListener(new SvgListener(model));
	    
	    GridLayout gl1 = new GridLayout(1,2);
	    gl1.setHgap(5);
	    JPanel pan1 = new JPanel(gl1);
	    pan1.add(nouvelleLigne);
	    pan1.add(sauvegarde);
	    pan = new JPanel( new BorderLayout());
	    pan.add(pan1, BorderLayout.SOUTH);
	    
	    this.add(new JScrollPane(tableau), BorderLayout.CENTER);
	    this.add(pan, BorderLayout.SOUTH);
	}
	
	public JTable getTableau(){
		return this.tableau;
	}
	
	public ZModel<T> getModel(){
		return this.model;
	}
	
	// Action effectuée par le bouton "Svg/MàJ" (modif. dans TableauTrans, TableauPlacement)
	public void svgMaJTableau(){
		// Sauvegarde des données modifiées
		int i = 0;
		for (T obj : model.getData()){
			if (model.getDAOtableau().update(obj)){
				i++;						
			} else {};
		};
		System.out.println("TableauCommun.SvgListener : Svg de "+i+" ligne(s).");
		model.updateData();
	}
}
