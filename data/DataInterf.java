package gestionSuivi.data;

import gestionSuivi.placement.Placement;

public interface DataInterf {
	public String nameSvg(Placement place);
	
	public Object[][] defaultData();
	
	public Object[] defaultLine();
}
