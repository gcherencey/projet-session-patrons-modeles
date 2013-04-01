package client;

import interfaces.BrokerInterface;

import java.net.MalformedURLException;
import java.net.URL;

import javax.swing.JOptionPane;
import javax.xml.namespace.QName;
import javax.xml.ws.Service;



/**
 * 
 * Le client qui va s'abonner au broker.
 * 
 * @author Valentin Brémond
 * 
 * @version 1.0
 *
 */
public class Client
{
	/**
	 * L'URL du broker.
	 */
	private String urlBroker;
	
	/**
	 * L'interface du broker.
	 */
	private BrokerInterface interfaceBroker;
	
	
	
	// CONSTRUCTEURS
	
	
	
	/**
	 * Constructeur par défaut.
	 */
	public Client ()
	{
		// On demande l'URL du broker
		this.urlBroker = JOptionPane.showInputDialog(null, "Veuillez entrer l'adresse IP du broker", "Adresse du broker", JOptionPane.QUESTION_MESSAGE);
		
		// On tente de s'y connecter
		try
		{
			this.interfaceBroker = connexionBroker (urlBroker);
		}
		catch (Exception e)
		{
			// Si l'URL n'est pas bonne, on affiche une erreur
			JOptionPane.showMessageDialog (null, "Veuillez saisir une URL valide.", "URL non valide", JOptionPane.ERROR_MESSAGE);
			System.exit (0);
		}
		
		// On s'abonne au broker
		if (!interfaceBroker.sAbonner ())
		{
			// Si on ne peut pas s'abonner, on affiche une erreur
			JOptionPane.showMessageDialog (null, "Impossible de s'abonner au contenu.", "Erreur de contenu", JOptionPane.ERROR_MESSAGE);
			System.exit (0);
		}
		
		// Puis on lance la fenêtre graphique
		new ClientGraphique ();
	}
	
	
	
	// MÉTHODES
	
	
	
	/**
	 * Permet de se connecter à un broker.
	 * 
	 * @param url L'URL du broker sur lequel se connecter.
	 * 
	 * @return L'interface des services récupérée sur le broker.
	 * 
	 * @throws MalformedURLException
	 */
	private BrokerInterface connexionBroker (String url) throws MalformedURLException
	{
		// On créé l'URL
		URL urlBroker = new URL (url);

		// On créé le Qname
	    QName qname = new QName ("http://interfaces/", "BrokerImplementationService");
	    
	    // On créé le service
	    Service service = Service.create (urlBroker, qname);
	    
	    // Puis on renvoie l'interface
	    return service.getPort (BrokerInterface.class);
	}
	
	
	
	public static void main (String[] args)
	{
		new Client ();
	}

}