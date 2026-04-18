package es.udc.paproject.backend.rest.dtos;

import java.util.List;

public class MovieSessionsDto {

    private MovieSummaryDto movie;
    private List<BillboardSessionDto> sessions;

    public MovieSessionsDto() {}

    public MovieSessionsDto(MovieSummaryDto movie, List<BillboardSessionDto> sessions) {
        this.movie = movie;
        this.sessions = sessions;
    }

    public MovieSummaryDto getMovie() {
        return movie;
    }

    public void setMovie(MovieSummaryDto movie) {
        this.movie = movie;
    }

    public List<BillboardSessionDto> getSessions() {
        return sessions;
    }

    public void setSessions(List<BillboardSessionDto> sessions) {
        this.sessions = sessions;
    }
}
