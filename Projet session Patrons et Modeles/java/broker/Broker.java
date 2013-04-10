package broker;

import interfaces.ClientInterface;

import java.net.InetSocketAddress;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.HashSet;

import javax.xml.namespace.QName;
import javax.xml.ws.Endpoint;
import javax.xml.ws.Service;
import javax.xml.ws.handler.MessageContext;

import com.sun.net.httpserver.HttpExchange;
import com.sun.xml.internal.ws.developer.JAXWSProperties;
import commun.Information;


/**
 * 
 * Broker qui va gerer la transmission des info entre fournisseur et client.
 * 
 * @author CHERENCEY Gaylord, BREMOND Valentin, MASSACRET Florian
 * 
 * @version 1.0
 *
 */

/**
 * @author Utilisateur
 *
 */
/**
 * @author Utilisateur
 *
 */
public class Broker
{
	/**
	 * HashSet contenant les adresses IP des differents client
	 */
	private HashMap<String, HashSet<String>> adressesIPClient;
	private HashSet<String> typesInformation;
	
	
	
	// CONSTRUCTEUR
	
	
	
	/**
	 * Constructeur par défaut.
	 */
	public Broker()
	{
		this.adressesIPClient = new HashMap<String, HashSet<String>>();
		this.typesInformation = new HashSet<String>();
		// On cree notre WSDL et notre interface
		Endpoint.publish ("http://localhost:9998/broker", new BrokerImplementation(this));
	}
	
	
	// METHODES
	
	
	
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
		
		if (!this.adressesIPClient.containsKey(remoteAddress))
		{
			this.adressesIPClient.put(remoteHost, new HashSet<String>());
			return true;			
		}
		
		else
		{
			return false;
		}
		
	};
	
	
	
	/**
	 * @param mc precise dans quel "contexte" nous sommes (canal de communication) afin de mapper les services avec un client particulier
	 * @param listeServiceAsouscrire contient tout les services auxquels le client veux souscrire 
	 * @return true si la souscription a ete realisee avec succes
	 */
	public boolean souscrireAdesServices(MessageContext mc, String[] listeServiceAsouscrire) 
	{
		//On recupere l'adresse IP du client qui veut se desabonner
		HttpExchange exchange = (HttpExchange) mc.get(JAXWSProperties.HTTP_EXCHANGE);
		InetSocketAddress remoteAddress = exchange.getRemoteAddress();
		String remoteHost = remoteAddress.getHostName();
		
		HashSet<String> listeAjoutServiceAuClient = new HashSet<String>();
		
		if (this.adressesIPClient.containsKey(remoteHost)){
			
			for (String service : listeServiceAsouscrire)
			{
				listeAjoutServiceAuClient.add(service);
			}
			
			this.adressesIPClient.put(remoteHost, listeAjoutServiceAuClient);
			return true;
		}
		else
		{
			return false;
		}
		
	}
	
	
	
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
		
		if(this.adressesIPClient.containsKey(remoteHost))
		{
			this.adressesIPClient.remove(remoteHost);
			return true;
		}
		
		else
		{
			return false;
		}
	};

	
	
	/**
	 * Permet d'ajouter un type d'information au broker (ex: cinema, sante ...)
	 * @param type a ajouter dans le broker
	 * @return true si l'ajout a ete realise, false sinon
	 */
	public boolean ajouterTypeInformation(String type) {
		return this.typesInformation.add(type);
	}
	
	
	
	/**
	 * Permet de transferer le message du fournisseur vers les clients abonnes 
	 * @param info est la nouvelle information a transmettre
	 * @return true si la suppression a ete realise avec succes
	 */
	public boolean envoyerInformation (Information info)
	{
		
		//adresse du client a qui envoyer l'information
		URL url = null;
		
		//Si aucun client ne s'est abonne
		if (this.adressesIPClient.isEmpty())
		{
			return true;
		}
		
		//sinon
		else
		{		
			
			// Pour chaque client present dans la liste on envoie l'information selon ses souscriptions
			for (String adresseClient : this.adressesIPClient.keySet()) 
			{
				System.out.println(adresseClient);
				try
				{
					
					// On cree l'url pour le client courant
					url = new URL("http://"+ adresseClient + ":9999/client?wsdl");
					
					// On cree le qname
					QName qname = new QName("http://client/", "ClientImplementationService");
					
					// On cree le service
					Service service = Service.create(url, qname);
			    
					// On cree une instance de l'interface client
					ClientInterface client = service.getPort (ClientInterface.class);
					
					//Si le client a souscrit a ce service
					if (this.adressesIPClient.get(adresseClient).contains(info.getTypeToString()) && !this.adressesIPClient.get(adresseClient).isEmpty())
					{				
						// On fait appel de la methode distante
						client.envoyerInformation(info);
					}
					
				}
				catch (MalformedURLException e)
				{
					
					return false;
				}	
			}
			
			return true;
		}
	};
	
	
	
	/**
	 * Permet de savoir s'il y a au moins un client de connecté.
	 * @return true si au moins un client est connecté, false si aucun client n'est connecté
	 */
	public boolean auMoinsUnClient ()
	{
		return !this.adressesIPClient.isEmpty ();
	}
	
	
	
	/**
	 * Permet d'envoyer la liste des types d'information aux clients connectes.
	 * @return liste des types d'information
	 */
	
	public String[] recupererTypesInformation() {
		
		//On retourne la liste dans une forme serialisable
		return this.typesInformation.toArray(new String[this.typesInformation.size()]);
	}
	
	
		
	/**
	 * Permet d'instancier un broker
	 * @param args
	 */
	public static void main (String[] args)
	{
		new Broker();
	}

}
