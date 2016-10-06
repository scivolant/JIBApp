package gestionSuivi.fenetrePlacement;

import java.awt.GridLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;

public class PanSynthese extends JPanel {
	private JLabel prixM;
	private JLabel totUC;
	private JLabel totEUR;

	public PanSynthese(String prixMoyenS, String totalUCS, String totalEURS){
		super();
	    GridLayout gl2 = new GridLayout(1,6);
	    gl2.setHgap(5);
	    this.setLayout(gl2);
	    this.prixM = new JLabel(prixMoyenS);
	    this.totUC = new JLabel(totalUCS);
	    this.totEUR = new JLabel(totalEURS);
		
	    this.add(new JLabel("Prix équil. : "));
	    this.add(prixM);
	    this.add(new JLabel("Total UC : "));
	    this.add(totUC);
	    this.add(new JLabel("Total EUR : "));
	    this.add(totEUR);
	}
	
	public void setDonnes(String prixMoyenS, String totalUCS, String totalEURS){
		this.prixM.setText(prixMoyenS);
		this.totUC.setText(totalUCS);
		this.totEUR.setText(totalEURS);
	}
}
