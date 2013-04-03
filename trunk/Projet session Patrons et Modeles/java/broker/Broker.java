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


/**
 * 
 * Broker qui va gerer la transmission des info entre fournisseur et client.
 * 
 * @author Cherencey Gaylord
 * 
 * @version 1.0
 *
 */

public class Broker
{
	/**
	 * HashSet contenant les adresses IP des differents client
	 */
	private Set<String> adressesIPClient;
	
	//CONSTRUCTEUR
	
	/**
	 * Constructeur par defaut.
	 */
	public Broker()
	{
		
		this.adressesIPClient = new HashSet<String>();
		// On cree notre WSDL et notre interface
		Endpoint.publish ("http://localhost:9998/broker", new BrokerImplementation(this));
	}
	
	//METHODES
	
	/**
	 * Permet d'abonner l'utilisateur en le rajoutant a la liste
	 * @param messageContext precise dans quel "contexte" nous sommes (canal de communication)
	 * @return true si l'ajout a ete realise avec succes
	 */
	public boolean sAbonner (MessageContext messageContext)
	{
		//On recupere l'adresse IP du client qui veut s'abonner
		HttpExchange exchange = (HttpExchange) messageContext.get(JAXWSProperties.HTTP_EXCHANGE);
		InetSocketAddress remoteAddress = exchange.getRemoteAddress();
		String remoteHost = remoteAddress.getHostName();
		
		return this.adressesIPClient.add(remoteHost);
		
	};
	
	/**
	 * Permet de desabonner l'utilisateur en l'enlevant a la liste
	 * @param messageContext precise dans quel "contexte" nous sommes (canal de communication)
	 * @return true si la suppression a ete realise avec succes
	 */
	public boolean seDesabonner (MessageContext mc)
	{
		//On recupere l'adresse IP du client qui veut se desabonner
		HttpExchange exchange = (HttpExchange) mc.get(JAXWSProperties.HTTP_EXCHANGE);
		InetSocketAddress remoteAddress = exchange.getRemoteAddress();
		String remoteHost = remoteAddress.getHostName();
		
		return this.adressesIPClient.remove(remoteHost);
	};

	/**
	 * Permet de transferer le message du fournisseur vers les clients abonnes 
	 * @param info est la nouvelle information a transmettre
	 * @return true si la suppression a ete realise avec succes
	 */
	public boolean envoyerInformation (String info)
	{
		
		System.out.println(this.adressesIPClient.toString());
		
		//Si aucun client ne s'est abonne
		if (this.adressesIPClient.isEmpty())
		{		
			System.out.println("En attente de clients");
			return true;
		}
		
		//sinon
		else
		{
			
			// on cree un Iterator pour parcourir notre HashSet
			Iterator i = this.adressesIPClient.iterator(); 
			
			// Pour chaque client present dans la liste on envoie l'information
			while(i.hasNext()) 
			{
				
				//adresse du client a qui envoyer l'information
				URL url = null;
				
				try {
					
					//on cree l'url pour le client courant
					url = new URL("http://"+ (String) i.next() + ":9999/client?wsdl");
					
					// On cree le qname
					QName qname = new QName("http://client/", "ClientImplementationService");
					
					// On cree le service
					Service service = Service.create(url, qname);
			    
					//On cree une instance de l'interface client
					ClientInterface client = service.getPort (ClientInterface.class);
					
					System.out.println(info);
					
					//On fait appel de la methode distante
					client.envoyerInformation(info);

				} catch (MalformedURLException e) {
					
					return false;
				}	

			}
			
			return true;
		}

	};
	
	/**
	 * Permet d'instancier un broker
	 * @param args
	 */
	public static void main (String[] args)
	{
		// TODO Auto-generated method stub
		Broker broker = new Broker();
	}

}
