package es.udc.paproject.backend.rest.dtos;

import java.time.LocalDateTime;

public class BillboardSessionDto {

	private Long id;
	private LocalDateTime date;

	public BillboardSessionDto() {}

	public BillboardSessionDto(Long id, LocalDateTime date) {
		this.id = id;
		this.date = date;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public LocalDateTime getDate() {
		return date;
	}

	public void setDate(LocalDateTime date) {
		this.date = date;
	}
}
