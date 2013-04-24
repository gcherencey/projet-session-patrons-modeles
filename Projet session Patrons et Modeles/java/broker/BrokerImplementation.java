package broker;

import interfaces.BrokerInterface;

import java.util.Arrays;
import java.util.HashSet;

import javax.annotation.Resource;
import javax.jws.WebService;
import javax.xml.ws.WebServiceContext;
import javax.xml.ws.handler.MessageContext;

import commun.Information;



/** 
 * 
 * Implémente l'interface du broker.
 * 
 * @author Gaylord Cherencey, Valentin Brémond, Florian Massacret
 * 
 * @version 1.0
 *
 */
@WebService (endpointInterface = "interfaces.BrokerInterface")
public class BrokerImplementation implements BrokerInterface
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
	
	
	
	// MÉTHODES
	
	
	
	@Override
	public boolean sAbonner (String[] listeTypesInformations)
	{
		MessageContext mc = ws.getMessageContext ();
		
		return broker.sAbonner (mc, new HashSet<String> (Arrays.asList (listeTypesInformations)));
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
		return broker.ajouterTypeInformation (type);
	}
	
	
	
	@Override
	public String[] recupererTypesInformations ()
	{
		Object[] liste = broker.recupererTypesInformations ().toArray ();
		
		return Arrays.copyOf(liste, liste.length, String[].class);
	}
	
}
