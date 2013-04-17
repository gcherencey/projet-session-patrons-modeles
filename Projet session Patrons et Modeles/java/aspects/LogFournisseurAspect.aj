package aspects;

import java.util.logging.Logger;

import commun.FormatLog;
import commun.Information;

import fournisseur.Fournisseur;

/**
 * Aspect gerant le comportement des methodes selon leurs retours de la classe Fournisseur
 * @author CHERENCEY Gaylord, BREMOND Valentin, MASSACRET Florian
 **/

public aspect LogFournisseurAspect
{
	//Creation du logger
	Logger logger =  FormatLog.createLogger ();
		
	//POINTCUT
	
	pointcut loggCallEnvoiType (String type) : call (boolean Fournisseur.ajouterTypeInformation (String)) && args(type);
	
	pointcut loggCallEnvoiMessage (Information info) : call (boolean Fournisseur.envoyerInformation (Information)) && args(info);
	
	//ADVICES
	
	after (String type) returning (boolean reponse) : loggCallEnvoiType (type)
	{
		if(reponse)
		{
			logger.info ("Envoi du type -> " + type);
		}
		else
		{
			logger.warning ("Le service [" + type + "] n'a pu etre envoye (peut etre est-il deja present sur le broker)");
		}
	}
	
	after (Information info) returning (boolean reponse) : loggCallEnvoiMessage (info)
	{
		if(reponse)
		{
			logger.info ("Envoi du message au broker -> [" + info.getTypeToString() + "] " + info.getInformation());
		}
		else
		{
			logger.warning ("Erreur lors de l'envoi du message -> [" + info.getTypeToString() + "] " + info.getInformation());
		}
	}
}