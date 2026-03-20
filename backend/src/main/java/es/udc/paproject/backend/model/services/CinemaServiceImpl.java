package es.udc.paproject.backend.model.services;

import es.udc.paproject.backend.model.entities.*;
import es.udc.paproject.backend.model.exceptions.InstanceNotFoundException;
import es.udc.paproject.backend.model.exceptions.InvalidDateException;
import es.udc.paproject.backend.model.exceptions.IncorrectCreditCardException;
import es.udc.paproject.backend.model.exceptions.AlreadyDeliveredException;
import es.udc.paproject.backend.model.exceptions.NotEnoughSeatsException;
import es.udc.paproject.backend.model.exceptions.PermissionException;
import es.udc.paproject.backend.model.exceptions.SessionAlreadyStartedException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

@Service
@Transactional
public class CinemaServiceImpl implements CinemaService {

    @Autowired
    private SessionDao sessionDao;
    @Autowired
    private MovieDao movieDao;
    @Autowired
    private PurchaseDao purchaseDao;
    @Autowired
    private PermissionChecker permissionChecker;

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

    @Override
    @Transactional(readOnly = true)
    public Movie findMovieById(Long id) throws InstanceNotFoundException {

        Optional<Movie> movie = movieDao.findById(id);

        if (!movie.isPresent()) {
            throw new InstanceNotFoundException("project.entities.movie", id);
        }

        return movie.get();

    }

	@Override
	@Transactional(readOnly = true)
	public Session findSession(Long sessionId) throws InstanceNotFoundException, SessionAlreadyStartedException {

		Optional<Session> session = sessionDao.findById(sessionId);

		if (!session.isPresent()) {
			throw new InstanceNotFoundException("project.entities.session", sessionId);
		}

		Session sessionEntity = session.get();

		// No se permite consultar sesiones que ya han comenzado.
		if (!sessionEntity.getDate().isAfter(LocalDateTime.now())) {
			throw new SessionAlreadyStartedException(sessionId);
		}

		return sessionEntity;
	}

	@Override
	public Purchase buyTickets(Long userId, Long sessionId, int numTickets, String bankCard)
			throws InstanceNotFoundException, NotEnoughSeatsException, SessionAlreadyStartedException {

		User user = permissionChecker.checkUser(userId);

		Optional<Session> session = sessionDao.findById(sessionId);

		if (!session.isPresent()) {
			throw new InstanceNotFoundException("project.entities.session", sessionId);
		}

		Session sessionEntity = session.get();

		// La sesión ya ha comenzado.
		if (!sessionEntity.getDate().isAfter(LocalDateTime.now())) {
			throw new SessionAlreadyStartedException(sessionId);
		}

		// No hay suficientes plazas libres.
		if (numTickets > sessionEntity.getFreeSeats()) {
			throw new NotEnoughSeatsException(sessionId);
		}

		sessionEntity.setFreeSeats(sessionEntity.getFreeSeats() - numTickets);

		Purchase purchase = new Purchase(user, sessionEntity, numTickets, bankCard, LocalDateTime.now(), false);

		return purchaseDao.save(purchase);
	}

	/**
	 * FUNC-5: histórico de compras de un usuario.
	 */
	@Override
	@Transactional(readOnly = true)
	public Block<Purchase> findPurchases(Long userId, int page, int size) throws InstanceNotFoundException {

		// Validación de existencia del usuario y permiso (sin validaciones de formato).
		permissionChecker.checkUser(userId);

		Pageable pageable = PageRequest.of(page, size);
		Slice<Purchase> slice = purchaseDao.findByUserIdOrderByPurchaseDateDesc(userId, pageable);

		return new Block<>(slice.getContent(), slice.hasNext());
	}

	/**
	 * FUNC-5: detalle de una compra de un usuario.
	 */
	@Override
	@Transactional(readOnly = true)
	public Purchase findPurchase(Long userId, Long purchaseId)
			throws InstanceNotFoundException, PermissionException {

		Optional<Purchase> purchaseOpt = purchaseDao.findById(purchaseId);

		if (!purchaseOpt.isPresent()) {
			throw new InstanceNotFoundException("project.entities.purchase", purchaseId);
		}

		Purchase purchase = purchaseOpt.get();

		// Solo el dueño de la compra puede verla.
		if (!purchase.getUser().getId().equals(userId)) {
			throw new PermissionException();
		}

		return purchase;
	}

	/**
	 * FUNC-6: entregar entradas de una compra.
	 */
	@Override
	public void deliverTickets(Long purchaseId, String bankCard)
			throws InstanceNotFoundException, IncorrectCreditCardException,
					AlreadyDeliveredException, SessionAlreadyStartedException {

		Optional<Purchase> purchaseOpt = purchaseDao.findById(purchaseId);

		if (!purchaseOpt.isPresent()) {
			throw new InstanceNotFoundException("project.entities.purchase", purchaseId);
		}

		Purchase purchase = purchaseOpt.get();

		// Tarjeta incorrecta
		if (!purchase.getBankCard().equals(bankCard)) {
			throw new IncorrectCreditCardException(purchaseId);
		}

		// Entradas ya entregadas
		if (purchase.isDelivered()) {
			throw new AlreadyDeliveredException(purchaseId);
		}

		Session session = purchase.getSession();

		// La sesión ya ha comenzado
		if (!session.getDate().isAfter(LocalDateTime.now())) {
			throw new SessionAlreadyStartedException(session.getId());
		}

		// Marcamos las entradas como entregadas
		purchase.setDelivered(true);
	}

}