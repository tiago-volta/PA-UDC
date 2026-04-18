package es.udc.paproject.backend.rest.dtos;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import es.udc.paproject.backend.model.entities.MovieSessions;

public class MovieSessionsConversor {

    private MovieSessionsConversor() {}

    public final static MovieSessionsDto toMovieSessionsDto(MovieSessions movieSessions) {
        List<BillboardSessionDto> sessionDtos = movieSessions.getSessions().stream()
                .map(s -> new BillboardSessionDto(s.getId(), s.getDate()))
                .sorted(Comparator.comparing(BillboardSessionDto::getDate))
                .collect(Collectors.toList());

        return new MovieSessionsDto(
                MovieConversor.toMovieSummaryDto(movieSessions.getMovie()),
                sessionDtos
        );
    }

    public final static List<MovieSessionsDto> toMovieSessionsDtos(List<MovieSessions> movieSessionsList) {
        List<MovieSessionsDto> items = movieSessionsList.stream()
                .map(ms -> toMovieSessionsDto(ms))
                .collect(Collectors.toList());

        items.sort(Comparator.comparing(dto -> dto.getMovie().getTitle()));

        return items;
    }
}
