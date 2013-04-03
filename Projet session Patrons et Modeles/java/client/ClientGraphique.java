package client;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Observable;
import java.util.Observer;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;



/**
 * 
 * La fenêtre qui va afficher les informations.
 * 
 * @author Valentin Brémond
 * 
 * @version 1.0
 *
 */
public class ClientGraphique extends JFrame implements ActionListener,Observer
{
	private static final long serialVersionUID = 1L;
	
	/**
	 * Stocke l'instance du Client.
	 */
	private Client client;
	
	private JButton boutonFermer;
	private JPanel panelPrincipal, panelScrollPane;
	private JScrollPane scrollPane;
	
	
	
	// CONSTRUCTEURS
	
	
	
	/**
	 * Constructeur par valeurs.
	 * 
	 * @param client Le Client lié à cette instance.
	 */
	public ClientGraphique (Client client)
	{
		// On demande à être informé à chaque nouveau message reçu
		this.client = client;
		client.addObserver (this);
		
		// On construit la fenêtre
		this.setTitle ("Informations");
		
		panelPrincipal = new JPanel ();
		
		this.add (panelPrincipal);
		this.setContentPane (panelPrincipal);
		panelPrincipal.setLayout (new BorderLayout ());
		
		panelScrollPane = new JPanel ();
		panelScrollPane.setLayout (new BoxLayout (panelScrollPane, BoxLayout.PAGE_AXIS));
		
		scrollPane = new JScrollPane (panelScrollPane);
		scrollPane.setPreferredSize (new Dimension (400, 420));
		scrollPane.setHorizontalScrollBarPolicy (JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		scrollPane.setVerticalScrollBarPolicy (JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		
		panelPrincipal.add (scrollPane, BorderLayout.NORTH);
		
		boutonFermer = new JButton ("Fermer");
		boutonFermer.addActionListener (this);
		panelPrincipal.add (boutonFermer, BorderLayout.CENTER);
		
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
			// Si l'utilisateur veut fermer la fenêtre, on quitte l'application
			this.dispose ();
			
			client.fermerConnexion (this);
		}
		
	}
	
	@Override
	public void update (Observable arg0, Object arg1)
	{
		// Dès que le client reçoit une nouvelle information, on l'affiche
		this.panelScrollPane.add (new JLabel ((String) arg1));
		this.validate ();
	}
}