package aspects;

import interfaces.BrokerInterface;

import java.util.logging.Logger;

import client.Client;

import commun.FormatLog;
import commun.Information;



/**
 * 
 * Aspect permettant de gérer les logs et les exceptions du client.
 * 
 * @author Valentin Brémond, Gaylord Cherencey, Florian Massacret
 * 
 * @version 1.0
 *
 */
public aspect LogClientAspect
{
	// On créé le logger
	Logger log = FormatLog.createLogger ();
	
	
	
	// MÉTHODES
	
	
	
	/**
	 * Se déclenche lors du démarrage de l'application.
	 */
	before () : call (Client.new (..))
	{
		log.info ("Démarrage de l'application client...");
	}
	
	
	
	/**
	 * Se déclenche lors de l'arrêt de l'application.
	 */
	before () : call (* System.exit (..))
	{
		log.info ("Application client terminée");
	}
	
	
	
	/**
	 * Se déclenche lors de la connexion au broker.
	 * 
	 * @param urlBroker L'URL du broker.
	 */
	BrokerInterface around (String urlBroker) : call (BrokerInterface Client.connexionBroker (String)) && args (urlBroker)
	{
		BrokerInterface retour;
		
		log.info ("Tentative de connexion au serveur " + urlBroker + "...");
		
		retour = proceed (urlBroker);
		
		log.info ("Connexion réussie au serveur " + urlBroker);
		
		return retour;
	}
	
	
	
	/**
	 * Se déclenche lors de l'abonnement.
	 */
	boolean around () : call (boolean BrokerInterface.sAbonner ())
	{
		boolean retour;
		
		log.info ("Tentative d'abonnement au broker...");
		
		retour = proceed ();
		
		if (retour)
		{
			log.info ("Abonnement réussi");
		}
		else
		{
			log.warning ("Abonnement impossible");
		}
		
		return retour;
	}
	
	
	
	/**
	 * Se déclenche à chaque nouvelle information.
	 * 
	 * @param info L'information qui doit être affichée.
	 */
	before (Information info) : call (void Client.setInfo (Information)) && args (info)
	{
		log.info ("Information reçue : '[" + info.getTypeToString() + "] " + info.getInformation() + "'");
	}
	
	
	
	/**
	 * Se déclenche lors du désabonnement.
	 */
	boolean around () : call (boolean BrokerInterface.seDesabonner ())
	{
		boolean retour;
		
		log.info ("Tentative de désabonnement du broker...");
		
		retour = proceed ();
		
		if (retour)
		{
			log.info ("Désabonnement réussi");
		}
		else
		{
			log.warning ("Désabonnement impossible");
		}
		
		return retour;
	}
}
