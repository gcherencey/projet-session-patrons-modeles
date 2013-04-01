package client;

import interfaces.ClientInterface;

import javax.jws.WebService;



/**
 * 
 * Implémente l'interface du client.
 * 
 * @author Valentin Brémond
 * 
 * @version 1.0
 *
 */
@WebService (endpointInterface = "interfaces.ClientInterface")
public class ClientImplementation implements ClientInterface
{
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
	public boolean envoyerInformation (String info)
	{
		// Met à jour l'information du client
		client.setInfo (info);
		
		return true;
	}
}
