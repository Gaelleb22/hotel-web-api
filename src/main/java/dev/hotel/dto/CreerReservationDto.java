package dev.hotel.dto;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author formation
 *
 */
public class CreerReservationDto {
	
	@NotNull
	@JsonProperty("dateDebut")
	private LocalDate dateDebut;
	@NotNull
	@JsonProperty("dateFin")
    private LocalDate dateFin;

	@NotNull
	@JsonProperty("clientId")
	private UUID clientUuid;
	@Size(min = 1)
	@NotNull
    @JsonProperty("chambres")
    private List<UUID> chambresUuid = new ArrayList<>();

	/**Constructeur
	 * @param dateDebut
	 * @param dateFin
	 * @param clientUuid
	 * @param chambresUuid
	 */
	public CreerReservationDto(LocalDate dateDebut, LocalDate dateFin, UUID clientUuid, List<UUID> chambresUuid) {
		super();
		this.dateDebut = dateDebut;
		this.dateFin = dateFin;
		this.clientUuid = clientUuid;
		this.chambresUuid = chambresUuid;
	}

	/**
	 * @return the dateDebut
	 */
	public LocalDate getDateDebut() {
		return dateDebut;
	}

	/**
	 * @param dateDebut the dateDebut to set
	 */
	public void setDateDebut(LocalDate dateDebut) {
		this.dateDebut = dateDebut;
	}

	/**
	 * @return the dateFin
	 */
	public LocalDate getDateFin() {
		return dateFin;
	}

	/**
	 * @param dateFin the dateFin to set
	 */
	public void setDateFin(LocalDate dateFin) {
		this.dateFin = dateFin;
	}

	/**
	 * @return the client
	 */
	public UUID getClientUuid() {
		return clientUuid;
	}

	/**
	 * @param client the client to set
	 */
	public void setClientUuid(UUID clientUuid) {
		this.clientUuid = clientUuid;
	}

	/**
	 * @return the chambres
	 */
	public List<UUID> getChambresUuid() {
		return chambresUuid;
	}

	/**
	 * @param chambres the chambres to set
	 */
	public void setChambresUuid(List<UUID> chambresUuid) {
		this.chambresUuid = chambresUuid;
	}
	
}
