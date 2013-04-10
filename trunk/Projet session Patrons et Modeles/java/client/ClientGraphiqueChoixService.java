package client;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map.Entry;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JPanel;

/**
* 
* La fenêtre qui va afficher les choix de services disponible.
* 
* @author CHERENCEY Gaylord, BREMOND Valentin, MASSACRET Florian
* 
* @version 1.0
*
*/
	
public class ClientGraphiqueChoixService extends JFrame implements ActionListener, ItemListener
{

	private static final long serialVersionUID = 1L;

	/**
	 * Stocke l'instance du Client.
	 */
	private Client client;
	
	private JButton boutonValider;
	
	private JPanel panelPrincipal;
	private JPanel panelChoix;

	private HashSet <JCheckBox> listeCheckBox = new HashSet<JCheckBox>();
	private HashMap <String, Boolean> statutCheckBox = new HashMap<String, Boolean>();
	
	private JCheckBox checkBox;
	
	
	// CONSTRUCTEURS
	
	
	
	/**
	 * Constructeur par valeurs.
	 * 
	 * @param client Le Client lié à cette instance.
	 */
	public ClientGraphiqueChoixService (Client client)
	{
		this.client = client;
		
		// On construit la fenêtre
		this.setTitle ("Choix des services");
		
		panelPrincipal = new JPanel ();
		panelChoix = new JPanel();
		
		this.add (panelPrincipal);
		this.setContentPane (panelPrincipal);
		
		//Pour chaque service disponible (element)
		for (String element : this.client.getListeTypesInformation())
		{	
			//On cree et ajoute une checkBox avec le nom du service dans la fenetre
			checkBox = new JCheckBox(element);
			checkBox.addItemListener(this);
			panelChoix.add(checkBox);
			
			//On ajoute aussi cette entree dans le HasMap avec comme valeur de base false (soit non-cochee)
			statutCheckBox.put(element, false);
			
		    listeCheckBox.add(checkBox);
		}
		
		panelPrincipal.setLayout (new BorderLayout ());
		
		panelPrincipal.add (panelChoix, BorderLayout.CENTER);
		
		boutonValider = new JButton ("Valider");
		boutonValider.addActionListener (this);
		panelPrincipal.add (boutonValider, BorderLayout.SOUTH);
		
		this.setSize (400, 475);
		this.setLocationRelativeTo (null);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible (true);
	}
	
	
	
	// MÉTHODES
	
	
		@Override
	public void actionPerformed (ActionEvent arg0)
	{
		
		Object evenement = arg0.getSource ();
		
		//Si l'utilisateur valide ses choix
		if (evenement == boutonValider)
		{
			//Pour chaque entree dans le HashMap stutCheckBox
			for(Entry<String, Boolean> entry : statutCheckBox.entrySet()) 
			{
				//On verifie si la case est cochee 
				if (entry.getValue() == true)
				{
					//On rajoute ce service dans la liste des services choisis par le client
					this.client.getListeServiceAsouscrire().add(entry.getKey());
				}
				
			}
			
			//On fait appel a la methode souscrireAdesServices pour preciser quels services on choisi au broker (on transforme l'ArrayList des services souscris en String[] (objet serialisable) )
			this.client.getInterfaceBroker().souscrireAdesServices(this.client.getListeServiceAsouscrire().toArray(new String[this.client.getListeServiceAsouscrire().size()]));
			
			// Si l'utilisateur veut fermer la fenêtre
			this.dispose ();
			
			
			//On lance la fenetre qui va afficher l'actualisation des informations envoyees par les fournisseurs
			new ClientGraphique (this.client);
		}
		
	}

		
		
	@Override
	public void itemStateChanged(ItemEvent arg0) {
		
		Object evenement = arg0.getSource ();
		
		//Si le statut des checkbox change on le modifie aussi dans le Hashmap
		statutCheckBox.put(((JCheckBox)evenement).getText(), ((JCheckBox)evenement).isSelected());

	}
		
}