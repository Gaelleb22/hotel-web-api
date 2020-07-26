package dev.hotel.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import dev.hotel.entite.Chambre;
import dev.hotel.entite.Client;
import dev.hotel.entite.Reservation;
import dev.hotel.exception.ChambreNotFoundException;
import dev.hotel.exception.ClientNotFoundException;
import dev.hotel.repository.ReservationRepository;

@Service
public class ReservationService {
	
	private ReservationRepository reservationRepository;
	
	private ClientService clientService;
	private ChambreService chambreService;
	
	/**Constructeur
	 * @param reservationRepository
	 * @param clientService
	 * @param chambreService
	 */
	public ReservationService(ReservationRepository reservationRepository, ClientService clientService,
			ChambreService chambreService) {
		super();
		this.reservationRepository = reservationRepository;
		this.clientService = clientService;
		this.chambreService = chambreService;
	}


	/**Méthode pour creer une réservation et la sauvegarder
	 * @param dateDebut
	 * @param dateFin
	 * @param clientUuid
	 * @param chambresUuid
	 * @return
	 */
	@Transactional
	public Reservation creer(LocalDate dateDebut, LocalDate dateFin, UUID clientUuid, List<UUID> chambresUuid) {
		Optional<Client> client = clientService.findByUuid(clientUuid);
		
		if(client.isEmpty()) {
			throw new ClientNotFoundException();
		}
		
		List<Chambre> chambres = new ArrayList<>();
		for(UUID uuid : chambresUuid) {
			Optional<Chambre> opt = chambreService.findByUuid(uuid);
			
			if(opt.isEmpty()) {
				ChambreNotFoundException exception = new ChambreNotFoundException();
				exception.setUuid(uuid);
				throw exception;
			}			
			chambres.add(opt.get());
			
			
		}
		
		
		Reservation reservation = new Reservation(dateDebut, dateFin, client.get(), chambres);
		return this.reservationRepository.save(reservation);
	}

}
