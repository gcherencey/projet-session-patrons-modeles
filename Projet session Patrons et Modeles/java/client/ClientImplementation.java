package client;

import interfaces.ClientInterface;

import javax.jws.WebService;

import commun.Information;



/**
 * 
 * Implémente l'interface du client.
 * 
 * @author Florian Massacret, Valentin Brémond, Gaylord Cherencey
 * 
 * @version 1.0
 *
 */
@WebService (endpointInterface = "interfaces.ClientInterface")
public class ClientImplementation implements ClientInterface
{
	/**
	 * Stocke l'instance du Client.
	 */
	private Client client;
	
	
	
	// CONSTRUCTEURS
	
	
	
	/**
	 * Constructeur par valeurs.
	 * 
	 * @param client Le Client qui instancie ce ClientImplementation.
	 */
	public ClientImplementation (Client client)
	{
		this.client = client;
	}
	
	
	
	// MÉTHODES
	
	
	
	@Override
	public boolean envoyerInformation (Information info)
	{
		// Met à jour l'information du client
		client.setInfo (info);
		
		return true;
	}
	
}
