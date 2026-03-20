package es.udc.paproject.backend.rest.dtos;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class PurchaseSummaryDto {

	private Long id;
	private LocalDateTime purchaseDate;
	private String movieTitle;
	private int numTickets;
	private BigDecimal totalPrice;
	private LocalDateTime sessionDate;
	private boolean delivered;

	public PurchaseSummaryDto() {}

	public PurchaseSummaryDto(Long id, LocalDateTime purchaseDate, String movieTitle, int numTickets,
			BigDecimal totalPrice, LocalDateTime sessionDate, boolean delivered) {
		this.id = id;
		this.purchaseDate = purchaseDate;
		this.movieTitle = movieTitle;
		this.numTickets = numTickets;
		this.totalPrice = totalPrice;
		this.sessionDate = sessionDate;
		this.delivered = delivered;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public LocalDateTime getPurchaseDate() {
		return purchaseDate;
	}

	public void setPurchaseDate(LocalDateTime purchaseDate) {
		this.purchaseDate = purchaseDate;
	}

	public String getMovieTitle() {
		return movieTitle;
	}

	public void setMovieTitle(String movieTitle) {
		this.movieTitle = movieTitle;
	}

	public int getNumTickets() {
		return numTickets;
	}

	public void setNumTickets(int numTickets) {
		this.numTickets = numTickets;
	}

	public BigDecimal getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(BigDecimal totalPrice) {
		this.totalPrice = totalPrice;
	}

	public LocalDateTime getSessionDate() {
		return sessionDate;
	}

	public void setSessionDate(LocalDateTime sessionDate) {
		this.sessionDate = sessionDate;
	}

	public boolean isDelivered() {
		return delivered;
	}

	public void setDelivered(boolean delivered) {
		this.delivered = delivered;
	}
}

