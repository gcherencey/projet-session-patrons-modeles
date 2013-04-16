package client;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashSet;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;



/**
 * 
 * La fenêtre qui va afficher les choix de services disponible.
 * 
 * @author Gaylord Cherencey, Florian Massacret, Valentin Brémond
 * 
 * @version 1.0
 *
*/
public class ClientGraphiqueChoixService extends Graphique implements ActionListener
{
	private static final long serialVersionUID = 1L;

	/**
	 * Stocke l'instance du Client.
	 */
	private Client client;
	
	/**
	 * Stocke la liste des types d'information.
	 */
	private HashSet<String> listeTypesInformations;
	
	/**
	 * Permet de savoir si l'utilisateur a coché au moins une case.
	 */
	boolean auMoinsUne = false;
	
	/**
	 * La liste des CheckBox qui vont contenir les types d'informations.
	 */
	private JCheckBox[] checkBoxs;
	
	private JButton boutonValider;
	
	private JPanel panelPrincipal;
	private JPanel panelChoix;
	
	
	
	// CONSTRUCTEURS
	
	
	
	/**
	 * Constructeur par valeurs.
	 * 
	 * @param client Le Client lié à cette instance.
	 * @param listeTypesInformations La liste des types d'information disponibles.
	 */
	public ClientGraphiqueChoixService (Client client, HashSet<String> listeTypesInformations)
	{
		super ();
		
		this.client = client;
		this.listeTypesInformations = listeTypesInformations;
		
		// On construit la fenêtre
		this.setTitle ("Choix des services");
		
		panelPrincipal = new JPanel ();
		panelChoix = new JPanel ();
		panelChoix.setLayout(new BoxLayout(panelChoix, BoxLayout.PAGE_AXIS));
		
		this.add (panelPrincipal);
		this.setContentPane (panelPrincipal);
		
		// On créé un tableau de checkboxs de taille identique au nombre de types d'informations disponibles
		checkBoxs = new JCheckBox [this.listeTypesInformations.size ()];
		// Permet de se promener dans ce tableau de checkboxs
		int i = 0;
		
		// Pour chaque type disponible
		for (String type : this.listeTypesInformations)
		{
			// On crée et ajoute une CheckBox avec le nom du service dans la fenêtre
			checkBoxs[i] = new JCheckBox (type);
			
			panelChoix.add (checkBoxs[i]);
			
		    ++i;
		}
		
		panelPrincipal.setLayout (new BorderLayout ());
		
		panelPrincipal.add (panelChoix, BorderLayout.CENTER);
		
		boutonValider = new JButton ("Valider");
		boutonValider.addActionListener (this);
		panelPrincipal.add (boutonValider, BorderLayout.SOUTH);
		
		this.setSize (250, 400);
		this.setLocationRelativeTo (null);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible (true);
	}
	
	
	
	// MÉTHODES
	
	
	
	@Override
	public void actionPerformed (ActionEvent arg0)
	{
		// On récupère la source
		Object evenement = arg0.getSource ();
		
		//Si l'utilisateur valide ses choix
		if (evenement == boutonValider)
		{	
			// On prépare la liste finale des types choisis par l'utilisateur
			HashSet<String> listeTypesChoisis = new HashSet<String> ();
			
			for (JCheckBox checkbox : checkBoxs)
			{
				if (checkbox.isSelected ())
				{
					this.auMoinsUne = true;
					listeTypesChoisis.add (checkbox.getText ());
				}
			}
			
			// Si l'utilisateur a coché au moins une case, on peut continuer normalement
			if (auMoinsUne)
			{
				// On envoie cette liste au Client pour appeler la prochaine fenêtre
				this.client.choisirTypesInformations (listeTypesChoisis);
				
				// Puis on ferme la fenêtre
				fermerFenetre ();
			}
			else
			{
				// Sinon on lui affiche un message
				JOptionPane.showMessageDialog (null, "Veuillez cocher au moins une case", "Information", JOptionPane.INFORMATION_MESSAGE);
			}
		}
	}



	@Override
	protected void fermerFenetre ()
	{
		this.dispose ();
	}	
}