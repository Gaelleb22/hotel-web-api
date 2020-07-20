package dev.hotel.controleur;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import dev.hotel.entite.Client;
import dev.hotel.repository.ClientRepository;

@RestController
@RequestMapping("/clients")
public class ClientCtrl {
	
	@Autowired ClientRepository clientRepository;
	
	@RequestMapping(method = RequestMethod.GET)
	public Page<Client> findClient(@RequestParam("start") Integer start, @RequestParam("size") Integer size){
		Page<Client> clients = clientRepository.findAll(PageRequest.of(start, size));
		return clients;
	}

}
