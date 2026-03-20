package es.udc.paproject.backend.model.services;

import es.udc.paproject.backend.model.entities.Movie;
import es.udc.paproject.backend.model.entities.MovieSessions;
import es.udc.paproject.backend.model.entities.Session;
import es.udc.paproject.backend.model.entities.Purchase;
import es.udc.paproject.backend.model.exceptions.InstanceNotFoundException;
import es.udc.paproject.backend.model.exceptions.InvalidDateException;
import es.udc.paproject.backend.model.exceptions.SessionAlreadyStartedException;
import es.udc.paproject.backend.model.exceptions.NotEnoughSeatsException;


import java.time.LocalDate;
import java.util.List;

public interface CinemaService {
    public List<MovieSessions> findCartelera(LocalDate date) throws InvalidDateException;

    public Movie findMovieById(Long id) throws InstanceNotFoundException;
    
    public Session findSession(Long sessionId) throws InstanceNotFoundException, SessionAlreadyStartedException;

}
