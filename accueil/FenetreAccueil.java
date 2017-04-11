package gestion.accueil;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedList;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import gestion.compta.Placement;
import gestion.data.DataCenter;

public class FenetreAccueil extends JPanel {
	protected JTable tableauRepartition;
	protected JLabel achats;
	protected JLabel ventes;
	
	public FenetreAccueil(){
		super();

		DataCenter dataSql = DataCenter.getInstance();
		
		// Chercher les donn�es pour l'accueil :
		Object[][] data = dataSql.accueilData();
		
	    //Les titres des colonnes
	    String  title[] = {"Type", "Euros", "%"};
	    
	    ZModel2 model = new ZModel2(data, title);
	    this.tableauRepartition = new JTable(model);
	    this.tableauRepartition.setAutoCreateColumnsFromModel(false); // pour ne pas perdre le formatage en cas de setDataVector... (voir http://www.chka.de/swing/table/faq.html)
	    this.tableauRepartition.setRowHeight(30);
	    
	    this.tableauRepartition.getColumn("Euros").setCellRenderer(new NumberRenderer());
	    this.tableauRepartition.getColumn("%").setCellRenderer(new PercentageRenderer());
	    
	    // Panneau des ordres :
	    JLabel texteAchats = new JLabel("Achats � ex�c. :");
	    achats = new JLabel(String.valueOf(DataCenter.getNbrAchatsAExec()));
	    JLabel texteVentes = new JLabel("Ventes � ex�c. :");
	    ventes = new JLabel(String.valueOf(DataCenter.getNbrVentesAExec()));
	    GridLayout gl2 = new GridLayout(1,4);
	    gl2.setHgap(5);
	    JPanel panOrdres = new JPanel(gl2);
	    panOrdres.add(texteAchats);
	    panOrdres.add(achats);
	    panOrdres.add(texteVentes);
	    panOrdres.add(ventes);	
	    
	    // Bouton M�J et son listener
		JButton maJ = new JButton("M�J");
	    class MaJListener implements ActionListener{
			
			public void actionPerformed(ActionEvent event){
				// mise � jour du panneau synth�se (sur des donn�es � jour)
				((ZModel2) tableauRepartition.getModel()).setDataVector(dataSql.accueilData(),title);
				
				// mise � jour des ordres d'achats et de ventes
				updateOrdres();
			}
		}
	    maJ.addActionListener(new MaJListener());
	    
	    // Bouton "Dernier cours" et son listener
	    JButton derniersCours = new JButton("Derniers cours (en ligne)");
	    class DCoursListener implements ActionListener{
			
			public void actionPerformed(ActionEvent event){
				// recherche des derniers cours, pour tous les placements
				LinkedList<Placement> data = dataSql.getPlacementDAO().getAll();
				for (Placement place: data){
					dataSql.dernierCoursMaJ(place);
				}
				System.out.println("DCoursListener : mise � jour des cours termin�e !");
				
				// M�J des ordres
				updateOrdres();		
			}
		}
	    derniersCours.addActionListener(new DCoursListener());
	    
	    // Panneau pour contenir les boutons :
	    GridLayout gl1 = new GridLayout(1,2);
	    gl1.setHgap(5);
	    JPanel panBouton = new JPanel(gl1);
	    panBouton.add(derniersCours);
	    panBouton.add(maJ);    
		
	    // Panneau bas
	    JPanel panBas = new JPanel(new GridLayout(2,1));
	    panBas.add(panOrdres);
	    panBas.add(panBouton);

	    
		// Mise en place finale
	    JPanel pan = new JPanel();
	    pan.setPreferredSize(new Dimension(350,100));
	    pan.add(new JScrollPane(tableauRepartition));
	    
		BorderLayout bl = new BorderLayout(); 
		this.setLayout(bl);

		this.add(new JLabel("R�partition des sommes :"), BorderLayout.NORTH);
		this.add(pan, BorderLayout.CENTER);
		this.add(panBas,BorderLayout.SOUTH);
		this.setVisible(true);
	}
	
	public void updateOrdres(){
		DataCenter dataCenter = DataCenter.getInstance();
		// Mise � jour du nombre d'ordres � effectuer
		dataCenter.achatsAExecuter();
		dataCenter.ventesAExecuter();
		this.achats.setText(String.valueOf(DataCenter.getNbrAchatsAExec()));
		this.ventes.setText(String.valueOf(DataCenter.getNbrVentesAExec()));
	}
}
