package broker;

import interfaces.ClientInterface;

import java.net.URL;

import javax.annotation.Resource;
import javax.jws.WebService;
import javax.xml.namespace.QName;
import javax.xml.ws.Service;
import javax.xml.ws.WebServiceContext;
import javax.xml.ws.handler.MessageContext;


@WebService (endpointInterface = "interfaces.BrokerInterface")
public class BrokerImplementation implements interfaces.BrokerInterface
{
	
	/**
	 * Stocke l'instance du Broker.
	 */
	private Broker broker;
	
	/**
	 * Constructeur par valeurs.
	 * 
	 * @param client Le Client qui instancie ce ClientImplementation.
	 */
	public BrokerImplementation(Broker broker) {
		
		this.broker = broker;

	}
	
	@Resource
	WebServiceContext ws;
	@Override
	public boolean sAbonner ()
	{
		MessageContext mc = ws.getMessageContext ();
		broker.sAbonner(mc);
		
		return true;
		
	};
		
	@Override
	public boolean seDesabonner ()
	{
		MessageContext mc = ws.getMessageContext ();
		broker.seDesabonner(mc);
		return true;
		
	};
	
	@Override
	public boolean envoyerInformation (String info)
	{	    
	    broker.envoyerInformation(info);
		return true;
	};
}
