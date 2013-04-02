package broker;

import java.util.ArrayList;

import javax.xml.ws.Endpoint;

public class Broker
{
	
	/**
	 * @param args
	 */
	public static void main (String[] args)
	{
		// TODO Auto-generated method stub
		Endpoint.publish ("http://localhost:9998/ws/broker", new BrokerImplementation());
	}	

}
