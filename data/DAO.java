package gestion.data;

import java.sql.Connection;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;

import gestion.compta.Compte;
import gestion.compta.Placement;

public abstract class DAO<T>{
	protected static Connection conn;
	protected DataCenter dataCenter;
	
	public DAO(DataCenter dataCenter){
		this.dataCenter = dataCenter;
		this.conn=dataCenter.getConn();
	}

	abstract public boolean create(T obj);
	
	abstract public boolean update(T obj);
	
	abstract public boolean delete(T obj);
	
	abstract public T find(int index);
	
	// renvoie un élément pleinement initialisé
	abstract public T newElement();
	
	abstract public LinkedList<T> getData();

}
