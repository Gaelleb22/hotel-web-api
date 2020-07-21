package dev.hotel.controleur;


import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
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
	public ResponseEntity<?> findClient(@RequestParam("start") Integer start, @RequestParam("size") Integer size){
		if(start == null || size == null || start<0 || size<=0) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("erreur de paramètres");
		}
		
		return ResponseEntity.status(HttpStatus.OK).body(clientRepository.findAll(PageRequest.of(start, size)).toList());
	}
	
	@RequestMapping(path = "/clients/{uuid}", method = RequestMethod.GET)
	public ResponseEntity<?> findClientByUUID(@PathVariable String uuid) {

        int dash1 = uuid.indexOf('-', 0);
        int dash2 = uuid.indexOf('-', dash1 + 1);
        int dash3 = uuid.indexOf('-', dash2 + 1);
        int dash4 = uuid.indexOf('-', dash3 + 1);
        int dash5 = uuid.indexOf('-', dash4 + 1);

        if (uuid.length()>36 || dash4 < 0 || dash5 >= 0) {
        	return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Uuid invalide");
        }
        
        
		Optional<Client> opt = clientRepository.findByUuid(UUID.fromString(uuid));
		if(opt.isEmpty()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Client non trouvé");
		}
		
		return ResponseEntity.status(HttpStatus.OK).body(opt.get());
	}

}
