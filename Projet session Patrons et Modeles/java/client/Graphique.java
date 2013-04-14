package client;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;



/**
 * 
 * Classe abstraite mère des classes graphiques.
 * 
 * @author Valentin Brémond, Gaylord Cherencey, Florian Massacret
 * 
 * @version 1.0
 *
 */
public abstract class Graphique extends JFrame
{
	private static final long serialVersionUID = 1L;

	
	
	// CONSTRUCTEURS
	
	
	
	/**
	 * Constructeur par défaut.
	 */
	public Graphique ()
	{
		this.setDefaultCloseOperation (JFrame.DO_NOTHING_ON_CLOSE);
		
		this.addWindowListener(new WindowAdapter ()
		{
			public void windowClosing(WindowEvent e)
			{
				fermerFenetre ();
			}
		});
	}
	
	
	
	// MÉTHODES
	
	
	
	/**
	 * Méthode appelée lors de la fermeture de la fenêtre.
	 */
	protected abstract void fermerFenetre ();
}
