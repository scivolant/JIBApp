package gestionSuivi.fenetrePlacement;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

import gestionSuivi.placement.Placement;

public class FenetrePlacement extends JPanel {
	private JTabbedPane tabbedPane;
	private TableauTransaction tabTrans;
	private TableauOrdres tabOrdres;

	public FenetrePlacement(Placement place){
		super();
		
		// "Toolbar" pour pouvoir créer des opérations prédéfinies.
		JPanel tool = new JPanel();
		JLabel label0 = new JLabel(place.getName());
		tool.add(label0);
		
		// si le placement est LyxorETFEuro (ind = 2) ou LyxorETFDJ (ind = 5), 
		// alors on ajoute un bouton avec une opération prédéfinie...
		if ((place.getIndex()==2) || (place.getIndex()==5)) {
		    JButton predefBouton = new JButton("Achats prédéf.");
		    predefBouton.addActionListener(new ActionListener(){
		      public void actionPerformed(ActionEvent arg0){
		    	  BoiteDialogueCreationLignes boite = new BoiteDialogueCreationLignes(tabTrans, place);
		      }
		    });
			tool.add(predefBouton);
		}
		
		tabbedPane = new JTabbedPane();
		tabTrans = new TableauTransaction(place);
		tabOrdres = new TableauOrdres(place);
		
		tabbedPane.addTab("Transactions",tabTrans);
		tabbedPane.addTab("Ordres",tabOrdres);
		
		this.setLayout(new BorderLayout());
		this.add(tool, BorderLayout.NORTH);
		this.add(tabbedPane, BorderLayout.CENTER);
	}
	
	public ZModel getModel(){
		return (ZModel)tabTrans.getTableau().getModel();
	}
}
