package dev.hotel.dto;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;


public class ReservationDto extends CreerReservationDto{

	private UUID uuid;

    public UUID getUuid() {
		return uuid;
	}

	public void setUuid(UUID uuid) {
		this.uuid = uuid;
	}
	
	public ReservationDto(LocalDate dateDebut, LocalDate dateFin, UUID clientUuid, List<UUID> chambresUuid) {
		super(dateDebut, dateFin, clientUuid, chambresUuid);
	}

}
