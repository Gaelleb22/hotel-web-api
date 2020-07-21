package dev.hotel.service;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import dev.hotel.entite.Client;
import dev.hotel.repository.ClientRepository;

@Service
public class ClientService {
	
	private ClientRepository clientRepository;
	
	public ClientService(ClientRepository clientRepository) {
		super();
		this.clientRepository = clientRepository;
	}
	
	@Transactional
	public Client creer(String nom, String prenoms) {
		
		Client client = new Client(nom, prenoms);
		
		Client clientSauvegarde = this.clientRepository.save(client);
		
		return clientSauvegarde;
	}

}
