package es.udc.paproject.backend.model.services;

import es.udc.paproject.backend.model.entities.MovieSessions;
import es.udc.paproject.backend.model.exceptions.InvalidDateException;

import java.time.LocalDate;
import java.util.List;

public interface CinemaService {
    public List<MovieSessions> findCartelera(LocalDate date) throws InvalidDateException;
}
