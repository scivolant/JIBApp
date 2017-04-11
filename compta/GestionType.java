package gestion.compta;

public class GestionType {
	private String name;
	private int id_type;
	
	public GestionType(int index, String name){
		this.id_type=index;
		this.name = name;
	}
	
	public int getIdType(){
		return id_type;
	}
	
	public String toString(){
		return name;
	}
	
	public String getName(){
		return name;
	}
	
	public void setName(String name){
		this.name =name;
	}
	
	public static GestionType[] values(){
		String[] namesTypes = {"ETF","OPCVM","Obligations","Immo"};
		GestionType[] vectTypes = new GestionType[namesTypes.length];
		int i = 0;
		for (String name:namesTypes){
			vectTypes[i]=new GestionType(i,name);
			i++;
		}
		return vectTypes;
	}

	// Valeur par d�faut de type, mis � jour par GestionTypesDAO.create.
	public static GestionType defaultEntry() {
		GestionType type = new GestionType(0, "Type par d�faut");
		return null;
	}
}
