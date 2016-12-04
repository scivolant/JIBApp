package gestionSuivi.fenetrePlacement;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JToolBar;

import gestionSuivi.placement.Placement;

public class FenetrePlacement extends JPanel {
	private TableauTransaction tabTrans;

	public FenetrePlacement(Placement place){
		super();
		
		// "Toolbar" pour pouvoir créer des opérations prédéfinies.
		JToolBar tool = new JToolBar();
		JLabel label0 = new JLabel(place.getName());
	    JButton predefBouton = new JButton("Op. prédéf.");
	    predefBouton.addActionListener(new ActionListener(){
	      public void actionPerformed(ActionEvent arg0){
	    	  BoiteDialogueCreationLignes boite = new BoiteDialogueCreationLignes(tabTrans);
	      }
	    });
		tool.add(label0);
		tool.addSeparator();
		tool.add(predefBouton);
		
		tabTrans = new TableauTransaction(place);
		
		this.setLayout(new BorderLayout());
		this.add(tool, BorderLayout.NORTH);
		this.add(tabTrans, BorderLayout.CENTER);
	}
	
	public ZModel getModel(){
		return (ZModel)tabTrans.getTableau().getModel();
	}
}
