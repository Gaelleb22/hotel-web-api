package dev.hotel.controleur;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import dev.hotel.entite.Client;
import dev.hotel.repository.ClientRepository;

@RestController
public class ClientCtrl {
	
	@Autowired ClientRepository clientRepository;
	
	@RequestMapping(value = "/clients", method = RequestMethod.GET)
	public ResponseEntity<List<Client>> findClient(@RequestParam("start") Integer start, @RequestParam("size") Integer size){
		List<Client> clients = clientRepository.findAll(PageRequest.of(start, size)).toList();
		
		return ResponseEntity.status(200).body(clients);
	}
	
	/*@RequestMapping(method = RequestMethod.GET)
	public Client findClientByUUID() {
		return null;
	}*/

}
