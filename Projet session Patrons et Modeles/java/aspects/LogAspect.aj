package aspects;

import java.util.Iterator;
import java.util.logging.Logger;



public aspect LogAspect {
		
		FormatLog formatter = new FormatLog();
		Logger logger =  formatter.creatLogger();
	
		pointcut loggCallEnvoi(String info): call(boolean broker.Broker.envoyerInformation(String))
											 && args(info);
		pointcut loggCallsAbonne(): call(boolean broker.Broker.sAbonner(..));
		pointcut loggGetIP(): target(broker.Broker) && call(Object Iterator.next());
		pointcut loggCallseDesabonne(): call(boolean broker.Broker.seDesabonner(..));
	    
		  after() returning (Object o): loggGetIP(){
			  logger.info("Adresse destination ->" + o.toString());
		  }
		  
	      after(String info) returning (boolean reponse): loggCallEnvoi(info) {
	    	  
	    	  
	    	  if (reponse == true){
	    		  logger.info("Message -> '"+ info +"' envoyee avec succes");
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
