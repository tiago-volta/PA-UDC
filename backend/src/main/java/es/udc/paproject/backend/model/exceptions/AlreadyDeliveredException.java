package es.udc.paproject.backend.model.exceptions;

public class AlreadyDeliveredException extends Exception {

	private Long purchaseId;

	public AlreadyDeliveredException(Long purchaseId) {
		this.purchaseId = purchaseId;
	}

	public Long getPurchaseId() {
		return purchaseId;
	}

}

