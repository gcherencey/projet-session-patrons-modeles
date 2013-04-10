package client;

import interfaces.BrokerInterface;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashSet;
import java.util.Observable;

import javax.swing.JOptionPane;
import javax.xml.namespace.QName;
import javax.xml.ws.Endpoint;
import javax.xml.ws.Service;

import commun.Information;


/**
 * 
 * Le client qui va s'abonner au broker.
 * 
 * @author CHERENCEY Gaylord, BREMOND Valentin, MASSACRET Florian
 * 
 * @version 1.0
 *
 */
public class Client extends Observable
{
	/**
	 * L'URL du broker.
	 */
	private String urlBroker;
	
	/**
	 * L'interface du broker.
	 */
	private BrokerInterface interfaceBroker;
	
	/**
	 * L'information envoyée par le broker.
	 */
	private Information info;	
	
	/**
	 * Liste des types d'information present sur le broker.
	 */
	private String[] listeTypesInformation;
	
	/**
	 * Liste des services auxquels le client veut souscrire.
	 */
	private HashSet<String> listeServiceAsouscrire = new HashSet<String>();
	
	
	// CONSTRUCTEURS
	
	
	/**
	 * Constructeur par défaut.
	 */
	public Client ()
	{
		// On créé notre WSDL et notre interface
		Endpoint.publish ("http://localhost:9999/client", new ClientImplementation (this));
		
		// On demande l'URL du broker
		this.urlBroker = JOptionPane.showInputDialog(null, "Veuillez entrer l'adresse IP du broker", "Adresse du broker", JOptionPane.QUESTION_MESSAGE);
		
		// On tente de s'y connecter
		try
		{
			this.setInterfaceBroker(connexionBroker (urlBroker));
		}
		catch (MalformedURLException e)
		{
			// Si l'URL n'est pas bonne, on affiche une erreur
			JOptionPane.showMessageDialog (null, "Veuillez saisir une URL valide.", "URL non valide", JOptionPane.ERROR_MESSAGE);
			System.exit (0);
		}
		
		// On s'abonne au broker
		if (!this.getInterfaceBroker().sAbonner ())
		{
			// Si on ne peut pas s'abonner, on affiche une erreur
			JOptionPane.showMessageDialog (null, "Impossible de s'abonner au contenu.", "Erreur de contenu", JOptionPane.ERROR_MESSAGE);
			System.exit (0);
		}
		
		//On recupere les types d'informations disponibles
		if(this.getInterfaceBroker().recupererTypesInformation().length == 0 ){
			// Si on ne peut pas s'abonner, on affiche une erreur
			JOptionPane.showMessageDialog (null, "Pas de service disponible pour le moment.", "Erreur de fournisseur", JOptionPane.ERROR_MESSAGE);
			System.exit (0);
		}
		
		//On recupere la liste des types de services proposes par les fournisseurs
		this.setListeTypesInformation(this.getInterfaceBroker().recupererTypesInformation());	
		
		//On demarre la fenetre permettant les choix de services
		new ClientGraphiqueChoixService(this);
		
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
		URL broker = new URL (url);

		// On créé le Qname
	    QName qname = new QName ("http://broker/", "BrokerImplementationService");
	    
	    // On créé le service
	    Service service = Service.create (broker, qname);
	    
	    // Puis on renvoie l'interface
	    return service.getPort (BrokerInterface.class);
	}
	
	
	
	/**
	 * Permet de fermer l'application proprement.
	 * 
	 * @param clientGraphique Le ClientGraphique qui observe ce client.
	 */
	protected void fermerConnexion (ClientGraphique clientGraphique)
	{
		// On enlève l'observateur
		deleteObserver (clientGraphique);
		
		// On se désabonne du broker
		getInterfaceBroker().seDesabonner ();
		
		// Puis on quitte
		System.exit (0);
	}
	
	
	//GETTER & SETTER
	
	
	/**
	 * Permer de mettre à jour l'information à transmettre au graphique.
	 * 
	 * @param info L'information à afficher par la partie graphique.
	 */
	public void setInfo (Information info)
	{
		this.info = info;
		
		setChanged ();
		notifyObservers (this.info);
	}
	
	
	
	/**
	 * Permet de récupérer la dernière information.
	 * 
	 * @return La dernière information envoyée par le broker.
	 */
	public Information getInfo ()
	{
		return this.info;
	}
	
	
	
	/**
	 * Permet de recuperer l'instance de brokerInterface
	 * @return the interfaceBroker
	 */
	public BrokerInterface getInterfaceBroker() {
		return interfaceBroker;
	}


	
	/**
	 * Permet de changer l'instance de brokerInterface
	 * @param interfaceBroker the interfaceBroker to set
	 */
	public void setInterfaceBroker(BrokerInterface interfaceBroker) {
		this.interfaceBroker = interfaceBroker;
	}
	
	
	
	/**
	 * Permet de recuperer la liste des services auxquels le client veut souscrire
	 * @return the listeServiceAsouscrire
	 */
	public HashSet<String> getListeServiceAsouscrire() {
		return this.listeServiceAsouscrire;
	}
	
	

	/**
	 * Permet de recuperer la liste des types de service disponible
	 * @return the listeServiceAsouscrire
	 */
	public String [] getListeTypesInformation() {
		return this.listeTypesInformation;
	}	
	
	
	
	/**
	 * Permet de changer la liste des types de service disponible
	 * @param typesInformation the typesInformation to set
	 */
	public void setListeTypesInformation(String[] typesInformation) {
		this.listeTypesInformation = typesInformation;
	}
	
	
	
	public static void main (String[] args)
	{
		new Client ();
	}

}
