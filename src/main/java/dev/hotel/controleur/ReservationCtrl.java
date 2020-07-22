package dev.hotel.controleur;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import dev.hotel.dto.CreerReservationDto;
import dev.hotel.dto.ReservationDto;
import dev.hotel.entite.Chambre;
import dev.hotel.entite.Reservation;
import dev.hotel.exception.ChambreNotFoundException;
import dev.hotel.exception.ClientNotFoundException;
import dev.hotel.service.ReservationService;

@RestController
@RequestMapping("/reservations")
public class ReservationCtrl {
	
	@Autowired
	private ReservationService reservationService;
	
	@PostMapping
	public ReservationDto creerReservation(@RequestBody @Valid CreerReservationDto reservation){
		
		Reservation reservationCreer = reservationService.creer(reservation.getDateDebut(), reservation.getDateFin(), 
				reservation.getClientUuid(), reservation.getChambresUuid());
		
		List<UUID> chambresUuid = new ArrayList<>();
		for(Chambre chambre : reservationCreer.getChambres()) {
			chambresUuid.add(chambre.getUuid());
		}
		ReservationDto reservationDto = new ReservationDto(reservationCreer.getDateDebut(), 
				reservationCreer.getDateFin(), reservationCreer.getClient().getUuid(), chambresUuid);
		reservationDto.setUuid(reservationCreer.getUuid());
		
		return reservationDto;
	}
	
	@ExceptionHandler(value = {ClientNotFoundException.class})
	public ResponseEntity<String> onClientNotFound(ClientNotFoundException exception){
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("message : "+exception.getMessage());
	}
	
	@ExceptionHandler(value = {ChambreNotFoundException.class})
	public ResponseEntity<String> onChambreNotFound(ChambreNotFoundException exception){
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("message : "+exception.getMessage());
	}


}
