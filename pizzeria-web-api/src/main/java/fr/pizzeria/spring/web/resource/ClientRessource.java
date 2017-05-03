package fr.pizzeria.spring.web.resource;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import fr.pizzeria.model.Client;
import fr.pizzeria.spring.web.repository.IClientRepository;

/**
 * Ressource Client
 * 
 * @author ETY 12
 *
 */
@RestController
@RequestMapping("/clients")
public class ClientRessource {

	@Autowired
	private IClientRepository clientDao;

	
	@RequestMapping(value = "{id}", method = RequestMethod.GET)
	public Client getClient(@PathVariable("id") Integer id) {
		return this.clientDao.findById(id);
	}

	@RequestMapping(method = RequestMethod.POST)
	public void ajouterClient(@RequestBody Client client) {
		//Hash du mot de passe
		client.setMotDePasse(DigestUtils.sha256Hex(client.getMotDePasse()));
		clientDao.save(client);
	}
	
	@RequestMapping(value = "{id}", method = RequestMethod.PUT)
	public void modifierClient(@PathVariable("id") Integer id, @RequestBody Client newClient) {
		
		Client oldClient = clientDao.findById(id) ;
		newClient.setId(oldClient.getId()) ;
		if(newClient.getMotDePasse() == "") {
			newClient.setMotDePasse(oldClient.getMotDePasse()) ;
		}else{
			//Hash du mot de passe
			newClient.setMotDePasse(DigestUtils.sha256Hex(newClient.getMotDePasse()));			
		}
	
		
		clientDao.save(newClient);
	}


	@RequestMapping(method = RequestMethod.GET)
	public Integer recupererClient(@RequestParam("email") String email, @RequestParam("motDePasse") String motDePasse) {
		Client reponse = clientDao.findByEmailAndMotDePasse(email, motDePasse);
		return reponse != null ? reponse.getId() : -1;
	}
	
	
	@RequestMapping(value = "/email/{email}", method = RequestMethod.GET)
	public boolean loginClientExiste(@PathVariable("email") String email) {
		Client reponse = clientDao.findByEmail(email);
		return reponse != null ? true : false;
	}
	
	@RequestMapping(value = "/verifPwd",method = RequestMethod.GET)
	public boolean testMdpClient(@RequestParam("id") Integer id, @RequestParam("motDePasse") String motDePasse) {
		
		
		Client client = clientDao.findById(id);
	
		// e3b0c44298fc1c149afbf4c8996fb92427ae41e4649b934ca495991b7852b855 <=> motDePasse="" sans cryptage
		return (client.getMotDePasse().equals(motDePasse));
	}
	
}
