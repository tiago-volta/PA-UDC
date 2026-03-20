package es.udc.paproject.backend.rest.dtos;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class DeliverTicketsParamsDto {

	private String bankCard;

	public DeliverTicketsParamsDto() {}

	@NotNull
	@Size(min = 1)
	public String getBankCard() {
		return bankCard;
	}

	public void setBankCard(String bankCard) {
		this.bankCard = bankCard;
	}
}

