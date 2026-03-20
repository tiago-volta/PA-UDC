package es.udc.paproject.backend.rest.dtos;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class PurchaseDto {

	private Long id;
	private LocalDateTime purchaseDate;
	private String movieTitle;
	private int numTickets;
	private BigDecimal totalPrice;
	private LocalDateTime sessionDate;
	private String roomName;
	private Long sessionId;
	private boolean delivered;

	public PurchaseDto() {}

	public PurchaseDto(Long id, LocalDateTime purchaseDate, String movieTitle, int numTickets,
			BigDecimal totalPrice, LocalDateTime sessionDate, String roomName, Long sessionId, boolean delivered) {
		this.id = id;
		this.purchaseDate = purchaseDate;
		this.movieTitle = movieTitle;
		this.numTickets = numTickets;
		this.totalPrice = totalPrice;
		this.sessionDate = sessionDate;
		this.roomName = roomName;
		this.sessionId = sessionId;
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

	public String getRoomName() {
		return roomName;
	}

	public void setRoomName(String roomName) {
		this.roomName = roomName;
	}

	public Long getSessionId() {
		return sessionId;
	}

	public void setSessionId(Long sessionId) {
		this.sessionId = sessionId;
	}

	public boolean isDelivered() {
		return delivered;
	}

	public void setDelivered(boolean delivered) {
		this.delivered = delivered;
	}
}

