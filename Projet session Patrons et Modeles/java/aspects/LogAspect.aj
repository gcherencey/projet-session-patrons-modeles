package aspects;

import java.util.logging.Logger;


public aspect LogAspect {
	
		Logger logger = Logger.getLogger("trace");
	
		pointcut loggCallEnvoi(): call(boolean broker.Broker.envoyerInformation(..));
		pointcut loggCallsAbonne(): call(boolean broker.Broker.sAbonner(..));
		pointcut loggCallseDesabonne(): call(boolean broker.Broker.seDesabonner(..));
		
	      after() returning (boolean reponse): loggCallEnvoi() {
	    	  
	    	  if (reponse == true){
	    		  logger.info("Message envoyee avec succes");
	    	  }
	    	  
	    	  else{
	    		  logger.info("URL invalide, impossible d'envoyer l'information a cette adresse");
	    	  }
	      }
	      
	      after() returning (boolean reponse): loggCallsAbonne() {
	    	  
	    	  if (reponse == true){
	    		  logger.info("Client rajoute avec succes");
	    	  }
	    	  
	    	  else{
	    		  logger.info("Erreur la de l'ajout du client");
	    	  }
	      }
	      
	      after() returning (boolean reponse): loggCallseDesabonne() {
	    	  
	    	  if (reponse == true){
	    		  logger.info("Client desabonne avec succes");
	    	  }
	    	  
	    	  else{
	    		  logger.info("Erreur la de la suppression du client");
	    	  }
	      }
	      
}
