package es.udc.paproject.backend.model.exceptions;

public class IncorrectCreditCardException extends Exception {

	private Long purchaseId;

	public IncorrectCreditCardException(Long purchaseId) {
		this.purchaseId = purchaseId;
	}

	public Long getPurchaseId() {
		return purchaseId;
	}

}

