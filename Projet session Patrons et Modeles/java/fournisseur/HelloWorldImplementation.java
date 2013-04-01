package fournisseur;

import java.net.InetSocketAddress;

import javax.annotation.Resource;
import javax.jws.WebService;
import javax.xml.ws.WebServiceContext;
import javax.xml.ws.handler.MessageContext;

import com.sun.net.httpserver.HttpExchange;
import com.sun.xml.internal.ws.developer.JAXWSProperties;


@WebService (endpointInterface = "fournisseur.HelloWorldInterface")
public class HelloWorldImplementation implements HelloWorldInterface
{	 
	@Resource
	WebServiceContext ws;
	
		@Override
		public String getHelloWorld (String name)
		{
			
			// Permet de récupérer l'adresse du client
			MessageContext mc = ws.getMessageContext ();
			HttpExchange exchange = (HttpExchange) mc.get(JAXWSProperties.HTTP_EXCHANGE);
			InetSocketAddress remoteAddress = exchange.getRemoteAddress();
			String remoteHost = remoteAddress.getHostName();
			
			return "Salut " + name + " ! Ça feel today ? T'es connecté à l'adresse " + remoteHost;
		}	 
}
