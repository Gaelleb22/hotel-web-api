package dev.hotel.controleur;


import java.util.Optional;
import java.util.UUID;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import dev.hotel.dto.ClientDto;
import dev.hotel.dto.CreerClientDto;
import dev.hotel.entite.Client;
import dev.hotel.service.ClientService;

@RestController
@RequestMapping("/clients")
public class ClientCtrl {
	
	@Autowired
	private ClientService clientService;

	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<?> findClient(@RequestParam("start") Integer start, @RequestParam("size") Integer size){
		if(start == null || size == null || start<0 || size<=0) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("erreur de paramètres");
		}
		
		return ResponseEntity.status(HttpStatus.OK).body(clientService.findAll(start, size));
	}
	
	@RequestMapping(path = "/{uuidString}", method = RequestMethod.GET)
	public ResponseEntity<?> findClientByUUID(@PathVariable String uuidString) {
        
		UUID uuid = null;
		try{
			uuid = UUID.fromString(uuidString);
		} catch(IllegalArgumentException e) {
			new IllegalArgumentException (e.getMessage());
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Uuid invalide");
		}
        
		Optional<Client> opt = clientService.findByUuid(uuid);
		if(opt.isEmpty()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Client non trouvé");
		}
		
		return ResponseEntity.status(HttpStatus.OK).body(opt.get());
	}
	
	
	@PostMapping
	public ClientDto creerClient(@RequestBody @Valid CreerClientDto client){
		
		Client clientCreer = clientService.creer(client.getNom(), client.getPrenoms());
		
		ClientDto clientDto = new ClientDto();
		clientDto.setNom(clientCreer.getNom());
		clientDto.setPrenoms(clientCreer.getPrenoms());
		clientDto.setUuid(clientCreer.getUuid());
		
		return clientDto;
	}

}
