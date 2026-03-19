package es.udc.paproject.backend.test.model.services;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import es.udc.paproject.backend.model.exceptions.*;
import es.udc.paproject.backend.model.entities.MovieSessions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import es.udc.paproject.backend.model.entities.Movie;
import es.udc.paproject.backend.model.entities.MovieDao;
import es.udc.paproject.backend.model.entities.Room;
import es.udc.paproject.backend.model.entities.RoomDao;
import es.udc.paproject.backend.model.entities.Session;
import es.udc.paproject.backend.model.entities.SessionDao;
import es.udc.paproject.backend.model.entities.User;
import es.udc.paproject.backend.model.services.Block;
import es.udc.paproject.backend.model.services.CinemaService;
import es.udc.paproject.backend.model.services.UserService;

import static org.junit.jupiter.api.Assertions.*;

//ORDENAR POR FUNCIONALIDADES

@SpringBootTest
@ActiveProfiles("test")
@Transactional
public class CinemaServiceTest {

    private final Long NON_EXISTENT_ID = Long.valueOf(-1);

    @Autowired
    private CinemaService cinemaService;

    @Autowired
    private UserService userService;

    @Autowired
    private MovieDao movieDao;


    @Autowired
    private SessionDao sessionDao;


    private User createAndSignUpUser(String userName) throws Exception {
        User user = new User(userName, "1234", "Paco", "Díaz", userName + "@gmail.com");
        try{
            userService.signUp(user);
        }catch (DuplicateInstanceException e){
            throw new RuntimeException(e);
        }
        return user;
    }

    private Movie addMovie(String title, int runtime){
        return movieDao.save(new Movie(title, "Peliculón", runtime ));
    }

    private Session addSession(Movie movie, Room room, LocalDateTime date, String price) {
        return sessionDao.save(new Session(movie, room, date, new BigDecimal(price)));
    }

    private Room addRoom(String name, int capacity) {
        return roomDao.save(new Room(name, capacity));
    }



    @Test
    public void testFindCarteleraSuccess() throws Exception {

        //Datos para la base de datos
        Movie movie1 = addMovie("Batman", 150);
        Room room1 = addRoom("Sala VIP", 20);

        Movie movie2 = addMovie("Cumbres Borrascosas", 90);
        Room room2 = addRoom("Sala 14", 60);

        // Sesiones para hoy
        LocalDateTime hoySesion1 = LocalDate.now().atTime(23, 50).withNano(0);
        LocalDateTime hoySesion2 = LocalDate.now().atTime(23, 54).withNano(0);

        // Sesiones para mañana
        LocalDate mananaDate = LocalDate.now().plusDays(1);
        LocalDateTime mananaSesion1 = mananaDate.atTime(18, 0).withNano(0);
        LocalDateTime mananaSesion2 = mananaDate.atTime(16, 30).withNano(0);

        Session s1 = addSession(movie1, room1, hoySesion1, "10.00"); // Batman hoy
        Session s2 = addSession(movie1, room1, mananaSesion1, "10.00"); // Batman mañana
        Session s3 = addSession(movie2, room2, hoySesion2, "10.00"); // Cumbres hoy
        Session s4 = addSession(movie2, room2, mananaSesion2, "10.00"); // Cumbres mañana

        //Comprobamos la cartelera de hoy
        List<MovieSessions> carteleraHoy = cinemaService.findCartelera(LocalDate.now());

        // Verificamos s1 y s3 (están hoy)
        //si estamos a 54 comprobar 1, sino ninguna
        assertTrue(carteleraHoy.stream().flatMap(ms -> ms.getSessions().stream())
                .anyMatch(s -> s.getId().equals(s1.getId())));
        assertTrue(carteleraHoy.stream().flatMap(ms -> ms.getSessions().stream())
                .anyMatch(s -> s.getId().equals(s3.getId())));

        // Verificamos que s2 y s4 NO están hoy
        assertFalse(carteleraHoy.stream().flatMap(ms -> ms.getSessions().stream())
                .anyMatch(s -> s.getId().equals(s2.getId())));
        assertFalse(carteleraHoy.stream().flatMap(ms -> ms.getSessions().stream())
                .anyMatch(s -> s.getId().equals(s4.getId())));

        //Comprobamos la cartelera de mañana
        List<MovieSessions> carteleraManana = cinemaService.findCartelera(mananaDate);

        // Verificamos s2 y s4 están mañana.
        assertTrue(carteleraManana.stream().flatMap(ms -> ms.getSessions().stream())
                .anyMatch(s -> s.getId().equals(s2.getId())));
        assertTrue(carteleraManana.stream().flatMap(ms -> ms.getSessions().stream())
                .anyMatch(s -> s.getId().equals(s4.getId())));

        // Verificamos que s1 y s3 no están mañana
        assertFalse(carteleraManana.stream().flatMap(ms -> ms.getSessions().stream())
                .anyMatch(s -> s.getId().equals(s1.getId())));
        assertFalse(carteleraManana.stream().flatMap(ms -> ms.getSessions().stream())
                .anyMatch(s -> s.getId().equals(s3.getId())));
    }


    @Test
    public void testFindCarteleraInvalidDates() {
        // Caso 1: Fecha en el pasado
        LocalDate anteayer = LocalDate.now().minusDays(2);
        assertThrows(InvalidDateException.class, () -> {
            cinemaService.findCartelera(anteayer);
        });

        // Caso 2: Fecha más de 6 días
        LocalDate futuroLejano = LocalDate.now().plusDays(8);
        assertThrows(InvalidDateException.class, () -> {
            cinemaService.findCartelera(futuroLejano);
        });
    }









}
