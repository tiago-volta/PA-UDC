package es.udc.paproject.backend.rest.dtos;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import es.udc.paproject.backend.model.services.MovieSessions;

public class MovieSessionsConversor {

    private MovieSessionsConversor() {}

    public final static MovieSessionsDto toMovieSessionsDto(MovieSessions movieSessions) {
        // Invocamos a los otros dos conversores
        return new MovieSessionsDto(
                MovieConversor.toMovieSummaryDto(movieSessions.getMovie()),
                SessionConversor.toSessionDtos(movieSessions.getSessions())
        );
    }

    public final static List<MovieSessionsDto> toMovieSessionsDtos(List<MovieSessions> movieSessionsList) {
        List<MovieSessionsDto> items = movieSessionsList.stream()
                .map(ms -> toMovieSessionsDto(ms))
                .collect(Collectors.toList());

        //Ordenamos por título
        items.sort(Comparator.comparing(dto -> dto.getMovie().getTitle()));

        return items;
    }
}