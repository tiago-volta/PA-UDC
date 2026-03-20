package es.udc.paproject.backend.model.services;

import es.udc.paproject.backend.model.entities.Movie;
import es.udc.paproject.backend.model.entities.MovieSessions;
import es.udc.paproject.backend.model.entities.Session;
import es.udc.paproject.backend.model.entities.Purchase;
import es.udc.paproject.backend.model.exceptions.AlreadyDeliveredException;
import es.udc.paproject.backend.model.exceptions.IncorrectCreditCardException;
import es.udc.paproject.backend.model.exceptions.PermissionException;
import es.udc.paproject.backend.model.services.Block;
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

	/**
	 * FUNC-5: histórico de compras de un usuario.
	 */
	Block<Purchase> findPurchases(Long userId, int page, int size) throws InstanceNotFoundException;

	/**
	 * FUNC-5: detalle de una compra de un usuario.
	 */
	Purchase findPurchase(Long userId, Long purchaseId)
		throws InstanceNotFoundException, PermissionException;

	/**
	 * FUNC-6: entregar entradas de una compra.
	 */
	void deliverTickets(Long purchaseId, String bankCard)
		throws InstanceNotFoundException, IncorrectCreditCardException,
				AlreadyDeliveredException, SessionAlreadyStartedException;

	public Purchase buyTickets(Long userId, Long sessionId, int numTickets, String bankCard)
		throws InstanceNotFoundException, NotEnoughSeatsException, SessionAlreadyStartedException;

        
}
