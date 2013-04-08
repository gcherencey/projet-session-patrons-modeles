package commun;

import java.io.Serializable;



/**
 * 
 * Paquet contenant une information et son type.
 * 
 * @author Valentin Brémond, Florian Massacret, Gaylord Cherencey
 * 
 * @version 1.0
 *
 */
public class Information implements Serializable
{
	/**
	 * Utilisé par la sérialisation.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * L'information contenu dans le paquet.
	 */
	private String information;
	
	/**
	 * Le type de l'information.
	 */
	private int type;
	
	/**
	 * Les différents types disponibles.
	 */
	public static final int
		THEATRE = 0,
		LIVRES = 1,
		CUISINE = 2,
		INFORMATIQUE = 3,
		SCIENCES = 4,
		POLITIQUE = 5,
		MUSIQUE = 6,
		CINEMA = 7,
		AUTOMOBILE = 8,
		JARDINAGE = 9;
	
	
	
	// CONSTRUCTEURS
	
	
	
	/**
	 * Constructeur par défaut.
	 */
	public Information ()
	{
	}
	
	
	
	/**
	 * Constructeur par valeurs.
	 * 
	 * @param type Le type de l'information.
	 * @param information L'information.
	 */
	public Information (int type, String information)
	{
		this.type = type;
		this.information = information;
	}
	
	
	
	// MÉTHODES
	
	
	
	/**
	 * Permet de récupérer le type de l'information sous la forme d'une chaîne de caractères compréhensible par un être humain.
	 * 
	 * @return Le type de l'information.
	 */
	public String getTypeToString ()
	{
		String typeToString = "";
		
		switch (this.type)
		{
			case 0: typeToString = "Théâtre";
			break;
			
			case 1: typeToString = "Livres";
			break;
			
			case 2: typeToString = "Cuisine";
			break;
			
			case 3: typeToString = "Informatique";
			break;
			
			case 4: typeToString = "Sciences";
			break;
			
			case 5: typeToString = "Politique";
			break;
			
			case 6: typeToString = "Musique";
			break;
			
			case 7: typeToString = "Cinéma";
			break;
			
			case 8: typeToString = "Automobile";
			break;
			
			case 9: typeToString = "Jardinage";
			break;
			
			default: typeToString = "Type inconnu";
			break;
		}
		
		return typeToString;
	}
	
	
	
	// GETTERS & SETTERS
	
	
	
	public String getInformation ()
	{
		return information;
	}

	public void setInformation (String information)
	{
		this.information = information;
	}

	public int getType ()
	{
		return type;
	}

	public void setType (int type)
	{
		this.type = type;
	}
}
