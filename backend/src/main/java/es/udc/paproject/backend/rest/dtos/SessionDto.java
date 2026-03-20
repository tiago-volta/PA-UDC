package es.udc.paproject.backend.rest.dtos;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class SessionDto {

	private Long id;
	private Long movieId;
	private Long roomId;
	private LocalDateTime date;
	private BigDecimal price;
	private int freeSeats;

	public SessionDto() {}

	public SessionDto(Long id, Long movieId, Long roomId, LocalDateTime date, BigDecimal price, int freeSeats) {
		this.id = id;
		this.movieId = movieId;
		this.roomId = roomId;
		this.date = date;
		this.price = price;
		this.freeSeats = freeSeats;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getMovieId() {
		return movieId;
	}

	public void setMovieId(Long movieId) {
		this.movieId = movieId;
	}

	public Long getRoomId() {
		return roomId;
	}

	public void setRoomId(Long roomId) {
		this.roomId = roomId;
	}

	public LocalDateTime getDate() {
		return date;
	}

	public void setDate(LocalDateTime date) {
		this.date = date;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public int getFreeSeats() {
		return freeSeats;
	}

	public void setFreeSeats(int freeSeats) {
		this.freeSeats = freeSeats;
	}
}
