package fournisseur;

import interfaces.BrokerInterface;

import java.net.MalformedURLException;
import java.net.URL;

import javax.xml.namespace.QName;
import javax.xml.ws.Service;

import commun.Information;


/**
 * 
 * Le fournisseur qui va envoyer les informations.
 * 
 * @author Cherencey Gaylord, Florian Massacret, Valentin Brémond
 * 
 * @version 2.0
 *
 */
public class Fournisseur
{	
	/**
	 * Permet de démarrer le fournisseur.
	 * 
	 * @param args
	 * @throws MalformedURLException 
	 * @throws InterruptedException 
	 */
	public static void main (String[] args) throws MalformedURLException, InterruptedException
	{
		// L'information qui sera transmise
		Information info;
	
		// On crée l'URL
		URL url = new URL ("http://localhost:9998/broker?wsdl");
		
		// On crée le qname
	    QName qname = new QName ("http://broker/", "BrokerImplementationService");
		
	    // On crée le service
	    Service service = Service.create (url, qname);
	    
	    //On crée une instance de l'interface broker
	    BrokerInterface broker = service.getPort (BrokerInterface.class); 
	    
	    // On envoie des informations en boucle
	    while (true)
	    {
	    	// Toutes les secondes on envoie la nouvelle information au broker
	    	for(int i=0; i<10; i++)
	    	{
	    		info = new Information ((int) (Math.random () * 10), "Information : " + i);
	    		
	    		broker.envoyerInformation (info);
	    		
	    		Thread.sleep(1000);
	    	}
	    }
	}
}