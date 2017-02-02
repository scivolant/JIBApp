package gestionSuivi.compte;

import gestionSuivi.placement.Placement;

public enum Compte {
	Epargnissimo_AV("AV Epargn.", 0),
	Epargnissimo_retraite("Retr. Epargn.", 1),
	Compte_3("Oddo", 2),
	Paturle("Appart. Paturle", 3),
	CompteTitreBP("Cpt Titre BP", 4);
	
	private String name;
	private int index;
	
	Compte(String name, int index){
		this.name = name;
		this.index = index;
	}
	
	// collection de "getters"
	public static String[] getNames(){
		String[] output = new String[Compte.values().length];
		int index = 0;
		for (Compte compte:Compte.values()){
			output[index++]=compte.getName();
		}
		return output;
	}
	
	public int getIndex(){
		return this.index;
	}

	public String getName(){
		return this.name;
	}
}
