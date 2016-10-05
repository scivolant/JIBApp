package gestionSuivi.placement;

public enum GestionTypes {
	ETF("ETF",0), 
	OPCVM("OPCVM",1),
	Obligations("Obligations",2),
	Immo("Immo",3);
	
	private String nom;
	private int index;
	
	GestionTypes(String nom, int index){
		this.nom = nom;
		this.index=index;
	}
	
	public int getIndex(){
		return index;
	}
	
	public String toString(){
		return nom;
	}
}
