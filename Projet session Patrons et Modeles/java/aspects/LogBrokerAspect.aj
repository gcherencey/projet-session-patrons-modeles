package aspects;

import java.net.URL;
import java.util.Iterator;
import java.util.logging.Logger;

import javax.xml.ws.Service;

import broker.Broker;

import commun.FormatLog;



public aspect LogBrokerAspect {
		
		Logger logger =  FormatLog.createLogger();
	
		
		
		pointcut loggCallEnvoi (String info, Broker broker): call (boolean Broker.envoyerInformation(String)) && args (info) && target (broker);
		
		pointcut loggCallsAbonne (): call (boolean Broker.sAbonner (..));
		
		pointcut loggGetIP (): call (Object Iterator.next ()) && target (Broker);
		
		pointcut loggCallseDesabonne (): call (boolean broker.Broker.seDesabonner(..));
	    
		
		
		after() returning (Object o): loggGetIP ()
		{
			logger.info ("Adresse destination ->" + o.toString());
		}
		
		
		
		after(String info, Broker broker) returning (boolean reponse): loggCallEnvoi(info, broker)
		{
			if (!broker.auMoinsUnClient ())
			{
				logger.info ("Aucun client n'est connecté.");
			}
			else
			{
				if (reponse == true)
				{
					logger.info("Message -> '"+ info +"' envoyee avec succes");
				}
				
				else
				{
					logger.info("URL invalide, impossible d'envoyer l'information a cette adresse");
				}
			}
		}
	    
		
		after() returning (boolean reponse): loggCallsAbonne() {
	    	  
	    	  if (reponse == true){
	    		  logger.info("Client rajoute avec succes");
	    	  }
	    	  
	    	  else{
	    		  logger.info("Erreur lors de l'ajout du client");
	    	  }
	      }
	      
		
		
	      after() returning (boolean reponse): loggCallseDesabonne() {
	    	  
	    	  if (reponse == true){
	    		  logger.info("Client desabonne avec succes");
	    	  }
	    	  
	    	  else{
	    		  logger.info("Erreur lors de la suppression du client");
	    	  }
	      } 
}
