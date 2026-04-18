package es.udc.paproject.backend.model.services;

import es.udc.paproject.backend.model.entities.Movie;
import es.udc.paproject.backend.model.entities.MovieSessions;
import es.udc.paproject.backend.model.entities.Purchase;
import es.udc.paproject.backend.model.entities.Session;
import es.udc.paproject.backend.model.exceptions.*;

import java.time.LocalDate;
import java.util.List;

public interface CinemaService {

	Session findSession(Long sessionId) throws InstanceNotFoundException, SessionAlreadyStartedException;

	Purchase buyTickets(Long userId, Long sessionId, int numTickets, String bankCard)
		throws InstanceNotFoundException, NotEnoughSeatsException, SessionAlreadyStartedException;

	public List<MovieSessions> findCartelera(LocalDate date) throws InvalidDateException;

	Movie findMovieById(Long id) throws InstanceNotFoundException;

	/**
	 * FUNC-5: histórico de compras de un usuario.
	 */
	Block<Purchase> findPurchases(Long userId, int page, int size) throws InstanceNotFoundException;

	/**
	 * FUNC-6: entregar entradas de una compra.
	 */
	void deliverTickets(Long purchaseId, String bankCard)
		throws InstanceNotFoundException, IncorrectCreditCardException,
			AlreadyDeliveredException, SessionAlreadyStartedException;
}
