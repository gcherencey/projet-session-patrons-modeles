package aspects;

import java.net.MalformedURLException;
import java.util.logging.Logger;
import javax.xml.ws.WebServiceException;
import commun.FormatLog;
import fournisseur.Fournisseur;

/**
 * Aspect gerant le comportement des exceptions de la classe Fournisseur
 * @author CHERENCEY Gaylord, BREMOND Valentin, MASSACRET Florian
 *
 */

public aspect ExceptionFournisseurAspect
{
	//Creation du logger
	Logger logger =  FormatLog.createLogger ();
	
	//POINTCUT
	 
	pointcut LoggCallMain() : execution(public void Fournisseur.main(..));
	
	//ADVICES
	
	void around() : LoggCallMain() 
	{
		try {
				proceed();
			} catch(WebServiceException e) {
				logger.severe("Connection perdue avec le broker");
			}catch(MalformedURLException e) {
				logger.severe("URL invalide, impossible d'envoyer l'information a cette adresse");
	  		}
	  }
}
