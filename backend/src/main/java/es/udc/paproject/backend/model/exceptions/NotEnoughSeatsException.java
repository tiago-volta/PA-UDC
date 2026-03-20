package es.udc.paproject.backend.model.exceptions;

@SuppressWarnings("serial")
public class NotEnoughSeatsException extends Exception {

	private Long sessionId;

	public NotEnoughSeatsException(Long sessionId) {
		this.sessionId = sessionId;
	}

	public Long getSessionId() {
		return sessionId;
	}

}

