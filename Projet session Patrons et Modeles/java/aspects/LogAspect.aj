package aspects;


public aspect LogAspect {
	
	pointcut loggCalls():call(* broker.Broker.envoyerInformation*(..));

	
		after() : loggCalls() { 
			System.out.println("Après envoie"); 
		} 
}
