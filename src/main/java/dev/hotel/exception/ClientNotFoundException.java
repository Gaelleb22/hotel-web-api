package dev.hotel.exception;

public class ClientNotFoundException extends RuntimeException {

	@Override
	public String getMessage() {
		String message = "uuid client non trouvé";
		return message;
	}

}
