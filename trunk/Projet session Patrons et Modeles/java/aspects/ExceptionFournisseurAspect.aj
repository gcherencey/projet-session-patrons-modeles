package aspects;

import java.net.MalformedURLException;
import java.util.logging.Logger;
import javax.xml.ws.WebServiceException;
import commun.FormatLog;
import fournisseur.Fournisseur;



/**
 * 
 * Aspect gérant le comportement des exceptions de la classe Fournisseur
 * 
 * @author Gaylord Cherencey, Valentin Brémond, Florian Massacret
 *
 * @version 1.0
 *
 */
public aspect ExceptionFournisseurAspect
{
	//Creation du logger
	Logger logger =  FormatLog.createLogger ();
	
	//POINTCUT
	 
	pointcut LoggCallMain() : execution (public void Fournisseur.main(..));
	
	//ADVICES
	
	after () throwing (Exception e): LoggCallMain ()
	{
		if (e.getClass ().equals (WebServiceException.class))
		{
			logger.severe("Connection perdue avec le broker");
		}
		else if (e.getClass ().equals (MalformedURLException.class))
		{
			logger.severe("URL invalide, impossible d'envoyer l'information a cette adresse");
		}
		
		System.exit (0);
	}
}
