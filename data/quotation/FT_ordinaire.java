package gestion.data.quotation;

import java.sql.Date;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Locale;

import javax.swing.JOptionPane;

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
	public Date getDate(String text) throws QuotationException{
		Date date = new Date(0);
		
        // extraction de la date :
        int p = text.indexOf(" of market ");
        int from = text.indexOf("close ",p);
        int to = text.indexOf(".",from);
        
        System.out.println("FT_ordinaire.getDate -- from = "+from+", to = "+to);

        // ajout de 6, correspond à la taille de "as of " dans "from"
        String dateString= text.substring(from + 6, to);
        
        System.out.println("FT_ordinaire.getDate -- dateString ="+dateString);
        SimpleDateFormat sdf = new SimpleDateFormat("MMM dd yyyy",Locale.US);
        try{
        	date = new Date(sdf.parse(dateString).getTime());
		} catch (IllegalArgumentException e){
			e.printStackTrace();
			JOptionPane jop = new JOptionPane();
			jop.showMessageDialog(null, "Date pas au format MMM dd yyyy ? "+e.getMessage(),"ERREUR ds FT_ordinaire.getDate",JOptionPane.ERROR_MESSAGE);
			throw new QuotationException();
		} catch (Exception e){
			e.printStackTrace();
			JOptionPane jop = new JOptionPane();
			jop.showMessageDialog(null, e.getMessage(),"ERREUR ds FT_ordinaire.getDate",JOptionPane.ERROR_MESSAGE);
			throw new QuotationException();
		}
        return date;
	}

	@Override
	public float getPrice(String text) throws QuotationException{
		float price=0.f;
		
        int p = text.indexOf("class=\"text first\"");
        int from = text.indexOf(">",p);
        int to = text.indexOf("</td>",from);
        
        System.out.println("FT_ordinaire.getPrice -- from = "+from+", to = "+to);
        // ajout de 1, correspond à la taille de ">" dans "from"
        String priceString= text.substring(from + 1, to);
        
        System.out.println("FT_ordinaire.getPrice -- priceString ="+priceString);
		NumberFormat format = NumberFormat.getInstance(Locale.US);
        try{
        	price = format.parse((String)priceString).floatValue();
        	System.out.println("Prix récupéré = "+price);
		} catch (IllegalArgumentException e){
			e.printStackTrace();
			JOptionPane jop = new JOptionPane();
			jop.showMessageDialog(null, "priceString de la mauvaise forme ? "+e.getMessage(),"ERREUR ds FT_ordinaire.getPrice",JOptionPane.ERROR_MESSAGE);
			throw new QuotationException();
		} catch (Exception e){
			e.printStackTrace();
			JOptionPane jop = new JOptionPane();
			jop.showMessageDialog(null, e.getMessage(),"ERREUR ds FT_ordinaire.getPrice",JOptionPane.ERROR_MESSAGE);
			throw new QuotationException();
		}
        return price;
	}
}
