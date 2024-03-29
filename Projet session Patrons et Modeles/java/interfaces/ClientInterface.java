package interfaces;

import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.jws.soap.SOAPBinding.Style;

import commun.Information;



/**
 * 
 * Interface permettant au broker de mettre à jour un client.
 * 
 * @author Florian Massacret, Valentin Brémond, Gaylord Cherencey
 * 
 * @version 1.0
 *
 */
@WebService
@SOAPBinding (style = Style.RPC)
public interface ClientInterface
{
	/**
	 * Permet à un broker d'envoyer des informations à un client.
	 * 
	 * @param info L'information à envoyer.
	 * 
	 * @return true si le client a bien reçu l'information, false sinon.
	 */
	@WebMethod boolean envoyerInformation (Information info);
}