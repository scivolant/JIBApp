package gestionSuivi.data;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Arrays;

import gestionSuivi.compte.Compte;
import gestionSuivi.fenetrePlacement.ZModel;
import gestionSuivi.placement.GestionTypes;
import gestionSuivi.placement.Placement;


/*
 * Une implémentation initiale du système de données (avec sauvegarde dans des fichiers...)
 * À remplacer à terme par une base SQL.
 */

public class DataIni implements DataInterf {
	private static DataIni instance= new DataIni();
	/*
	 * Implémentation simplifiée de "singleton" : initialisation dès que la classe est appelée !
	 * 
	// nbr de colonnes dans un tableau de dataTrans
	private static int nbCol = 8;
	*/
	
	// Les deux tableaux contenants les transactions et ordres
	private Object[][][] dataTransG;	
	private Object[][][] dataOrdresG;
	
	// Etats (sélectionnés ou non) des comptes
	private Boolean[] comptesSelect;
	
	private DataIni(){
		int nbPlace = Placement.values().length;
		dataTransG = new Object[nbPlace][][];
		dataOrdresG = new Object[nbPlace][][];
		for (Placement place:Placement.values()){
			dataTransG[place.getIndex()]=this.fetchData(place);
			dataOrdresG[place.getIndex()]=this.fetchDataOrdres(place);
		}
		Compte[] listCompte = Compte.values();
		
		// liste des comptes sélectionnés. Initialisé à "False"
		comptesSelect = new Boolean[listCompte.length];
	}
	
	public static DataIni getInstance(){
		return instance;
	}

	@Override
	public String nameSvg(Placement place) {
		// TODO Auto-generated method stub
		return "svg_"+place.getMnemo()+".TransSvg";
	}
	
	public String nameSvgOrdres(Placement place) {
		// TODO Auto-generated method stub
		return "svg_"+place.getMnemo()+".OrdresSvg";
	}
	
	public Object[][] defaultData(){
		Compte[] listeCompte = Compte.values();
		Object[][] data = {
			      {"16/08/1983", listeCompte[0], new Float(15.0d), new Float(1), new Float(0), new Float(15d), new Float(0), "-"},
			      {"01/01/2001", listeCompte[0], new Float(15.0d), new Float(0), new Float(2), new Float(0), new Float(30),"-"},
			      {"02/02/2002", listeCompte[0], new Float(15.0d), new Float(3), new Float(0), new Float(45d), new Float(0),"-"},
			      {"13/12/2013", listeCompte[0], new Float(15.0d), new Float(0), new Float(1), new Float(0d), new Float(10d),"-"}		
				};
		return data;
	}
	
	public Object[][] defaultDataOrdres(){
		Compte[] listeCompte = Compte.values();
		Object[][] data = {
			      {listeCompte[0], new Float(20.0d), new Float(2), new Float(0), new Float(40), new Float(0),"Initialisation", "-"},
			      {listeCompte[1], new Float(40.0d), new Float(0), new Float(2), new Float(0), new Float(80),"Initialisation","-"},
			      {listeCompte[0], new Float(25.0d), new Float(3), new Float(0), new Float(75), new Float(0),"Initialisation","-"},
			      {listeCompte[1], new Float(45.0d), new Float(0), new Float(3), new Float(0), new Float(135),"Initialisation","-"}		
				};
		return data;
	}
	
	public Object[] defaultLine(){
		Compte[] listeCompte = Compte.values();
		Object[] ligneDefault= {"16/08/1983", listeCompte[0], new Float(15.0d), new Float(1), new Float(0), new Float(15d), new Float(0), "-"};
		return ligneDefault;
	}
	
	public Object[] defaultLineOrdres(){
		Compte[] listeCompte = Compte.values();
		Object[] ligneDefault= {listeCompte[0], new Float(20.0d), new Float(2), new Float(0), new Float(40), new Float(0),"Nouvelle ligne", "-"};
		return ligneDefault;
	}

	public void svgData(Placement place, ZModel model){
		// mise à jour de la copie de dataTransG
		this.dataTransG[place.getIndex()]=model.getData();
		
		// sauvegarde dans un fichier externe.
	    ObjectOutputStream oos;
	    try {	
	      //On envoie maintenant les données !
	      oos = new ObjectOutputStream(
	              new BufferedOutputStream(
	                new FileOutputStream(
	                  new File(this.nameSvg(place)))));
	            
	      try {
	        oos.writeObject(model.getData());
	      } catch (IOException e) {
	        e.printStackTrace();
	      }
		
	      oos.close();
	        	
	    } catch (FileNotFoundException e) {
	      e.printStackTrace();
	    } catch (IOException e) {
	      e.printStackTrace();
	    }
	}
	
	public void svgDataOrdres(Placement place, ZModel model){
		// mise à jour de la copie de dataTransG
		this.dataOrdresG[place.getIndex()]=model.getData();
		
		// sauvegarde dans un fichier externe.
	    ObjectOutputStream oos;
	    try {	
	      //On envoie maintenant les données !
	      oos = new ObjectOutputStream(
	              new BufferedOutputStream(
	                new FileOutputStream(
	                  new File(this.nameSvgOrdres(place)))));
	            
	      try {
	        oos.writeObject(model.getData());
	      } catch (IOException e) {
	        e.printStackTrace();
	      }
		
	      oos.close();
	        	
	    } catch (FileNotFoundException e) {
	      e.printStackTrace();
	    } catch (IOException e) {
	      e.printStackTrace();
	    }
	}
	
	public Object[][] lireData(Placement place){
		return this.dataTransG[place.getIndex()];
	}
	
	public Object[][] lireDataOrdres(Placement place){
		return this.dataOrdresG[place.getIndex()];
	}
	
	public Object[][] fetchData(Placement place){
	    ObjectInputStream ois;
	    Object[][] data;
	    try {	
	      //On récupère maintenant les données !
	      ois = new ObjectInputStream(
	              new BufferedInputStream(
	                new FileInputStream(
	                  new File(this.nameSvg(place)))));
	            
	      try {
	        data = (Object[][])ois.readObject();
		    System.out.println("Fichier retrouvé et lu... "+place.getName());
	      } catch (ClassNotFoundException e) {
		    System.err.println("Problème : ClassNotFoundException");
	        e.printStackTrace();
	    	data = this.defaultData();
	      }
		  
	    ois.close();
	        	
	    } catch (FileNotFoundException e) {
    	  System.err.println("Pas de sauvegarde trouvée, on prend les valeurs par défaut à la place !");
    	  data = this.defaultData();
	    } catch (IOException e) {
	      System.err.println("Autre problème IO...");
	      e.printStackTrace();
    	  data = this.defaultData();
	    }
	  return data;
	}
	
	public Object[][] fetchDataOrdres(Placement place){
	    ObjectInputStream ois;
	    Object[][] data;
	    try {	
	      //On récupère maintenant les données !
	      ois = new ObjectInputStream(
	              new BufferedInputStream(
	                new FileInputStream(
	                  new File(this.nameSvgOrdres(place)))));
	            
	      try {
	        data = (Object[][])ois.readObject();
		    System.out.println("Fichier retrouvé et lu... "+place.getName());
	      } catch (ClassNotFoundException e) {
		    System.err.println("Problème : ClassNotFoundException");
	        e.printStackTrace();
	        // attention ! Il y a trois cas différents (ci-dessous) où on utilise defaultDataOrdres...
	    	data = this.defaultDataOrdres();
	      }
		  
	    ois.close();
	        	
	    } catch (FileNotFoundException e) {
    	  System.err.println("Pas de sauvegarde trouvée, on prend les valeurs par défaut à la place !");
    	  data = this.defaultDataOrdres();
	    } catch (IOException e) {
	      System.err.println("Autre problème IO...");
	      e.printStackTrace();
    	  data = this.defaultDataOrdres();
	    }
	  return data;
	}
	
	public float totalUC(Placement place){
		float totUC=0;
		int totRow = this.dataTransG[place.getIndex()].length;
		for (int i=0; i<totRow; i++){
			totUC += (float)this.dataTransG[place.getIndex()][i][3] - (float)this.dataTransG[place.getIndex()][i][4];
		}
		return totUC;
	}
	
	public float totalEUR(Placement place){
		float totEUR=0;
		int totRow = this.dataTransG[place.getIndex()].length;
		if (totRow == 0) {
			return 0;
		} else {
			for (int i=0; i<totRow; i++){
				totEUR += (float)this.dataTransG[place.getIndex()][i][5] - (float)this.dataTransG[place.getIndex()][i][6];
			}
		return totEUR;
		}
	}
	
	public float prixMoyen(Placement place){
		float prix;
		prix = this.totalEUR(place)/this.totalUC(place);
		return prix;
	}
	
	public Object[][] accueilData(){
		// renvoie les données nécessaires pour remplir le tableau d'accueil.
		int nbTypes = GestionTypes.values().length;
		float[] totauxEUR = new float[nbTypes];
		float totalG = 0;
		Arrays.fill(totauxEUR,0f);
		for (Placement place:Placement.values()){
			totauxEUR[place.getType().getIndex()]+=totalEUR(place);
			totalG += totalEUR(place);
		}
		if (totalG==0){
			System.err.println("Somme totale (EUR) nulle, remplacée par 1");
			totalG=1;
		}
		Object[][] data = new Object[nbTypes+1][3];
		float totalPourcent=0;
		float temp;
		// remplir les premières lignes:
		for (GestionTypes type:GestionTypes.values()){
			temp=totauxEUR[type.getIndex()];
			data[type.getIndex()][0]=type.toString();
			data[type.getIndex()][1]=temp;
			data[type.getIndex()][2]=temp/totalG;
			totalPourcent+=temp/totalG;
		}
		// remplir la dernière ligne
		data[nbTypes][0]="Total";
		data[nbTypes][1]=totalG;
		data[nbTypes][2]=totalPourcent;
		return data;
	}
	
	public void updateCompte(Compte compte, Boolean isChecked){
		comptesSelect[compte.getIndex()]=isChecked;
	}
}
