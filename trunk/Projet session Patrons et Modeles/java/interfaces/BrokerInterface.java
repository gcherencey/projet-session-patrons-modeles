package interfaces;

import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.jws.soap.SOAPBinding.Style;

import commun.Information;



/**
 * 
 * Interface permettant à un client de se connecter ou à un fournisseur de fournir du contenu.
 * 
 * @author Florian Massacret, Gaylord Cherencey, Valentin Brémond
 * 
 * @version 1.0
 *
 */
@WebService
@SOAPBinding (style = Style.RPC)
public interface BrokerInterface
{
	/**
	 * Permet à un fournisseur de dire au broker le type d'information qu'il peut fournir.
	 * 
	 * @param type Le type de l'information à transmettre.
	 * 
	 * @return true si le broker a bien reçu le type, false sinon.
	 */
	@WebMethod boolean ajouterTypeInformation (String type);
	
	
	
	/**
	 * Permet à un client de récupérer les types d'informations disponibles sur le broker.
	 * 
	 * @return La liste des différents types d'informations.
	 */
	@WebMethod String[] recupererTypesInformations ();
	

	
	/**
	 * Permet à un client de s'abonner au broker.
	 * 
	 * @param listeTypesInformations Une liste des types d'information auxquels le client veut souscrire.
	 * 
	 * @return true si le client est abonné, false sinon.
	 */
	@WebMethod boolean sAbonner (String[] listeTypesInformations);
	
	
	
	/**
	 * Permet à un client de se déabonner du broker.
	 * 
	 * @return true si le client est désabonné, false sinon.
	 */
	@WebMethod boolean seDesabonner ();
	
	
	
	/**
	 * Permet à un fournisseur de fournir de l'information au broker.
	 * 
	 * @param info L'information à transmettre.
	 * 
	 * @return true si le broker a bien reçu l'information, false sinon.
	 */
	@WebMethod boolean envoyerInformation (Information info);
}
