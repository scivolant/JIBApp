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
 * Création des boutons pour sélection des comptes
 * Les "ActionListeners" associés sont créés dans GestionSuivi
 */

public class BoutonCompte extends JCheckBox{
	private Compte compte;
	
	public BoutonCompte(Compte compte){
		super(compte.getName());
		this.compte = compte;
	}
	
	public Compte getCompte(){
		return this.compte;
	}
}
