package gestion.compta;

import gestion.data.DataCenter;
import gestion.data.dao.SourceQuoteDAO;

public class Placement {
	private String name;
	private String mnemo;
	private GestionType type;
	private String ISIN;
	private String codeMaJ;
	private SourceQuote source;
	private int index;
	
	public Placement(String name, String mnemo, GestionType type, String ISIN, String codeMaJ, SourceQuote source){
		this.name = name;
		this.mnemo = mnemo;
		this.type = type;
		this.ISIN = ISIN;
		this.codeMaJ= codeMaJ;
		this.source = source;
		this.index=0;
	}
	
	// collection de "getters"
	public String getName(){
		return this.name;
	}
	
	public String getMnemo(){
		return this.mnemo;
	}
	
	public GestionType getType(){
		return this.type;
	}
	
	public String getISIN(){
		return this.ISIN;
	}
	
	public String getCodeMaJ(){
		return this.codeMaJ;
	}
	
	public int getIndex(){
		return this.index;
	}
	
	public SourceQuote getSource(){
		return this.source;
	}
	
	// Collection de "setters"
	public void setName(String name){
		this.name = name;
	}
	
	public void setMnemo(String mnemo){
		this.mnemo = mnemo;
	}
	
	public void setType(GestionType type){
		this.type = type;
	}
	
	public void setISIN(String ISIN){
		this.ISIN = ISIN;
	}
	
	public void setCodeMaJ(String codeMaJ){
		this.codeMaJ = codeMaJ;
	}
	
	public void setSource(SourceQuote source){
		this.source = source;
	}
	
	public String toString(){
		return this.getName();
	}
	
	// m�thode � utiliser avec parcimonie !
	public void setIndex(int index){
		this.index= index;
	}
	
	// Placement par d�faut, initialis� avec un indice = 0 (mis � jour ensuite par PlacementDAO.create)
	public static Placement defaultEntry(){
		GestionType type = DataCenter.getGestionTypesDAO().anyElement();
		SourceQuote source = DataCenter.getSourceQuoteDAO().anyElement();
		Placement place = new Placement("Placement par d�faut", "ParDef", type, "FRxxxyyy", "ParDef", source);
		return place;
	}
}
