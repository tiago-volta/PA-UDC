package es.udc.paproject.backend.rest.dtos;

import es.udc.paproject.backend.model.entities.Movie;

public class MovieConversor {

    public final static MovieDto toMovieDto(Movie movie) {
        return new MovieDto(
                movie.getId(),
                movie.getTitle(),
                movie.getSummary(),
                movie.getRuntime()
        );
    }

    public final static MovieSummaryDto toMovieSummaryDto(Movie movie) {
        return new MovieSummaryDto(
                movie.getId(),
                movie.getTitle()
        );
    }
}