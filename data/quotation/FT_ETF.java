package gestion.data.quotation;

import java.sql.Date;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Locale;

import javax.swing.JOptionPane;

import gestion.data.dao.DaoException;

public class FT_ETF extends Transfer {
	
	// Une seule instance attendue (mais un singleton paraissait un peu trop complexe)
	// En outre, les "Transfer" seront sans doute appel�s via Transfer.values()
	public FT_ETF() {
		super("FT ETF (transf.)");
	}

	@Override
	public Date getDate(String text) throws DaoException{
		Date date = new Date(0);
		
        // extraction de la date :
        int p = text.indexOf("<span class=\"delayDisclaimer disclaimerContainer\">Data delayed");
        int from = text.indexOf("as of ",p);
        int to = text.indexOf(".",from);
        
        System.out.println("FT_ETF.getDate -- from = "+from+", to = "+to);

        // ajout de 6, correspond � la taille de "as of " dans "from"
        String dateString= text.substring(from + 6, to);
        
        System.out.println("FT_ETF.getDate -- dateString ="+dateString);
        SimpleDateFormat sdf = new SimpleDateFormat("MMM dd yyyy HH:mm zzz",Locale.US);
        try{
        	date = new Date(sdf.parse(dateString).getTime());
        } catch (ParseException e){
        	try{
        		System.err.println("Dans FT_ETF.getDate() : date pas au format MMM dd yyyy HH:mm zzz, on tente MMM dd yyyy");
                SimpleDateFormat sdf2 = new SimpleDateFormat("MMM dd yyyy",Locale.US);
            	date = new Date(sdf2.parse(dateString).getTime());
        	} catch (Exception e2){
        		System.err.println("Dans FT_ETF.getDate() : MMM dd yyyy ne marche pas non plus.");
    			throw new DaoException();
        	}
		} catch (IllegalArgumentException e){
			e.printStackTrace();
			JOptionPane jop = new JOptionPane();
			jop.showMessageDialog(null, "Date pas au format MMM dd yyyy HH:mm zzz ? "+e.getMessage(),"ERREUR ds FT_ETF.getDate",JOptionPane.ERROR_MESSAGE);
			throw new DaoException();
		} catch (Exception e){
			e.printStackTrace();
			JOptionPane jop = new JOptionPane();
			jop.showMessageDialog(null, e.getMessage(),"ERREUR ds FT_ETF.getDate",JOptionPane.ERROR_MESSAGE);
			throw new DaoException();
		}
        return date;
	}

	@Override
	public float getPrice(String text) throws DaoException{
		float price = 0.f;
		
        int p = text.indexOf("<div class=\"contains wsodModuleContent\"><table><tbody><tr><td class=\"text first\">");
        int from = text.indexOf("<span>",p);
        int to = text.indexOf("</span>",from);
        
        System.out.println("FT_ETF.getPrice -- from = "+from+", to = "+to);
        // ajout de 5, correspond � la taille de "<span>" dans "fromP"
        String priceString= text.substring(from + 6, to);
        
        System.out.println("FT_ETF.getPrice -- priceString ="+priceString);
		NumberFormat format = NumberFormat.getInstance(Locale.US);
        try{
        	price = format.parse((String)priceString).floatValue();
        	System.out.println("Prix r�cup�r� = "+price);
		} catch (IllegalArgumentException e){
			e.printStackTrace();
			JOptionPane jop = new JOptionPane();
			jop.showMessageDialog(null, "priceString de la mauvaise forme ? "+e.getMessage(),"ERREUR ds FT_ETF.getPrice",JOptionPane.ERROR_MESSAGE);
			throw new DaoException();
		} catch (Exception e){
			e.printStackTrace();
			JOptionPane jop = new JOptionPane();
			jop.showMessageDialog(null, e.getMessage(),"ERREUR ds FT_ETF.getPrice",JOptionPane.ERROR_MESSAGE);
			throw new DaoException();
		}
        return price;
	}

	// Identifiant de cette classe :
	public int getIdTransf(){
		return 1;
	}
}
