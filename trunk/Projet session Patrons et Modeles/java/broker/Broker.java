package broker;

import interfaces.ClientInterface;

import java.net.InetSocketAddress;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import javax.xml.namespace.QName;
import javax.xml.ws.Endpoint;
import javax.xml.ws.Service;
import javax.xml.ws.handler.MessageContext;

import com.sun.net.httpserver.HttpExchange;
import com.sun.xml.internal.ws.developer.JAXWSProperties;

public class Broker
{
	private Set<String> adressesIPClient = new HashSet<String>();
	
	public Broker()
	{
		Endpoint.publish ("http://localhost:9998/broker", new BrokerImplementation(this));
	}
	
	public boolean sAbonner (MessageContext mc)
	{
		HttpExchange exchange = (HttpExchange) mc.get(JAXWSProperties.HTTP_EXCHANGE);
		InetSocketAddress remoteAddress = exchange.getRemoteAddress();
		String remoteHost = remoteAddress.getHostName();
		adressesIPClient.add(remoteHost);
		
		return true;
		
	};
	
	public boolean seDesabonner (MessageContext mc)
	{
		HttpExchange exchange = (HttpExchange) mc.get(JAXWSProperties.HTTP_EXCHANGE);
		InetSocketAddress remoteAddress = exchange.getRemoteAddress();
		String remoteHost = remoteAddress.getHostName();
		
		return adressesIPClient.remove(remoteHost);
	};

	public boolean envoyerInformation (String info)
	{
		
		System.out.println(adressesIPClient.toString());
		
		if (adressesIPClient.isEmpty())
		{		
			System.out.println("En attente de clients");
			return true;
		}
		
		else
		{
			
			Iterator i = adressesIPClient.iterator(); // on crée un Iterator pour parcourir notre HashSet
			
			while(i.hasNext()) // tant qu'on a un suivant
			{
				URL url;
				
				try {
					url = new URL("http://"+ (String) i.next() + ":9999/client?wsdl");
					
					//1st argument service URI, refer to wsdl document above
					//2nd argument is service name, refer to wsdl document above
					QName qname = new QName("http://client/", "ClientImplementationService");
			    
					Service service = Service.create(url, qname);
			    
					ClientInterface client = service.getPort (ClientInterface.class);
					
					System.out.println(info);
					client.envoyerInformation(info);

				} catch (MalformedURLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					return false;
					
				}	

			}
			return true;
		}

	};
	
	/**
	 * @param args
	 */
	public static void main (String[] args)
	{
		// TODO Auto-generated method stub
		Broker broker = new Broker();
	}

}
