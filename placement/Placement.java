package gestionSuivi.placement;

import gestionSuivi.placement.GestionTypes;

public enum Placement {
	
	FidelityEuro("Fidelity Europe","FidelityEuro",GestionTypes.OPCVM, "FRxx", "FE",0),
	CarmignacEm("Carmignac Emergent","CarmignacEm",GestionTypes.OPCVM, "FRyy", "CE",1),
	LyxorETFEuro("Lyxor ETF Euro","LyxorETFEuro",GestionTypes.ETF, "FRzz", "LE",2),
	BienImmo1("Bien Immobilier 1","BienImmo1",GestionTypes.Immo, "FRaa", "BI",3),
	Obligations1("Obligations 1", "Obligations1", GestionTypes.Obligations, "FRbb", "O1",4),
	LyxorETFDJ("Lyxor ETF DJ Indust. Av.","LyxorETFDJ",GestionTypes.ETF,"FRcc","02",5);
	
	private String name;
	private String mnemo;
	private GestionTypes type;
	private String ISIN;
	private String codeMaJ;
	private int index;

	Placement(String name, String mnemo, GestionTypes type, String ISIN, String codeMaJ, int index){
		this.name = name;
		this.mnemo = mnemo;
		this.type = type;
		this.ISIN = ISIN;
		this.codeMaJ= codeMaJ;
		this.index=index;
	}
	
	// collection de "getters"
	public static String[] getNames(){
		String[] output = new String[Placement.values().length];
		int index = 0;
		for (Placement place:Placement.values()){
			output[index++]=place.getName();
		}
		return output;
	}
	
	public String getName(){
		return this.name;
	}
	
	public String getMnemo(){
		return this.mnemo;
	}
	
	public GestionTypes getType(){
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
	
	// Collection de "setters"
	public void setName(String name){
		this.name = name;
	}
	
	public void setMnemo(String mnemo){
		this.mnemo = mnemo;
	}
	
	public void setType(GestionTypes type){
		this.type = type;
	}
	
	public void setISIN(String ISIN){
		this.ISIN = ISIN;
	}
	
	public void setCodeMaJ(String codeMaJ){
		this.codeMaJ = codeMaJ;
	}
}
