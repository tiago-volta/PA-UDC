package es.udc.paproject.backend.rest.dtos;

import java.util.List;

public class MovieSessionsDto {

    private MovieSummaryDto movie; // Usamos el resumen (solo ID y Título)
    private List<SessionDto> sessions; // Lista de DTOs de sesión

    public MovieSessionsDto() {}

    public MovieSessionsDto(MovieSummaryDto movie, List<SessionDto> sessions) {
        this.movie = movie;
        this.sessions = sessions;
    }

    public MovieSummaryDto getMovie() {
        return movie;
    }

    public void setMovie(MovieSummaryDto movie) {
        this.movie = movie;
    }

    public List<SessionDto> getSessions() {
        return sessions;
    }

    public void setSessions(List<SessionDto> sessions) {
        this.sessions = sessions;
    }
}