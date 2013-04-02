package broker;

import java.net.InetSocketAddress;
import java.util.ArrayList;

import javax.annotation.Resource;
import javax.jws.WebService;
import javax.xml.ws.WebServiceContext;
import javax.xml.ws.handler.MessageContext;

import com.sun.net.httpserver.HttpExchange;
import com.sun.xml.internal.ws.developer.JAXWSProperties;


@WebService (endpointInterface = "interfaces.BrokerInterface")
public class BrokerImplementation implements interfaces.BrokerInterface
{
	
	ArrayList<String> adressesIPClient = new ArrayList<String>();
	
	@Resource
	WebServiceContext ws;
	
		@Override
		public boolean sAbonner ()
		{
			MessageContext mc = ws.getMessageContext ();
			HttpExchange exchange = (HttpExchange) mc.get(JAXWSProperties.HTTP_EXCHANGE);
			InetSocketAddress remoteAddress = exchange.getRemoteAddress();
			String remoteHost = remoteAddress.getHostName();
			adressesIPClient.add(remoteHost);
			
			return true;
			
		};
		
		@Override
		public boolean seDesabonner ()
		{
			
			MessageContext mc = ws.getMessageContext ();
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
			return false;
		};

}
