package fournisseur;

import javax.jws.WebService;

@WebService (endpointInterface = "fournisseur.HelloWorldInterface")
public class HelloWorldImplementation implements HelloWorldInterface
{	 
		@Override
		public String getHelloWorld (String name)
		{
			return "Salut " + name + " ! Ça feel today ?";
		}	 
}
