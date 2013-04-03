package broker;

import javax.annotation.Resource;
import javax.jws.WebService;
import javax.xml.ws.WebServiceContext;
import javax.xml.ws.handler.MessageContext;

/* 
* Implémente l'interface du broker.
* 
* @author Cherencey Gaylord
* 
* @version 1.0
*
*/

@WebService (endpointInterface = "interfaces.BrokerInterface")
public class BrokerImplementation implements interfaces.BrokerInterface
{
	
	/**
	 * Stocke l'instance du Broker.
	 */
	private Broker broker;
	/**
	 * Stocke le resultat des methodes appellees.
	 */
	private boolean reponse;
	
	//CONSTRUCTEURS
	
	/**
	 * Constructeur par valeurs.
	 * 
	 * @param broker Le Broker qui instancie ce BrokerImplementation.
	 */
	public BrokerImplementation(Broker broker) {
		
		this.broker = broker;

	}
	
	@Resource
	WebServiceContext ws;
	
	//METHODES
	
	@Override
	public boolean sAbonner ()
	{
		MessageContext mc = ws.getMessageContext ();
		reponse = broker.sAbonner(mc);
		
		if (reponse == true){
			System.out.println("Client rajoute avec succes");
			return true;
		}
		else{
			System.err.println("Erreur la de l'ajout du client");
			return false;
		}
		
	};
		
	@Override
	public boolean seDesabonner ()
	{
		MessageContext mc = ws.getMessageContext ();
		reponse = broker.seDesabonner(mc);
		
		if (reponse == true){
			System.out.println("Client desabonne avec succes");
			return true;
		}
		else{
			System.err.println("Erreur la de la suppression du client");
			return false;
		}
		
	};
	
	@Override
	public boolean envoyerInformation (String info)
	{	    
	    return broker.envoyerInformation(info);
	    
	};
}
