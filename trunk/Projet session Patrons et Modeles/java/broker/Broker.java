package broker;

import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import javax.annotation.Resource;
import javax.xml.ws.Endpoint;
import javax.xml.ws.WebServiceContext;
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
		System.out.println(remoteHost);
		
		adressesIPClient.add(remoteHost);
		
		return true;
		
	};
	
	public boolean seDesabonner (MessageContext mc)
	{
		HttpExchange exchange = (HttpExchange) mc.get(JAXWSProperties.HTTP_EXCHANGE);
		InetSocketAddress remoteAddress = exchange.getRemoteAddress();
		String remoteHost = remoteAddress.getHostName();
		
		if(!adressesIPClient.contains(remoteAddress))
			return false;
		
		else
		{
			adressesIPClient.remove(remoteAddress);
			return true;
		}
		
	};

	public boolean envoyerInformation (String info)
	{
		System.out.println(info);
		return true;
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
