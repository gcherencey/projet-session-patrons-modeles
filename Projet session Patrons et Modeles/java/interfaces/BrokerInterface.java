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
 * @author CHERENCEY Gaylord, BREMOND Valentin, MASSACRET Florian
 * 
 * @version 1.0
 *
 */
@WebService
@SOAPBinding (style = Style.RPC)
public interface BrokerInterface
{
	/**
	 * Permet à un client de s'abonner au broker.
	 * 
	 * @return true si le client est abonné, false sinon.
	 */
	@WebMethod boolean sAbonner ();
	
	
	
	/**
	 * Permet a un client de souscrire a des services.
	 * 
	 * @return true si les souscriptions se sont bien effectuees, false sinon.
	 */
	@WebMethod boolean souscrireAdesServices (String[] listeServiceAsouscrire);
	
	
	
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
	
	
	
	/**
	 * Permet a un fournisseur de fournir le type de l'information au broker.
	 * 
	 * @param type Type de l'information a� transmettre.
	 * 
	 * @return true si le broker a bien reçu le type, false sinon.
	 */
	@WebMethod boolean ajouterTypeInformation (String type);
	
	
	
	/**
	 * Permet à un broker d'envoyer les types d'informations à un client.
	 * 
	 *@return la liste des differents types d'information.
	 */
	@WebMethod String[] recupererTypesInformation ();
}
