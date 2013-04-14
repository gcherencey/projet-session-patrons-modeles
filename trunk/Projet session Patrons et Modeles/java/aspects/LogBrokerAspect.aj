package aspects;

import java.util.Iterator;
import java.util.logging.Logger;

import broker.Broker;

import commun.FormatLog;
import commun.Information;

/**
 * Aspect gerant le comportement des methodes selon leurs retours de la classe Broker
 * @author CHERENCEY Gaylord, BREMOND Valentin, MASSACRET Florian
 **/

public aspect LogBrokerAspect
{
		//Creation du logger
		Logger logger =  FormatLog.createLogger ();
		
		//POINTCUTS
		
		pointcut loggCallEnvoi (Information info, Broker broker) : call (boolean Broker.envoyerInformation (Information)) && args (info) && target (broker);
		
		pointcut loggCallsAbonne (): call (boolean Broker.sAbonner (..));
		
		pointcut loggGetIP () : call (Object Iterator.next ()) && this (Broker);
		
		pointcut loggCallseDesabonne () : call (boolean broker.Broker.seDesabonner (..));
	    
		
		//ADVICES
		
		//Affichage de l'adresse IP destination apres appel de la methode Iterator.next () dans la classe Broker
		after () returning (Object o) : loggGetIP ()
		{
			logger.info ("Adresse destination -> " + o.toString ());
		}
		
		//Affichage de messages apres appel de la methode envoyerInformation dans la classe Broker
		after (Information info, Broker broker) returning (boolean reponse) : loggCallEnvoi (info, broker)
		{
			//Si il n'y a pas de client connecte, on affiche "Aucun client n'est connecte"
			if (!broker.auMoinsUnClient ())
			{
				logger.info ("Aucun client n'est connecte.");
			}
			else
			{
				//Si le message a bien ete envoye on affiche le message en question
				if (reponse == true)
				{
					logger.info ("Message ->  [" + info.getTypeToString() + "] "+ info.getInformation() + " envoyee avec succes");
				}
				else
				{
					logger.info ("URL invalide, impossible d'envoyer l'information a cette adresse");
				}
			}
		}
	    
		//Affichage de messages apres appel de la methode sAbonner dans la classe Broker
		after () returning (boolean reponse) : loggCallsAbonne ()
		{
			if (reponse)
			{
				logger.info ("Client rajoute avec succes");
	    	}  
	    	else
	    	{
	    		logger.info ("Erreur lors de l'ajout du client");
	    	}
		}
	      
		//Affichage de messages selon comportement de la methode seDesabonner dans la classe Broker
		after () returning (boolean reponse) : loggCallseDesabonne ()
		{
			if (reponse)
			{
				logger.info ("Client desabonne avec succes");
	    	}
	    	else
	    	{
	    		logger.info ("Erreur lors de la suppression du client");
	    	}
		}
}
