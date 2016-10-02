package gestionSuivi.data;

import gestionSuivi.compte.Compte;
import gestionSuivi.placement.Placement;

public class DataIni implements DataInterf {

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
			      {"13/12/2013", listeCompte[0], new Float(15.0d), new Float(0), new Float(4), new Float(0d), new Float(60d),"-"}		
				};
		return data;
	}
	
	public Object[] defaultLine(){
		Compte[] listeCompte = Compte.values();
		Object[] ligneDefault= {"16/08/1983", listeCompte[0], new Float(15.0d), new Float(1), new Float(0), new Float(15d), new Float(0), "-"};
		return ligneDefault;
	}

}
