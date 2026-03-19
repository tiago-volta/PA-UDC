package es.udc.paproject.backend.model.services;

import es.udc.paproject.backend.model.entities.Movie;
import es.udc.paproject.backend.model.entities.MovieSessions;
import es.udc.paproject.backend.model.entities.Session;
import es.udc.paproject.backend.model.entities.SessionDao;
import es.udc.paproject.backend.model.exceptions.InvalidDateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class CinemaServiceImpl implements CinemaService {

    @Autowired
    private SessionDao sessionDao;

    //Funcionalidad 1
    @Override
    @Transactional(readOnly = true)
    public List<MovieSessions> findCartelera(LocalDate date) throws InvalidDateException {

        if (date == null) {
            throw new InvalidDateException(date);
        }

        LocalDate today = LocalDate.now();
        LocalDateTime now = LocalDateTime.now().withNano(0);

        if (date.isBefore(today) || date.isAfter(today.plusDays(6))) {
            throw new InvalidDateException(date);
        }

        LocalDateTime start;

        if (date.equals(today)) {
            start = now;
        } else {
            start = date.atStartOfDay();
        }

        LocalDateTime end = date.atTime(23, 59, 59);

        List<Session> sessions = sessionDao.findSessionsByDate(start, end);

        List<MovieSessions> cartelera = new ArrayList<>();
        Movie currentMovie = null;
        List<Session> currentMovieSessions = new ArrayList<>();

        for (Session s : sessions) {
            if (currentMovie != null && !s.getMovie().getId().equals(currentMovie.getId())) {
                cartelera.add(new MovieSessions(currentMovie, currentMovieSessions));
                currentMovieSessions = new ArrayList<>();
            }
            currentMovieSessions.add(s);
            currentMovie = s.getMovie();
        }

        // Añadir último grupo
        if (currentMovie != null) {
            cartelera.add(new MovieSessions(currentMovie, currentMovieSessions));
        }

        return cartelera;
    }

}