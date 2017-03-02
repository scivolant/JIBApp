package gestion;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JCheckBox;

import gestion.compta.Compte;
import gestion.data.DataCenter;
import gestion.observer.Observable;
import gestion.observer.Observer;

/*
 * Création des boutons pour sélection des comptes et des alertes associées
 * 
 */

public class BoutonCompte extends JCheckBox{
	
	class CompteListener implements ActionListener, Observable<Compte>{
		Compte compte;
		private ArrayList<Observer> listeObserver = new ArrayList<Observer>();
		
		public CompteListener(Compte compte){
			super();
			this.compte = compte;
			this.addObserver(DataCenter.getInstance());
		}
		
		public void actionPerformed(ActionEvent e){
			this.updateObs(compte, ((JCheckBox)e.getSource()).isSelected());
		}
		
		public void addObserver(Observer<Compte> obs){
			listeObserver.add(obs);
		}
		
		public void updateObs(Compte compte, boolean bool){
			for (Observer<Compte> obs: listeObserver){
				obs.updateObs(compte, bool);
			}
		}
		
	}
	
	public BoutonCompte(Compte compte){
		super(compte.getName());
		this.addActionListener(new CompteListener(compte));
	}
}
