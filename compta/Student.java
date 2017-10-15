package gestion.compta;

public class Student {
	private String name;
	private int id_type;
	
	public Student(int index, String name){
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
	
	// ne devrait apparaître que dans GestionTypeDAO.create...
	public void setIdType(int id_type){
		this.id_type =id_type;
	}
	
	public static Student[] values(){
		String[] namesTypes = {"ETF","OPCVM","Obligations","Immo"};
		Student[] vectTypes = new Student[namesTypes.length];
		int i = 0;
		for (String name:namesTypes){
			vectTypes[i]=new Student(i,name);
			i++;
		}
		return vectTypes;
	}

	// Valeur par défaut de type, mis à jour par GestionTypesDAO.create.
	public static Student defaultEntry() {
		Student type = new Student(0, "Type par défaut");
		return type;
	}
}
