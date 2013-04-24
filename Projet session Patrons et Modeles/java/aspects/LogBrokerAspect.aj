package aspects;

import interfaces.ClientInterface;

import java.util.HashSet;
import java.util.logging.Logger;

import broker.Broker;

import commun.FormatLog;
import commun.Information;



/**
 *
 * Aspect gérant les logs du broker.
 * 
 * @author Gaylord Cherencey, Valentin Brémond, Florian Massacret
 * 
 * @version 1.0
 *
 */
public aspect LogBrokerAspect
{
	//Creation du logger
	Logger logger =  FormatLog.createLogger ();
		
	//POINTCUTS
		
	pointcut loggCallDemarrage(): call (Broker.new(..));
	
	pointcut loggCallsAbonne (HashSet<String> listeTypesInformations): execution (boolean Broker.sAbonner (.., HashSet<String>)) && args (.., listeTypesInformations);
	
	pointcut loggCallAjoutType (String type) : execution (boolean Broker.ajouterTypeInformation (String)) && args(type);
	
	pointcut loggCallRecupererTypesInformations () : execution (HashSet<String> Broker.recupererTypesInformations ());

	pointcut loggCallEnvoi (ClientInterface client, String adresseClient, Information info) : call (void Broker.envoieAuClient(ClientInterface, String , Information)) && args (client, adresseClient ,info);
	
	pointcut loggCallseDesabonne () : call (boolean broker.Broker.seDesabonner (..));
		
	//ADVICES
	
	before () : loggCallDemarrage()
	{
		logger.info ("Démarrage du broker");
		logger.info ("En attente d'un nouveau client...");
	}
	
	//Affichage de messages apres appel de la methode sAbonner dans la classe Broker
	after (HashSet<String> listeTypesInformations) returning (boolean reponse) : loggCallsAbonne (listeTypesInformations)
	{
		if (reponse)
		{
			logger.info ("Client rajouté avec succès");
			logger.info ("Le client a souscrit aux types : " + listeTypesInformations.toString());
	   	}  
	   	else
	   	{
	   		logger.info ("Erreur lors de l'ajout du client");
	   	}
	}
	
	
	//Afficher les services ajoutes par le fournisseur
	after(String type) returning (boolean reponse) : loggCallAjoutType (type)
	{
		if(reponse)
		{
			logger.info("Nouveau type disponible -> " + type);
		}
		else
		{
			logger.warning("Le type [" + type + "] n'a pu être ajouté (peut-être est-il déjà présent)");
		}
	}
	
	//Affichage des types d'information par le broker
	after () returning (HashSet<String> listeTypeInformations) : loggCallRecupererTypesInformations()
	{
		if(!listeTypeInformations.isEmpty())
		{
			logger.info("Types proposés -> " + listeTypeInformations.toString());
		}
		
		else
		{
			logger.info("Erreur lors de la réception des types");
		}
	}
	
	
	//Affichage de messages apres appel de la methode envoyerInformation dans la classe Broker
	after (ClientInterface client, String adresseClient, Information info) : loggCallEnvoi (client, adresseClient ,info)
	{
		logger.info("Envoi du message -> [" + info.getTypeToString() + "] " + info.getInformation());
		logger.info("Adresse destination -> " + adresseClient);
	}

	
	//Affichage de messages selon comportement de la methode seDesabonner dans la classe Broker
	after () returning (boolean reponse) : loggCallseDesabonne ()
	{
		if (reponse)
		{
			logger.info ("Client désabonné avec succès");
    	}
    	else
    	{
    		logger.info ("Erreur lors de la suppression du client");
    	}
	}
}