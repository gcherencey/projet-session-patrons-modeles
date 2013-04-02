package broker;

import javax.jws.WebService;


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
	
	
	@Override
	public boolean sAbonner ()
	{
		broker.sAbonner();
		
		return true;
		
	};
		
	@Override
	public boolean seDesabonner ()
	{
		
		broker.seDesabonner();
		return true;
		
	};
	
	@Override
	public boolean envoyerInformation (String info)
	{
		broker.envoyerInformation(info);
		return true;
	};
}
