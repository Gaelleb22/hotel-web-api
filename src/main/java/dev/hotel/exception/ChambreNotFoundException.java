package dev.hotel.exception;

import java.util.UUID;

public class ChambreNotFoundException extends RuntimeException {
	
	private UUID uuid;
	
	@Override
	public String getMessage() {
		String message = "la chambre "+this.uuid+" n'existe pas";
		return message;
	}

	/**
	 * @return the uuid
	 */
	public UUID getUuid() {
		return uuid;
	}

	/**
	 * @param uuid the uuid to set
	 */
	public void setUuid(UUID uuid) {
		this.uuid = uuid;
	}
	
	

}
