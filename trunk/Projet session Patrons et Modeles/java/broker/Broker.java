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

public class Broker
{
	/**
	 * HashSet contenant les adresses IP des differents client.
	 */
	private HashMap<String, HashSet<String>> adressesIPClient;
	
	/**
	 * Permet de stocker les types d'informations auxquels un client souscrit.
	 */
	private HashSet<String> typesInformation;
	
	/**
	 * Permet un appel distant des methode client
	 */
	private ClientInterface client;
	
	
	
	// CONSTRUCTEUR
	
	
	
	/**
	 * Constructeur par défaut.
	 */
	public Broker()
	{
		this.adressesIPClient = new HashMap<String, HashSet<String>> ();
		this.typesInformation = new HashSet<String> ();
		
		// On cree notre WSDL et notre interface
		Endpoint.publish ("http://localhost:9998/broker", new BrokerImplementation (this));
	}
	
	
	// METHODES
	
	
	
	/**
	 * Permet d'abonner un utilisateur.
	 * 
	 * @param messageContext Précise dans quel "contexte" nous sommes (canal de communication).
	 * @param listeTypesInformations Le type d'informations auxquels le client veut souscrire.
	 * 
	 * @return true si l'ajout a été réalisé avec succes, false sinon.
	 */
	public boolean sAbonner (MessageContext messageContext, HashSet<String> listeTypesInformations)
	{
		//On recupere l'adresse IP du client qui veut s'abonner
		HttpExchange exchange = (HttpExchange) messageContext.get (JAXWSProperties.HTTP_EXCHANGE);
		InetSocketAddress remoteAddress = exchange.getRemoteAddress ();
		String remoteHost = remoteAddress.getHostName ();
		
		// Si l'utilisateur n'existe pas déjà dans la liste, on le rajoute
		if (!this.adressesIPClient.containsKey (remoteAddress))
		{
			this.adressesIPClient.put (remoteHost, listeTypesInformations);
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
		HttpExchange exchange = (HttpExchange) mc.get (JAXWSProperties.HTTP_EXCHANGE);
		InetSocketAddress remoteAddress = exchange.getRemoteAddress ();
		String remoteHost = remoteAddress.getHostName ();
		
		if(this.adressesIPClient.containsKey (remoteHost))
		{
			this.adressesIPClient.remove (remoteHost);
			return true;
		}
		else
		{
			return false;
		}
	}

	
	
	/**
	 * Permet d'ajouter un type d'information au broker (ex: cinema, sante ...).
	 * 
	 * @param type Le type à ajouter dans le broker.
	 * 
	 * @return true si l'ajout a été réalisé, false sinon.
	 */
	public boolean ajouterTypeInformation (String type)
	{
		return this.typesInformation.add (type);
	}
	
	
	
	/**
	 * Permet de transférer le message du fournisseur vers les clients abonnés.
	 *  
	 * @param info L'information à transmettre.
	 * 
	 * @return true si la suppression a été réalisée avec succès, false sinon.
	 */
	public boolean envoyerInformation (Information info)
	{
		// Adresse du client à qui envoyer l'information
		URL url = null;
		
		// Si aucun client ne s'est abonné
		if (this.adressesIPClient.isEmpty())
		{
			return true;
		}
		
		else
		{
			// Pour chaque client présent dans la liste, on envoie l'information selon ses souscriptions
			for (String adresseClient : this.adressesIPClient.keySet ()) 
			{
				try
				{
					// On crée l'URL pour le client courant
					url = new URL ("http://"+ adresseClient + ":9999/client?wsdl");
					
					// On crée le qname
					QName qname = new QName ("http://client/", "ClientImplementationService");
					
					// On crée le service
					Service service = Service.create (url, qname);
			    
					//On affecte le service au client
					this.client = service.getPort (ClientInterface.class);
					
					if (!this.adressesIPClient.get(adresseClient).isEmpty())
					{
						// Si le client a souscrit à ce type d'information, on lui envoie
						if (this.adressesIPClient.get(adresseClient).contains (info.getTypeToString ()))
						{	
								// Si le client a souscrit à au moins un type d'information
								envoieAuClient(this.client, adresseClient, info);
						}
					}
					
				}
				catch (MalformedURLException e)
				{
					return false;
				}	
			}
			
			return true;
		}
	}
	
	
	/**
	 * Methode local qui permet d'envoyer une information a un client.
	 * 
	 * @param client instance de ClientInterface qui permet l'appel des methode distance du client
	 * @param adresseClient correspond a l'adresse IP du client distant
	 * @param info contient l'information (type, message)
	 */
	private void envoieAuClient(ClientInterface client, String adresseClient, Information info )
	{
		this.client.envoyerInformation(info);
	}
	
	
	/**
	 * Permet de savoir s'il y a au moins un client de connecté.
	 * 
	 * @return true si au moins un client est connecté, false si aucun client n'est connecté.
	 */
	public boolean auMoinsUnClient ()
	{
		return !this.adressesIPClient.isEmpty ();
	}
	
	
	
	/**
	 * Permet d'envoyer la liste des types d'information aux clients connectés.
	 * 
	 * @return La liste des types d'information.
	 */
	public HashSet<String> recupererTypesInformations ()
	{
		return this.typesInformation;
	}
	
	
		
	/**
	 * Permet d'instancier un broker.
	 */
	public static void main (String[] args)
	{
		new Broker();
	}
}
