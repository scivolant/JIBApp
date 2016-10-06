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

public class DataIni implements DataInterf {
	private static DataIni instance= new DataIni();
	private static int nbInstance =0;
	/*
	// nbr de colonnes dans un tableau de dataTrans
	private static int nbCol = 8;
	*/
	
	
	private Object[][][] dataTransG;
	
	private DataIni(){
		nbInstance +=1;
		System.out.println(nbInstance);
		int nbPlace = Placement.values().length;
		dataTransG = new Object[nbPlace][][];
		for (Placement place:Placement.values()){
			dataTransG[place.getIndex()]=this.fetchData(place);
		}
	}
	
	public static DataIni getInstance(){
		return instance;
	}

	@Override
	public String nameSvg(Placement place) {
		// TODO Auto-generated method stub
		return "svg_"+place.getMnemo()+".TransSvg";
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
	
	public Object[] defaultLine(){
		Compte[] listeCompte = Compte.values();
		Object[] ligneDefault= {"16/08/1983", listeCompte[0], new Float(15.0d), new Float(1), new Float(0), new Float(15d), new Float(0), "-"};
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
	
	public Object[][] lireData(Placement place){
		return this.dataTransG[place.getIndex()];
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
}
