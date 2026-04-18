package es.udc.paproject.backend.rest.dtos;

import es.udc.paproject.backend.model.entities.Session;

public class SessionConversor {

    private SessionConversor() {}

    public final static SessionDto toSessionDto(Session session) {
        return new SessionDto(
                session.getId(),
                session.getMovie().getId(),
                session.getMovie().getTitle(),
                session.getMovie().getRuntime(),
                session.getRoom().getId(),
                session.getRoom().getName(),
                session.getDate(),
                session.getPrice(),
                session.getFreeSeats()
        );
    }
}
