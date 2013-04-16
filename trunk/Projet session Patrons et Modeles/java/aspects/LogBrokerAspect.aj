package aspects;

import interfaces.ClientInterface;

import java.util.HashMap;
import java.util.HashSet;
import java.util.logging.Logger;

import broker.Broker;
import broker.BrokerImplementation;

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
		
		pointcut loggCallEnvoi (Information info, Broker broker) : execution (boolean ClientInterface.envoyerInformation (Information)) && args (info) && target (broker);
		
		pointcut loggCallsAbonne (HashSet<String> listeTypesInformations): execution (boolean Broker.sAbonner (.., HashSet<String>)) && args (.., listeTypesInformations);
		
		pointcut loggGetIP (Object adresseClient) : execution (boolean HashMap.get(Object)) && args(adresseClient);
		
		pointcut loggCallseDesabonne () : call (boolean broker.Broker.seDesabonner (..));
	    
		pointcut loggCallReceptionTypes () : call (  BrokerImplementation.recupererTypesInformations()) && within(Broker);
		
		//ADVICES
		
		after () returning (HashSet<String> listeTypesInformation) : loggCallReceptionTypes ()
		{
			logger.info(listeTypesInformation.toString());
		}
		
		
		
		
		//ADVICES
		
		//Affichage de l'adresse IP destination apres appel de la methode Iterator.next () dans la classe Broker
		after (Object adresseClient) returning (boolean resultat) : loggGetIP (adresseClient)
		{
			logger.info ("Adresse destination -> " + adresseClient);
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
		after (HashSet<String> listeTypesInformations) returning (boolean reponse) : loggCallsAbonne (listeTypesInformations)
		{
			if (reponse)
			{
				logger.info ("Client rajoute avec succes");
				logger.info ("Le client a souscrit aux services: " + listeTypesInformations.toString());
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