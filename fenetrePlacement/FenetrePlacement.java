package gestionSuivi.fenetrePlacement;

import java.awt.BorderLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;

import gestionSuivi.placement.Placement;

public class FenetrePlacement extends JPanel {
	private TableauTransaction tabTrans;

	public FenetrePlacement(Placement place){
		super();
		JLabel label0 = new JLabel(place.getName());
		label0.setSize(200, 30);
		
		tabTrans = new TableauTransaction(place);
		
		this.setLayout(new BorderLayout());
		this.add(label0, BorderLayout.NORTH);
		this.add(tabTrans, BorderLayout.CENTER);
	}
	
	public ZModel getModel(){
		return (ZModel)tabTrans.getTableau().getModel();
	}
}
