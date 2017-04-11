package gestion.data.quotation;

import java.sql.Date;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Locale;

import javax.swing.JOptionPane;

import gestion.data.dao.DaoException;

/*
 * Dernière tentative (2017.02.24)
 * Ca ne marchait pas à cause du certificat de sécurité (HTTPS)
 */

public class SicavOnline extends Transfer {
	
	// Une seule instance attendue (mais un singleton paraissait un peu trop complexe)
	// En outre, les "Transfer" seront sans doute appelés via Transfer.values()
	public SicavOnline() {
		super("Sicav Online");
	}
	
	// Identifiant de cette classe :
	public int getIdTransf(){
		return 5;
	}

	@Override
	public Date getDate(String text) throws DaoException{
		Date date = new Date(0);
		
        // extraction de la date :
        int p = text.indexOf("Valeur liquidative");
        int from = text.indexOf(" au ",p);
        int to = text.indexOf(":",from);
        
        System.out.println("SicavOnline.getDate -- from = "+from+", to = "+to);

        // ajout de 4, correspond à la taille de " au " dans "from"
        String dateString= text.substring(from + 4, to);
        
        System.out.println("SicavOnline.getDate -- dateString ="+dateString);
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy",Locale.FRANCE);
        try{
        	date = new Date(sdf.parse(dateString).getTime());
		} catch (IllegalArgumentException e){
			e.printStackTrace();
			JOptionPane jop = new JOptionPane();
			jop.showMessageDialog(null, "Date pas au format dd/MM/yyyy ? "+e.getMessage(),"ERREUR ds SicavOnline.getDate",JOptionPane.ERROR_MESSAGE);
			throw new DaoException();
		} catch (Exception e){
			e.printStackTrace();
			JOptionPane jop = new JOptionPane();
			jop.showMessageDialog(null, e.getMessage(),"ERREUR ds SicavOnline.getDate",JOptionPane.ERROR_MESSAGE);
			throw new DaoException();
		}
        return date;
	}

	@Override
	public float getPrice(String text) throws DaoException{
		float price=0.f;
		
        int p = text.indexOf("<div id=\"VL\"");
        int from = text.indexOf(">",p);
        int to = text.indexOf("</div>",from);
        
        System.out.println("SicavOnline.getPrice -- from = "+from+", to = "+to);
        // ajout de 1, correspond à la taille de ">" dans "from"
        String priceString= text.substring(from + 1, to);
        
        System.out.println("SicavOnline.getPrice -- priceString ="+priceString);
		NumberFormat format = NumberFormat.getInstance(Locale.FRANCE);
        try{
        	price = format.parse((String)priceString).floatValue();
        	System.out.println("Prix récupéré = "+price);
		} catch (IllegalArgumentException e){
			e.printStackTrace();
			JOptionPane jop = new JOptionPane();
			jop.showMessageDialog(null, "priceString de la mauvaise forme ? "+e.getMessage(),"ERREUR ds SicavOnline.getPrice",JOptionPane.ERROR_MESSAGE);
			throw new DaoException();
		} catch (Exception e){
			e.printStackTrace();
			JOptionPane jop = new JOptionPane();
			jop.showMessageDialog(null, e.getMessage(),"ERREUR ds SicavOnline.getPrice",JOptionPane.ERROR_MESSAGE);
			throw new DaoException();
		}
        return price;
	}
}
