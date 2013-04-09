package broker;

import javax.annotation.Resource;
import javax.jws.WebService;
import javax.xml.ws.WebServiceContext;
import javax.xml.ws.handler.MessageContext;

import commun.Information;

/* 
* Implémente l'interface du broker.
* 
 * @author CHERENCEY Gaylord, BREMOND Valentin, MASSACRET Florian
* 
* @version 1.0
*
*/

@WebService (endpointInterface = "interfaces.BrokerInterface")
public class BrokerImplementation implements interfaces.BrokerInterface
{
	@Resource
	WebServiceContext ws;
	
	/**
	 * Stocke l'instance du Broker.
	 */
	private Broker broker;
	
	
	
	//CONSTRUCTEURS
	
	
	
	/**
	 * Constructeur par valeurs.
	 * 
	 * @param broker Le Broker qui instancie ce BrokerImplementation.
	 */
	public BrokerImplementation(Broker broker)
	{
		this.broker = broker;
	}
	
	
	
	//METHODES
	
	
	
	@Override
	public boolean sAbonner ()
	{
		MessageContext mc = ws.getMessageContext ();
		return broker.sAbonner (mc);
	};
	
	
	
	@Override
	public boolean seDesabonner ()
	{
		MessageContext mc = ws.getMessageContext ();
		return broker.seDesabonner (mc);	
	};
	
	
	
	@Override
	public boolean envoyerInformation (Information info)
	{	    
	    return broker.envoyerInformation (info);
	}
	
	
	
	@Override
	public boolean ajouterTypeInformation (String type)
	{	
		return broker.ajouterTypeInformation(type);
	}
	
	
	
	@Override
	public String[] recupererTypesInformation (){
		return broker.recupererTypesInformation();
	}
	
}
