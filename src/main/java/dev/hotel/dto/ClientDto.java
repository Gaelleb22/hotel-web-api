package dev.hotel.dto;

import java.util.UUID;

public class ClientDto extends CreerClientDto{

	private UUID uuid;

    public UUID getUuid() {
		return uuid;
	}

	public void setUuid(UUID uuid) {
		this.uuid = uuid;
	}

}
