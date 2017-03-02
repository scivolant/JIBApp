package gestion.data;

import java.sql.Connection;
import java.util.LinkedList;
import java.util.Set;

import gestion.compta.Compte;
import gestion.compta.Placement;

public abstract class DAOtableau<T> extends DAO<T>{
	
   	// Placement et comptes sélectionnés à retrouver par DataCenter
	/*
   	protected Placement placeCourant;
   	protected Set<Compte> comptesCourants;
   	*/

	public DAOtableau(DataCenter dataCenter) {
		// TODO Auto-generated constructor stub
		super(dataCenter);
	};
	
	/*
	public DAOtableau(Connection conn, Placement place, Set<Compte> comptesCourants) {
		// TODO Auto-generated constructor stub
		super(conn);
		this.placeCourant = place;
		this.comptesCourants = comptesCourants;
	}
	*/

}
