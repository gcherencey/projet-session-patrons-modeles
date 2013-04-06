package fournisseur;

import interfaces.BrokerInterface;

import java.net.MalformedURLException;
import java.net.URL;

import javax.xml.namespace.QName;
import javax.xml.ws.Service;


/**
 * 
 * Le fournisseur qui va envoyer les informations.
 * 
 * @author Cherencey Gaylord
 * 
 * @version 1.0
 *
 */
public class Fournisseur
{
	/**
	 * Permet de demarrer le fournisseur
	 * @param args
	 * @throws MalformedURLException 
	 * @throws InterruptedException 
	 */
	public static void main (String[] args) throws MalformedURLException, InterruptedException
	{

			// On cree l'URL
			URL url = new URL("http://localhost:9998/broker?wsdl");
			
			// On cree le qname
		    QName qname = new QName("http://broker/", "BrokerImplementationService");
			
		    // On cree le service
		    Service service = Service.create(url, qname);
		    
		    //On cree une instance de l'interface broker
		    BrokerInterface broker = service.getPort (BrokerInterface.class); 
		    
		    while(true)
		    {
		    	//Toutes les secondes on envoie la nouvelle information au broker
		    	for(int i=0; i<10; i++)
		    	{
		    		broker.envoyerInformation ("Information : " + i );
		    		Thread.sleep(1000);
		    	}
		    }
		}
}