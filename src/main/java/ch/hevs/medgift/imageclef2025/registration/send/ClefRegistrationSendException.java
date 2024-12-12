package ch.hevs.medgift.imageclef2025.registration.send;

public class ClefRegistrationSendException extends Exception {

	private static final long serialVersionUID = 3154191069743933574L;

	public ClefRegistrationSendException(String errorMessage, Throwable err) {
		super(errorMessage, err);
	}

	public ClefRegistrationSendException(String errorMessage) {
		super(errorMessage);
	}

}
