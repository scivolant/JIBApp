package gestion.aexecuter;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import gestion.data.DataCenter;

public class FenetreAExecuter extends JPanel {

	public FenetreAExecuter() {
		super();
		this.setLayout(new BoxLayout(this,BoxLayout.PAGE_AXIS));
		
		// Panneau des achats
		JPanel panAchats = new JPanel(new BorderLayout());
		JLabel achats = new JLabel("Achats");
		
		// Initialiser le mod�le
		Object[][] dataAchats = DataCenter.getInstance().achatsAExecuter();
		String[] titleAchats = {"Placement", "Volume (UC)", "Cours ordre", "Dernier cours", "Date cours"};
		AExecuterModel dModelA = new AExecuterModel(dataAchats,titleAchats);

		JTable tabAchats = new TableauAExecuter(dModelA);

		
		panAchats.add(achats,BorderLayout.NORTH);
		panAchats.add(new JScrollPane(tabAchats), BorderLayout.CENTER);
		//panAchats.setPreferredSize(new Dimension(400,300));
		
		
		// Panneau des ventes
		JPanel panVentes = new JPanel(new BorderLayout());
		JLabel ventes = new JLabel("Ventes");
		Object[][] dataVentes = DataCenter.getInstance().ventesAExecuter();
		String[] titleVentes = {"Placement", "Volume (UC)", "Cours ordre", "Dernier cours", "Date cours"};
		AExecuterModel dModelV = new AExecuterModel(dataVentes,titleVentes);
		
		JTable tabVentes = new TableauAExecuter(dModelV);
		
		panVentes.add(ventes,BorderLayout.NORTH);
		panVentes.add(new JScrollPane(tabVentes), BorderLayout.CENTER);
		
		// bouton de mise � jour 
		JButton maJ = new JButton("M�J des donn�es");
	    class AExecListener implements ActionListener{
			
			public void actionPerformed(ActionEvent event){
				// recherche des achats � ex�cuter
				Object[][] dataA = DataCenter.getInstance().achatsAExecuter();
				// on utilise setData + fireTableDataChanged() plut�t que setDataVector, 
				// car setDataVector perd les "renderers"
				dModelA.setData(dataA);
				dModelA.fireTableDataChanged();
				System.out.println("AExecListener : mise � jour des achats � effectuer termin�e !");
				
				Object[][] dataV = DataCenter.getInstance().ventesAExecuter();
				dModelV.setData(dataV);
				dModelV.fireTableDataChanged();
				System.out.println("AExecListener : mise � jour des ventes � effectuer termin�e !");
			}
		}
	    maJ.addActionListener(new AExecListener());
		
		this.add(panAchats);
		this.add(panVentes);
		this.add(maJ);
	}

}
