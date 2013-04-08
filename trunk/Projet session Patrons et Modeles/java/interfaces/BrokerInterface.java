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
 * @author Valentin Brémond
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
