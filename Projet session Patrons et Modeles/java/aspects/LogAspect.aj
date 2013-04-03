package aspects;


public aspect LogAspect {
	
		pointcut loggCall(): call(boolean broker.Broker.envoyerInformation(..));
		
	      after() returning (boolean reponse): loggCall() {
	    	  
	    	  if (reponse == true){
	    		  System.out.println("Message envoyee avec succes");
	    	  }
	    	  
	    	  else{
	    		  System.err.println("URL invalide, impossible d'envoyer l'information a cette adresse");
	    	  }
	      }
		
}
