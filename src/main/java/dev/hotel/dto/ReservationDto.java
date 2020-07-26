package dev.hotel.dto;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;


/**
 * @author formation
 *
 */
public class ReservationDto extends CreerReservationDto{

	private UUID uuid;

    /**Getter
     * @return
     */
    public UUID getUuid() {
		return uuid;
	}

	/**Setter
	 * @param uuid
	 */
	public void setUuid(UUID uuid) {
		this.uuid = uuid;
	}
	
	/**Constructeur
	 * @param dateDebut
	 * @param dateFin
	 * @param clientUuid
	 * @param chambresUuid
	 */
	public ReservationDto(LocalDate dateDebut, LocalDate dateFin, UUID clientUuid, List<UUID> chambresUuid) {
		super(dateDebut, dateFin, clientUuid, chambresUuid);
	}

}
