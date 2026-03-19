package es.udc.paproject.backend.model.entities;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Version;

@Entity
@Table(name = "Session")
public class Session {

	private Long id;
	private Movie movie;
	private Room room;
	private LocalDateTime date;
	private BigDecimal price;
	private int freeSeats;
	private long version;

	public Session() {}

	public Session(Movie movie, Room room, LocalDateTime date, BigDecimal price) {
		this.movie = movie;
		this.room = room;
		this.date = date.withNano(0);
		this.price = price.setScale(2, RoundingMode.HALF_EVEN);
		this.freeSeats = room.getCapacity();
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	// 'optional = false' significa que esta relación es obligatoria (no puede ser null).
	// 'fetch = FetchType.LAZY' establece que los datos de Movie se cargan de forma diferida, es decir, solo cuando se accede a ellos explícitamente.
	@ManyToOne(optional = false, fetch = FetchType.LAZY)
	@JoinColumn(name = "movieId")
	public Movie getMovie() {
		return movie;
	}

	public void setMovie(Movie movie) {
		this.movie = movie;
	}

	@ManyToOne(optional = false, fetch = FetchType.LAZY)
	@JoinColumn(name = "roomId")
	public Room getRoom() {
		return room;
	}

	public void setRoom(Room room) {
		this.room = room;
	}

	public LocalDateTime getDate() {
		return date;
	}

	public void setDate(LocalDateTime date) {
		this.date = date.withNano(0);
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price.setScale(2, RoundingMode.HALF_EVEN);
	}

	public int getFreeSeats() {
		return freeSeats;
	}

	public void setFreeSeats(int freeSeats) {
		this.freeSeats = freeSeats;
	}

	@Version
	public long getVersion() {
		return version;
	}

	public void setVersion(long version) {
		this.version = version;
	}
}
