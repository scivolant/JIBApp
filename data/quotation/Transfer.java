package gestion.data.quotation;

import java.sql.Date;

public abstract class Transfer {
	protected String name;
	
	public Transfer(String name){
		this.name = name;
	}
	
	public static Transfer[] values(){
		Transfer[] vect = new Transfer[4];
		vect[0] = new FT_ETF();
		vect[1] = new FT_ordinaire();
		vect[2] = new Boursorama();
		vect[3] = new Mano();
		return vect;
	}
	
	public abstract Date getDate(String text) throws QuotationException;

	public abstract float getPrice(String text) throws QuotationException;
	
	public abstract int getIdTransf();
	
	public String toString(){
		return this.name;
	}
}
