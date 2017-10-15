package gestion.data.quotation;

import java.sql.Date;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Locale;

import javax.swing.JOptionPane;

import gestion.data.dao.DaoException;

public class FT_ordinaire extends Transfer {
	
	// Une seule instance attendue (mais un singleton paraissait un peu trop complexe)
	// En outre, les "Transfer" seront sans doute appelés via Transfer.values()
	public FT_ordinaire() {
		super("FT ordinaire (transf.)");
	}
	
	// Identifiant de cette classe :
	public int getIdTransf(){
		return 2;
	}

	@Override
	public Date getDate(String text) throws DaoException{
		Date date = new Date(0);
		
        // extraction de la date :
        int p = text.indexOf("--</span></li></ul><div class=\"mod-disclaimer\">");
        String fromS = "as of ";
        int delta = fromS.length();
        int from = text.indexOf(fromS,p);
        int to = text.indexOf(".",from);
        
        System.out.println("FT_ordinaire.getDate -- from = "+from+delta+", to = "+to);

        // ajout de delta, correspond taille de fromS
        String dateString= text.substring(from + delta, to);
        
        System.out.println("FT_ordinaire.getDate -- dateString ="+dateString);
        SimpleDateFormat sdf = new SimpleDateFormat("MMM dd yyyy",Locale.US);
        try{
        	date = new Date(sdf.parse(dateString).getTime());
		} catch (IllegalArgumentException e){
			e.printStackTrace();
			JOptionPane jop = new JOptionPane();
			jop.showMessageDialog(null, "Date pas au format MMM dd yyyy ? "+e.getMessage(),"ERREUR ds FT_ordinaire.getDate",JOptionPane.ERROR_MESSAGE);
			throw new DaoException();
		} catch (Exception e){
			e.printStackTrace();
			JOptionPane jop = new JOptionPane();
			jop.showMessageDialog(null, e.getMessage(),"ERREUR ds FT_ordinaire.getDate",JOptionPane.ERROR_MESSAGE);
			throw new DaoException();
		}
        return date;
	}

	@Override
	public float getPrice(String text) throws DaoException{
		float price=0.f;
		
        int p = text.indexOf("Price (EUR)");
        String fromS = "</span><span class=\"mod-ui-data-list__value\">";
        int delta = fromS.length();
        int from = text.indexOf(fromS,p);
        int to = text.indexOf("</span>",from + delta);
        
        System.out.println("FT_ordinaire.getPrice -- from = "+from+delta+", to = "+to);
        // ajout de delta, correspond à la taille de fromS
        String priceString= text.substring(from + delta, to);
        
        System.out.println("FT_ordinaire.getPrice -- priceString ="+priceString);
		NumberFormat format = NumberFormat.getInstance(Locale.US);
        try{
        	price = format.parse((String)priceString).floatValue();
        	System.out.println("Prix récupéré = "+price);
		} catch (IllegalArgumentException e){
			e.printStackTrace();
			JOptionPane jop = new JOptionPane();
			jop.showMessageDialog(null, "priceString de la mauvaise forme ? "+e.getMessage(),"ERREUR ds FT_ordinaire.getPrice",JOptionPane.ERROR_MESSAGE);
			throw new DaoException();
		} catch (Exception e){
			e.printStackTrace();
			JOptionPane jop = new JOptionPane();
			jop.showMessageDialog(null, e.getMessage(),"ERREUR ds FT_ordinaire.getPrice",JOptionPane.ERROR_MESSAGE);
			throw new DaoException();
		}
        return price;
	}
}
