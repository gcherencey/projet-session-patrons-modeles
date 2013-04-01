package client;

import java.net.URL;

import javax.xml.namespace.QName;
import javax.xml.ws.Service;

import fournisseur.HelloWorldInterface;

public class HelloWorldClient
{
	public static void main (String[] args) throws Exception
	{	 
		URL url = new URL("http://localhost:9999/ws/hello?wsdl");
		
		//1st argument service URI, refer to wsdl document above
		//2nd argument is service name, refer to wsdl document above
	    QName qname = new QName("http://fournisseur/", "HelloWorldImplementationService");
	    
	    Service service = Service.create(url, qname);
	    
	    HelloWorldInterface hello = service.getPort (HelloWorldInterface.class);
	    
	    System.out.println (hello.getHelloWorld ("Val"));
	 
	}
}
