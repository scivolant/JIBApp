package gestion.data.quotation;

import java.sql.Date;

import gestion.data.dao.DaoException;

public abstract class Transfer {
	protected String name;
	
	public Transfer(String name){
		this.name = name;
	}
	
	public static Transfer[] values(){
		Transfer[] vect = new Transfer[5];
		vect[0] = new FT_ETF();
		vect[1] = new FT_ordinaire();
		vect[2] = new Boursorama();
		vect[3] = new Mano();
		vect[4] = new Boursorama_ETF();
		return vect;
	}
	
	public abstract Date getDate(String text) throws DaoException;

	public abstract float getPrice(String text) throws DaoException;
	
	public abstract int getIdTransf();
	
	public String toString(){
		return this.name;
	}
}
