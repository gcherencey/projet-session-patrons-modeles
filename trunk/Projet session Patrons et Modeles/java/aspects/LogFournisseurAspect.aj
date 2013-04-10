package aspects;

import java.util.logging.Logger;

import broker.Broker;

import commun.FormatLog;

/**
 * Aspect gerant le comportement des methodes selon leurs retours de la classe Fournisseur
 * @author CHERENCEY Gaylord, BREMOND Valentin, MASSACRET Florian
 **/

public aspect LogFournisseurAspect {

	//Creation du logger
	Logger logger =  FormatLog.createLogger ();
		
	//POINTCUT
	
	pointcut loggCallEnvoiType (String type) : call (boolean Broker.ajouterTypeInformation(String)) && args(type);
	
	//ADVICES
	
	after (String type) returning (boolean reponse) : loggCallEnvoiType (type)
	{
		if(reponse){
			logger.info ("Envoi du type -> " + type);
		}
		else
		{
			logger.info("Erreur lors de l'envoi du type -> " + type);
		}
	}
	
}