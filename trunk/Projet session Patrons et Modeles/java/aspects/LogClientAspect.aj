package aspects;

import interfaces.BrokerInterface;

import java.util.logging.Logger;

import javax.swing.JOptionPane;

import client.Client;

import commun.FormatLog;



/**
 * 
 * Aspect permettant de gérer les logs et les exceptions du client.
 * 
 * @author Valentin Brémond
 * 
 * @version 1.0
 *
 */
public aspect LogClientAspect
{
	// On créé le logger
	Logger log = FormatLog.createLogger ();
	
	//TODO : faire le reste
	
	 BrokerInterface around () : call (BrokerInterface Client.connexionBroker (..))
	 {
		 JOptionPane.showMessageDialog (null,  "Salut !");
		 
		 return proceed ();
	 }
}
