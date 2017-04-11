package gestion.data.quotation;

import java.sql.Date;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Locale;

import javax.swing.JOptionPane;

import gestion.data.dao.DaoException;

/*
 * Derni�re tentative (2017.02.24) :
 * Le code renvoy� n'est pas le m�me que celui que je peux voir avec Firefox (?)
 */

public class Quantalys extends Transfer {
	
	// Une seule instance attendue (mais un singleton paraissait un peu trop complexe)
	// En outre, les "Transfer" seront sans doute appel�s via Transfer.values()
	public Quantalys() {
		super("Quantalys");
	}
	
	// Identifiant de cette classe :
	public int getIdTransf(){
		return 4;
	}

	@Override
	public Date getDate(String text) throws DaoException{
		Date date = new Date(0);
		
        // extraction de la date :
        int p = text.indexOf("\"Contenu_Contenu_Contenu_lblDtVL\"");
        int from = text.indexOf("> (",p);
        int to = text.indexOf(")",from);
        
        System.out.println("Quantalys.getDate -- from = "+from+", to = "+to);

        // ajout de 3, correspond � la taille de "> (" dans "from"
        String dateString= text.substring(from + 3, to);
        
        System.out.println("Quantalys.getDate -- dateString ="+dateString);
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy",Locale.FRANCE);
        try{
        	date = new Date(sdf.parse(dateString).getTime());
		} catch (IllegalArgumentException e){
			e.printStackTrace();
			JOptionPane jop = new JOptionPane();
			jop.showMessageDialog(null, "Date pas au format dd/MM/yyyy ? "+e.getMessage(),"ERREUR ds Quantalys.getDate",JOptionPane.ERROR_MESSAGE);
			throw new DaoException();
		} catch (Exception e){
			e.printStackTrace();
			JOptionPane jop = new JOptionPane();
			jop.showMessageDialog(null, e.getMessage(),"ERREUR ds Quantalys.getDate",JOptionPane.ERROR_MESSAGE);
			throw new DaoException();
		}
        return date;
	}

	@Override
	public float getPrice(String text) throws DaoException{
		float price=0.f;
		
        int p = text.indexOf("\"Contenu_Contenu_Contenu_lblVLDevise\"");
        int from = text.indexOf(">",p);
        int to = text.indexOf(" EUR",from);
        
        System.out.println("Quantalys.getPrice -- from = "+from+", to = "+to);
        // ajout de 1, correspond � la taille de ">" dans "from"
        String priceString= text.substring(from + 1, to);
        
        System.out.println("Quantalys.getPrice -- priceString ="+priceString);
		NumberFormat format = NumberFormat.getInstance(Locale.FRANCE);
        try{
        	price = format.parse((String)priceString).floatValue();
        	System.out.println("Prix r�cup�r� = "+price);
		} catch (IllegalArgumentException e){
			e.printStackTrace();
			JOptionPane jop = new JOptionPane();
			jop.showMessageDialog(null, "priceString de la mauvaise forme ? "+e.getMessage(),"ERREUR ds Quantalys.getPrice",JOptionPane.ERROR_MESSAGE);
			throw new DaoException();
		} catch (Exception e){
			e.printStackTrace();
			JOptionPane jop = new JOptionPane();
			jop.showMessageDialog(null, e.getMessage(),"ERREUR ds Quantalys.getPrice",JOptionPane.ERROR_MESSAGE);
			throw new DaoException();
		}
        return price;
	}
}
