package gestion.compta;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.ConnectException;
import java.net.URL;

import javax.swing.JOptionPane;

import gestion.data.dao.DaoException;
import gestion.data.quotation.Transfer;

public class SourceQuote {
	private int id_source;
	private String name;
	private String url;
	private Transfer transf;

	// Initialis� � 0, mis � jour dans SourceQuoteDAO.create...
	public SourceQuote(String name, String url, Transfer transf) {
		this.id_source = 0;
		this.name = name;
		this.url = url;
		this.transf = transf;
	}

	public int getIdSource() {
		return id_source;
	}

	public void setIdSource(int id_source) {
		this.id_source = id_source;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Transfer getTransf() {
		return transf;
	}

	public void setTransf(Transfer transf) {
		this.transf = transf;
	}
	
	public String toString(){
		return this.name;
	}
	
	// Renvoie un cours non-initialis�
	public Cours getCours(Placement place) throws DaoException{
		String text ="";
		Cours cours = null;
		System.out.println("=== SourceQuote.getCours("+place.toString()+") ===");
		try{
	    	URL urlLink = new URL(url + place.getCodeMaJ());
	        BufferedReader in = new BufferedReader(
	                new InputStreamReader(urlLink.openStream()));
	        
	        // R�cup�re tout le texte � l'URL donn�e
	        String inputLine;
	        while ((inputLine=in.readLine())!= null){
	        	text+="\n"+inputLine;
	        }
	        System.out.println(">> page web r�cup�r�e ");
	        cours = new Cours(
	    	        transf.getDate(text),
	    	        place,
	    	        transf.getPrice(text)
	    	        );
		} catch (ConnectException e){
			JOptionPane jop = new JOptionPane();
			jop.showMessageDialog(null, e.getMessage(),"ERREUR !",JOptionPane.ERROR_MESSAGE);
	    	throw new DaoException();
		} catch (Exception e){
	    	e.printStackTrace();
	    	throw new DaoException();
		}
		return cours;
	}

	public static SourceQuote defaultEntry() {
		SourceQuote source = new SourceQuote(
				"Source par d�faut",
				"http://",
				Transfer.values()[0]
				);
		return source;
	}

}
