package client;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;



/**
 * 
 * La fenêtre qui va afficher les informations.
 * 
 * @author Valentin Brémond
 * 
 * @version 1.0
 *
 */
public class ClientGraphique extends JFrame implements ActionListener
{
	private JButton boutonFermer;
	private JPanel panelPrincipal;
	
	
	
	// CONSTRUCTEURS
	
	
	
	/**
	 * Constructeur par défaut.
	 */
	public ClientGraphique ()
	{
		super ("Informations");
		
		panelPrincipal = new JPanel ();
		
		this.add (panelPrincipal);
		
		boutonFermer = new JButton ("Fermer");
		boutonFermer.addActionListener (this);
		panelPrincipal.add (boutonFermer);
		
		this.setSize (500, 500);
		this.setLocationRelativeTo (null);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible (true);
	}
	
	
	
	// MÉTHODES
	
	
	
	@Override
	public void actionPerformed (ActionEvent arg0)
	{
		Object evenement = arg0.getSource ();
		
		if (evenement == boutonFermer)
		{
			this.dispose ();
			System.exit (0);
		}
		
	}
	
	
	
	public static void main (String[] args)
	{
		new ClientGraphique ();
	}
}
