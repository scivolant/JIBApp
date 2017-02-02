package gestionSuivi.data;

import gestionSuivi.compte.Compte;
import gestionSuivi.fenetrePlacement.ZModel;
import gestionSuivi.placement.Placement;

public interface DataInterf {
	// Pour avoir une unique instance de la base de donn�es,
	// un patron "singleton" est n�cessaire, mais pas codable dans l'interface
	// => il est r�alis� directement dans la classe concr�te.
	
	public String nameSvg(Placement place);
	
	public Object[][] defaultData();
	
	public Object[] defaultLine();
	
	public Object[] defaultLineOrdres();
	
	// sauvegarde les donn�es dans des fichiers
	public void svgData(Placement place, ZModel model);
	public void svgDataOrdres(Placement place, ZModel model);
	
	// pour chercher la copie des donn�es dans DataIni
	public Object[][] lireData(Placement place);
	public Object[][] lireDataOrdres(Placement place);
	
	// pour chercher les donn�es depuis les fichiers externes
	public Object[][] fetchData(Placement place);
	public Object[][] fetchDataOrdres(Placement place);
	
	public float totalUC(Placement place);
	public float totalEUR(Placement place);
	public float prixMoyen(Placement place);
	
	public Object[][] accueilData();
	
	// mets � jour la liste des comptes s�lectionn�s
	public void updateCompte(Compte compte, Boolean isChecked);
}
