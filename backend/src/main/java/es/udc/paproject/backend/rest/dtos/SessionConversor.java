package es.udc.paproject.backend.rest.dtos;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import es.udc.paproject.backend.model.entities.Session;

public class SessionConversor {

    private SessionConversor() {}

    // Convierte una lista de sesiones a dto (pra la cartelera)
    public final static List<SessionDto> toSessionDtos(List<Session> sessions) {
        List<SessionDto> items = sessions.stream()
                .map(s -> toSessionDto(s))
                .collect(Collectors.toList());

        //Ordenamos por fecha
        items.sort(Comparator.comparing(SessionDto::getDate));

        return items;
    }

    //Convertimos una sola sesion
    public final static SessionDto toSessionDto(Session session) {
        return new SessionDto(
                session.getId(),
                session.getMovie().getId(),
                session.getRoom().getId(),
                session.getDate(),
                session.getPrice(),
                session.getFreeSeats()
        );
    }
}
