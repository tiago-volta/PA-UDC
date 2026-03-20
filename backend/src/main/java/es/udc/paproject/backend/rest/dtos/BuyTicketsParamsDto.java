package es.udc.paproject.backend.rest.dtos;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class BuyTicketsParamsDto {

	private Integer numTickets;
	private String bankCard;

	public BuyTicketsParamsDto() {}

	@NotNull
	@Min(1)
	@Max(10)
	public Integer getNumTickets() {
		return numTickets;
	}

	public void setNumTickets(Integer numTickets) {
		this.numTickets = numTickets;
	}

	@NotNull
	@Size(min = 1)
	public String getBankCard() {
		return bankCard;
	}

	public void setBankCard(String bankCard) {
		this.bankCard = bankCard;
	}
}
