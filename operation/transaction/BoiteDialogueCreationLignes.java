package gestion.operation.transaction;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Date;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.HashSet;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;

import gestion.compta.Compte;
import gestion.compta.Placement;
import gestion.compta.Transaction;
import gestion.data.DataCenter;
import gestion.util.ZModel;


/**
 * 
 * @author Trivy
 * Classe pour la création des nouvelles lignes (sur répartition prédéfinie).
 */

public class BoiteDialogueCreationLignes extends JDialog{
	
	// Placement[] listePlacement = Placement.values();
	// les deux placements pour lesquels on a des transac. prédéf. 
	// listePlacement[2] <-> ETF Euro
	// listePlacement[5] <-> ETF DJ (US)
	protected HashSet<Integer> predefSet;
	private TableauTransaction tabTrans;
	
	public BoiteDialogueCreationLignes(TableauTransaction tabTrans, HashSet<Integer> predefSet){
		super();
		this.tabTrans=tabTrans;
		this.predefSet = predefSet;
		this.setTitle("Données transaction prédéfinie");
		this.setModal(false);
		this.setSize(400,250);
		this.setLocationRelativeTo(null);
		this.setVisible(true);
		
		DataCenter dataCenter = DataCenter.getInstance();
		Placement place = dataCenter.getPlaceCourant();
		
		JPanel panChoix = new JPanel();
		JPanel panControl = new JPanel();
		
		// date :
		JLabel dateLabel = new JLabel("Date ? (format AAAA-mm-JJ)");
		JFormattedTextField jtf = new JFormattedTextField(new SimpleDateFormat("yyyy-MM-dd"));
		Date now = new Date(System.currentTimeMillis());
		jtf.setText(now.toString());
		
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
		float repartition = dataCenter.getRepartition(place);
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
	    	
	    	// ajoute la première ligne au tableau (attention ! Pas initialisée !)
	    	Transaction trans0 = new Transaction(
	    			ZModel.convertStringToDate(jtf.getText()),
	    			place,
	    			dataCenter.getCompteDAO().find(1),
	    			cours,
	    			nbr*repartFinale, 
	    			new Float(0), 
	    			nbr*repartition*cours, 
	    			new Float(0)
	    	);
	    	((ZModel)tableau.getModel()).addRow(trans0);

	    	// ajoute la seconde ligne au tableau
	    	Transaction trans1 = new Transaction(
	    			ZModel.convertStringToDate(jtf.getText()),
	    			place,
	    			dataCenter.getCompteDAO().find(2),
	    			cours,
	    			nbr*(1-repartFinale),
	    			new Float(0), 
	    			nbr*(1-repartition)*cours, 
	    			new Float(0)
	    	);
	    	((ZModel)tableau.getModel()).addRow(trans1);
	    		    	
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
