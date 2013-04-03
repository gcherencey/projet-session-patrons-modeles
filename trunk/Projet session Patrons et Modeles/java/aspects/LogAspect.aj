package aspects;
import broker.Broker;


public aspect LogAspect {
	pointcut publicLog():call(public * Broker.*(..));
	pointcut loggCalls(): publicLog();
	
		after() : loggCalls() { 
			System.out.println("Apr�s le retrait"); 
		} 
}
