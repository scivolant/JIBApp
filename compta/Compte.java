package gestion.compta;

public class Compte {
	private String name;
	private int index;
	
	public Compte(String name){
		this.name = name;
		this.index = 0;
	}
	
	// collection de "getters"
	
	public int getIndex(){
		return this.index;
	}

	public String getName(){
		return this.name;
	}
	
	public void setName(String name){
		this.name = name;
	}
	
	// à utiliser avec parcimonie !
	public void setIndex(int index){
		this.index = index;
	}
	
	public String toString(){
		return this.getName();
	}
	
	// obtenir toute la collection des différents comptes dispos :
	public static Compte[] values(){
		String[] listeNoms ={"AV Epargn.","Retr. Epargn.","Oddo","Appart. Paturle","Cpt Titre BP"};
		int nbComptes = listeNoms.length;
		Compte[] vectComptes = new Compte[nbComptes];
		for (int i = 0; i< nbComptes; i++){
			vectComptes[i]=new Compte(listeNoms[i]);
			vectComptes[i].setIndex(i+1);
		}
		return vectComptes;
	}
	
	public static String[] getNames(){
		String[] output = new String[Compte.values().length];
		int index = 0;
		for (Compte compte:Compte.values()){
			output[index++]=compte.getName();
		}
		return output;
	}
}
