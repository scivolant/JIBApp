package gestion.operation.transaction;

import java.awt.GridLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

public class PanSynthese extends JPanel {
	private JLabel prixM;
	private JLabel totUC;
	private JLabel totEUR;
	private JLabel vL;
	private JLabel dCours;
	private JLabel pmValue;

	public PanSynthese(float[] vectFloat){
		// variable dans le vecteur, dans l'ordre : 
		// totalUC (0), totalEUR (1) [total des EUR investis !], dernierCours (2)
		super();
	    GridLayout gl2 = new GridLayout(2,6);
	    gl2.setHgap(10);
	    this.setLayout(gl2);
	    
	    // Calculs ini
	    float prixMoyen = vectFloat[1]/vectFloat[0];
	    float valeurLiq = vectFloat[0]*vectFloat[2];
	    
	    this.prixM = new JLabel(String.format("%.2f",prixMoyen),SwingConstants.RIGHT);
	    this.totUC = new JLabel(String.format("%.4f",vectFloat[0]),SwingConstants.RIGHT);
	    this.totEUR = new JLabel(String.format("%.2f",vectFloat[1]),SwingConstants.RIGHT);
	    this.vL = new JLabel(String.format("%.2f",valeurLiq),SwingConstants.RIGHT);
	    this.dCours = new JLabel(String.format("%.2f",vectFloat[2]),SwingConstants.RIGHT);
	    this.pmValue = new JLabel(String.format("%.2f",valeurLiq - vectFloat[1]),SwingConstants.RIGHT);
		
	    // Première ligne
	    this.add(new JLabel("Prix équil. : "));
	    this.add(prixM);
	    this.add(new JLabel("Total UC : "));
	    this.add(totUC);
	    this.add(new JLabel("Total EUR : "));
	    this.add(totEUR);
	    
	    // Seconde ligne
	    this.add(new JLabel("Dernier cours : "));
	    this.add(dCours);
	    this.add(new JLabel("Plus-value : "));
	    this.add(pmValue);
	    this.add(new JLabel("VL : "));
	    this.add(vL);
	}
	
	public void setData(float[] vectFloat){
	    // Calculs ini
	    float prixMoyen = vectFloat[1]/vectFloat[0];
	    float valeurLiq = vectFloat[0]*vectFloat[2];
	    
	    this.prixM.setText(String.format("%.2f",prixMoyen));
	    this.totUC.setText(String.format("%.4f",vectFloat[0]));
	    this.totEUR.setText(String.format("%.2f",vectFloat[1]));
	    this.vL.setText(String.format("%.2f",valeurLiq));
	    this.dCours.setText(String.format("%.2f",vectFloat[2]));
	    this.pmValue.setText(String.format("%.2f",valeurLiq - vectFloat[1]));
	}
}
