package gestionSuivi.placement;

import gestionSuivi.placement.GestionTypes;

public enum Placement {
	
	FidelityEuro("Fidelity Europe","FidelityEuro",GestionTypes.OPCVM, "FRxx", "FE",0),
	CarmignacEm("Carmignac Emergent","CarmignacEm",GestionTypes.OPCVM, "FRyy", "CE",1),
	LyxorETFEuro("Lyxor ETF Euro","LyxorETFEuro",GestionTypes.ETF, "FRzz", "LE",2),
	BienImmo1("Appart. Parturle","BienImmo1",GestionTypes.Immo, "-", "Paturle",3),
	Obligations1("Obligations 1", "Obligations1", GestionTypes.Obligations, "FRbb", "O1",4),
	LyxorETFDJ("Lyxor ETF DJ Indust. Av.","LyxorETFDJ",GestionTypes.ETF,"FRcc","02",5),
	Gallica("Gallica","Gallica",GestionTypes.OPCVM,"FR0010031195","Gallica",6),
	LBPAMActionsEuro("BP Actions Euro","LBPAMActionsEuro",GestionTypes.OPCVM,"FR0000286320","LBPAMActionsEuro",7),
	LBPAMActionsMonde("BP Actions Monde","LBPAMActionsMonde",GestionTypes.OPCVM,"FR0000288078","LBPAMActionsMonde",8),
	LyxorMTS35("Lyxor ETF Euro 3-5 ANS IG","LyxorMTS35",GestionTypes.Obligations,"FR0010037234","LyxorMTS35",9);
	
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
