package gestion.operation.transaction;

import java.awt.GridLayout;
import java.text.NumberFormat;

import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JPanel;

import gestion.compta.Compte;
import gestion.compta.Placement;

public class InputRow extends JPanel {
	private Placement place;
	private JLabel placeName;
	private JLabel compteName;
	private JFormattedTextField jftfCours;
	private JFormattedTextField jftfUC;
	
	public InputRow(Placement place, Compte compte){
		super();
		this.place = place;
		
		// Placement's name
		placeName = new JLabel(place.getName());
		
		// Compte's name
		compteName = new JLabel(compte.getName());
		
		// entrée nbr UC
		// + formatage des nombres à 4 décimales...
		NumberFormat formatter = NumberFormat.getNumberInstance();
		formatter.setMinimumFractionDigits(4);
		jftfUC = new JFormattedTextField(formatter);
		
		// Input cours
		jftfCours = new JFormattedTextField(NumberFormat.getNumberInstance());
		
		this.setLayout(new GridLayout(1,4));
		this.add(placeName);
		this.add(compteName);
		this.add(jftfUC);
		this.add(jftfCours);
	};
	
	public float getCours() {
    	float cours = ((Number)jftfCours.getValue()).floatValue();
    	
    	return cours;
	}
	
	public float getNbrUC() {
		float nbrUC = ((Number)jftfUC.getValue()).floatValue();
		
		return nbrUC;
	}
	
	public Placement getPlacement() {
		return place;
	}
	
	
}
