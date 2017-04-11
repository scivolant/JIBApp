package gestion.compta;

import java.sql.Date;

public class Cours {
	private int id_cours;
	private Placement place;
	private Date date;
	private float coursUnit;
	
	// initialisation de id_cours � 0
	// � renseigner d�s que possible !
	public Cours(Date date, Placement place, float coursUnit) {
		super();
		this.id_cours = 0;
		this.date = date;
		this.place = place;
		this.coursUnit = coursUnit;
	}

	public int getIdCours() {
		return id_cours;
	}

	// setIdCours, � utiliser avec parcimonie...
	public void setIdCours(int id_cours) {
		this.id_cours=id_cours;
	}
	
	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public Placement getPlace() {
		return place;
	}

	public void setPlace(Placement place) {
		this.place = place;
	}

	public float getCoursUnit() {
		return coursUnit;
	}

	public void setCoursUnit(float coursUnit) {
		this.coursUnit = coursUnit;
	}
	
	public String toString(){
		String string = "Cours n�"+this.getIdCours()+", date = "+this.getDate().toString()
				+" , prix = "+this.getCoursUnit();
		return string;
	}
}
