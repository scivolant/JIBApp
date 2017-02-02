package gestionSuivi.fenetrePlacement;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.NumberFormat;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JTable;

import gestionSuivi.compte.Compte;
import gestionSuivi.placement.Placement;


/**
 * 
 * @author Trivy
 * Classe pour la création des nouvelles lignes (sur répartition prédéfinie).
 */

public class BoiteDialogueCreationLignes extends JDialog{
	String[] optionsDispo={Placement.LyxorETFEuro.getName(),Placement.LyxorETFDJ.getName()};
	private TableauTransaction tabTrans;
	
	public BoiteDialogueCreationLignes(TableauTransaction tabTrans, Placement place){
		super();
		this.tabTrans=tabTrans;
		this.setTitle("Données transaction prédéfinie");
		this.setModal(false);
		this.setSize(400,250);
		this.setLocationRelativeTo(null);
		this.setVisible(true);
		
		JPanel panChoix = new JPanel();
		JPanel panControl = new JPanel();
		
		// date :
		JLabel dateLabel = new JLabel("Date ? (format JJ/MM/AAAA)");
		JTextField jtf = new JTextField("17/01/2017"); // Pourrait être remplacé par un contrôle du format des dates.
		
		// entrée cours 
		JLabel coursLabel = new JLabel("Cours :");
		JFormattedTextField jftfCours = new JFormattedTextField(NumberFormat.getNumberInstance());
		
		// entrée nbr UC
		// + formatage des nombres à 4 décimales...
		NumberFormat formatter = NumberFormat.getNumberInstance();
		formatter.setMinimumFractionDigits(4);
		JLabel totUCLabel = new JLabel("Nbr tot. UC :");
		JFormattedTextField jftfUC = new JFormattedTextField(formatter);
		
		// indiquer repartition entre les deux comptes...
		float repartition;
    	// si l'indice du placement est 2, alors il s'agit de LyxorETFEuro,
    	if (place.getIndex()==2){
    		// la valeur 20/78f correspond à la répartition "ordinaire"/"retraite" dans Epargnissimo (pour Lyxor Euro)
    		repartition=(20/78f);	    		
    	}
    	// si l'indice du placement est 5, alors il s'agit de LyxorETFDJ,
    	else if (place.getIndex()==5){
    		// la valeur 20/48f correspond à la répartition "ordinaire"/"retraite" dans Epargnissimo (pour Lyxor DJ)
    		repartition=(20/48f);
    	} else {
    		repartition=0.5f;
    	}
    	JLabel repartLabel = new JLabel("Coeff. répart. (compte 1 VS total) ");
		JFormattedTextField jftfRepart = new JFormattedTextField(formatter);
		jftfRepart.setValue(repartition);
		
		panChoix.setLayout(new GridLayout(4,2));
		panChoix.add(dateLabel);
		panChoix.add(jtf);
		panChoix.add(coursLabel);
		panChoix.add(jftfCours);
		panChoix.add(totUCLabel);
		panChoix.add(jftfUC);
		panChoix.add(repartLabel);
		panChoix.add(jftfRepart);
		
	    JButton cancelBouton = new JButton("Annuler");
	    cancelBouton.addActionListener(new ActionListener(){
	      public void actionPerformed(ActionEvent arg0) {
	        setVisible(false);
	      }      
	    });
	    
	    JButton okBouton = new JButton("OK");
	    okBouton.addActionListener(new ActionListener(){
	      public void actionPerformed(ActionEvent arg0){
			Compte[] listeCompte = Compte.values();
	    	JTable tableau = tabTrans.getTableau();
	    	float nbr = ((Number)jftfUC.getValue()).floatValue();
	    	float cours = 0f;
	    	float repartFinale = ((Number)jftfRepart.getValue()).floatValue();

	    	cours =((Number)jftfCours.getValue()).floatValue();
	    	
	    	// ajoute la première ligne au tableau
	    	Object[] ligne0 = {jtf.getText(), listeCompte[0].getName(), cours, nbr*repartFinale, new Float(0), nbr*repartition*cours, new Float(0), "-"};
	    	((ZModel)tableau.getModel()).addRow(ligne0);

	    	// ajoute la seconde ligne au tableau
	    	Object[] ligne1 = {jtf.getText(), listeCompte[1].getName(), cours, nbr*(1-repartFinale), new Float(0), nbr*(1-repartition)*cours, new Float(0), "-"};
	    	((ZModel)tableau.getModel()).addRow(ligne1);
	    		    	
	    	// termine le dialogue en rendant la boite invisible
	        setVisible(false);
	      }
	    });
	    
	    panControl.add(cancelBouton);
	    panControl.add(okBouton);
	    
	    this.getContentPane().add(panChoix, BorderLayout.CENTER);
	    this.getContentPane().add(panControl, BorderLayout.SOUTH);
	}
}
