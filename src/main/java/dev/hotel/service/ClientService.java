package dev.hotel.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import javax.transaction.Transactional;

import org.springframework.data.domain.PageRequest;
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


	public List<Client> findAll(Integer start, Integer size) {
		return clientRepository.findAll(PageRequest.of(start, size)).toList();
	}


	public Optional<Client> findByUuid(UUID uuid) {
		return clientRepository.findById(uuid);
	}

}
