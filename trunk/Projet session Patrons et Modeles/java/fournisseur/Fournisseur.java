package fournisseur;

import interfaces.BrokerInterface;

import java.net.MalformedURLException;
import java.net.URL;

import javax.xml.namespace.QName;
import javax.xml.ws.Service;

public class Fournisseur
{

	/**
	 * @param args
	 * @throws MalformedURLException 
	 */
	public static void main (String[] args) throws MalformedURLException
	{
		// TODO Auto-generated method stub
		URL url = new URL("http://localhost:9998/broker?wsdl");
		
		//1st argument service URI, refer to wsdl document above
		//2nd argument is service name, refer to wsdl document above
	    QName qname = new QName("http://broker/", "BrokerImplementationService");
	    
	    Service service = Service.create(url, qname);
	    
	    BrokerInterface broker = service.getPort (BrokerInterface.class);
	    
	    for(int i=0; i<10; i++){
	    	broker.envoyerInformation("Information : " + i );
	    }
	   
	}

}
