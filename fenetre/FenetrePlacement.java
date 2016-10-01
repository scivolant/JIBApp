package gestionSuivi.fenetre;

import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;

public class FenetrePlacement extends JPanel {
	private JPanel historique;
	private JPanel haut;

	public FenetrePlacement(String nom){
		super();
		JLabel label0 = new JLabel(nom);

		JPanel panInfo = new JPanel();
		String[] tab_info = {"NB total d'UC","prix moyen d'achat", "prix d'équilibre"};
		JLabel[] tab_lab = new JLabel[tab_info.length];
		for (int i = 0; i<tab_info.length; i++){
			tab_lab[i]= new JLabel(tab_info[i]);
			panInfo.add(tab_lab[i]);
		}
		
		this.setLayout(new BorderLayout());
		this.add(label0, BorderLayout.NORTH);
		this.add(panInfo, BorderLayout.CENTER);
	}
}
