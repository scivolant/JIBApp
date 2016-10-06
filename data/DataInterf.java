package gestionSuivi.data;

import gestionSuivi.fenetrePlacement.ZModel;
import gestionSuivi.placement.Placement;

public interface DataInterf {
	// Pour avoir une unique instance de la base de données,
	// un patron "singleton" est nécessaire, mais pas codable dans l'interface
	// => il est réalisé directement dans la classe concrète.
	
	public String nameSvg(Placement place);
	
	public Object[][] defaultData();
	
	public Object[] defaultLine();
	
	public void svgData(Placement place, ZModel model);
	
	// pour chercher la copie des données dans DataIni
	public Object[][] lireData(Placement place);
	
	// pour chercher les données depuis les fichiers externes
	public Object[][] fetchData(Placement place);
	
	public float totalUC(Placement place);
	
	public float totalEUR(Placement place);
	
	public float prixMoyen(Placement place);
	
	public Object[][] accueilData();
}
