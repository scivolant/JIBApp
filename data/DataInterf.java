package gestionSuivi.data;

import gestionSuivi.fenetre.tableauTransaction.ZModel;
import gestionSuivi.placement.Placement;

public interface DataInterf {
	public String nameSvg(Placement place);
	
	public Object[][] defaultData();
	
	public Object[] defaultLine();
	
	public void svgData(Placement place, ZModel model);
	
	public Object[][] lireData(Placement place);
	
	public float totalUC(Placement place, ZModel model);
	
	public float totalEUR(Placement place, ZModel model);
	
	public float prixMoyen(Placement place, ZModel model);
}
