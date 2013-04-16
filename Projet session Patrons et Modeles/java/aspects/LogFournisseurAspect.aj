package aspects;

import fournisseur.Fournisseur;
import interfaces.BrokerInterface;

import java.util.logging.Logger;

import commun.FormatLog;

/**
 * Aspect gerant le comportement des methodes selon leurs retours de la classe Fournisseur
 * @author CHERENCEY Gaylord, BREMOND Valentin, MASSACRET Florian
 **/

public aspect LogFournisseurAspect
{
	//Creation du logger
	Logger logger =  FormatLog.createLogger ();
		
	//POINTCUT
	
	pointcut loggCallEnvoiType (String type) : call (boolean BrokerInterface.ajouterTypeInformation (String)) && args(type) && within(Fournisseur);
	
	//ADVICES
	
	after (String type) returning (boolean reponse) : loggCallEnvoiType (type)
	{
		if(reponse)
		{
			logger.info ("Envoi du type -> " + type);
		}
		else
		{
			logger.warning ("Erreur lors de l'envoi du type -> " + type);
		}
	}
}