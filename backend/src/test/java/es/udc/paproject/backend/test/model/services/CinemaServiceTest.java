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

import es.udc.paproject.backend.model.entities.Purchase;
import es.udc.paproject.backend.model.entities.PurchaseDao;
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
	private RoomDao roomDao;

	@Autowired
	private SessionDao sessionDao;

	@Autowired
	private PurchaseDao purchaseDao;

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

	private Room addRoom(String name, int capacity) {
		return roomDao.save(new Room(name, capacity));
	}

	private Session addSession(Movie movie, Room room, LocalDateTime date, String price) {
		return sessionDao.save(new Session(movie, room, date, new BigDecimal(price)));
	}

	private Purchase addPurchase(User user, Session session, int numTickets, String bankCard) {
		return purchaseDao.save(new Purchase(user, session, numTickets, bankCard, LocalDateTime.now(), false));
	}


	@Test
	public void testFindSessionSuccess() throws Exception {
		Movie movie = addMovie("Regreso al futuro", 116);
		Room room = addRoom("Sala 3", 50);
		LocalDateTime date = LocalDateTime.now().plusDays(1).withNano(0);
		BigDecimal price = new BigDecimal("8.50");
		Session session = addSession(movie, room, date, price.toString());

		Session found = cinemaService.findSession(session.getId());

		assertNotNull(found);
		assertEquals(session.getId(), found.getId());
		assertEquals("Regreso al futuro", found.getMovie().getTitle());
		assertEquals(116, found.getMovie().getRuntime());
		assertEquals("Sala 3", found.getRoom().getName());
		assertEquals(50, found.getRoom().getCapacity());
		assertEquals(date, found.getDate());
		assertEquals(price, found.getPrice());
	}

	@Test
	public void testFindSessionNonExistent() {
		assertThrows(InstanceNotFoundException.class, () -> cinemaService.findSession(NON_EXISTENT_ID));
	}

	@Test
	public void testFindSessionAlreadyStarted() {
		Movie movie = addMovie("TorrentePresidente", 100);
		Room room = addRoom("Sala 5", 30);
		LocalDateTime pastDate = LocalDateTime.now().minusHours(1).withNano(0);
		Session session = addSession(movie, room, pastDate, "7.00");

		assertThrows(SessionAlreadyStartedException.class,
				() -> cinemaService.findSession(session.getId()));
	}

	@Test
	public void testBuyTicketsNonExistentUserOrSession() throws Exception {
		assertThrows(InstanceNotFoundException.class,
				() -> cinemaService.buyTickets(NON_EXISTENT_ID, 1L, 2, "1111"));
		User user = createAndSignUpUser("viewer0");
		assertThrows(InstanceNotFoundException.class,
				() -> cinemaService.buyTickets(user.getId(), NON_EXISTENT_ID, 2, "1111"));
	}

	@Test
	public void testBuyTicketsSuccess() throws Exception {
		User user = createAndSignUpUser("viewer1");
		Movie movie = addMovie("ET", 120);
		Room room = addRoom("Sala 1", 10);
		Session session= addSession(movie, room, LocalDateTime.now().plusDays(1),"5.00");

		int numTickets = 2;
		String bankCard = "1111-2222-3333-4444";

		Purchase compra = cinemaService.buyTickets(user.getId(), session.getId(), numTickets, bankCard);

		Purchase found = purchaseDao.findById(compra.getId()).get();

		assertEquals(user.getId(), found.getUser().getId());
		assertEquals(session.getId(), found.getSession().getId());
		assertEquals(numTickets, found.getNumTickets());
		assertEquals(bankCard, found.getBankCard());
		assertEquals(session.getPrice().multiply(new BigDecimal(numTickets)), found.getTotalPrice());
		assertFalse(found.isDelivered());
	}

	@Test
	public void testBuyTicketsUpdatesFreeSeatsAndFields() throws Exception {
		User user = createAndSignUpUser("viewer");
		Movie movie = addMovie("F1", 120);
		Room room = addRoom("Sala 8", 10);
		Session session = addSession(movie, room, LocalDateTime.now().plusDays(1), "5.00");

		int initialFreeSeats = session.getFreeSeats();

		Purchase purchase = cinemaService.buyTickets(user.getId(), session.getId(), 3, "9999-8888-7777-6666");

		Session updatedSession = sessionDao.findById(session.getId()).get();

		assertEquals(initialFreeSeats - 3, updatedSession.getFreeSeats());
		assertEquals(3, purchase.getNumTickets());
		assertEquals("9999-8888-7777-6666", purchase.getBankCard());
		assertNotNull(purchase.getTotalPrice());
	}

	@Test
	public void testBuyTicketsNotEnoughSeats() throws Exception {
		User user = createAndSignUpUser("viewer2");
		Movie movie = addMovie("Interestelar", 169);
		Room room = addRoom("Sala 2", 2);
		Session session = addSession(movie, room, LocalDateTime.now().plusDays(1), "5.00");

		assertThrows(NotEnoughSeatsException.class,
				() -> cinemaService.buyTickets(user.getId(), session.getId(), 1000, "1111-2222-3333-4444"));
	}

	@Test
	public void testBuyTicketsSessionAlreadyStarted() throws Exception {
		User user = createAndSignUpUser("viewer");
		Movie movie = addMovie("Up", 100);
		Room room = addRoom("Sala 9", 20);
		LocalDateTime pastDate = LocalDateTime.now().minusHours(1).withNano(0);
		Session session = addSession(movie, room, pastDate, "6.00");

		assertThrows(SessionAlreadyStartedException.class,
				() -> cinemaService.buyTickets(user.getId(), session.getId(), 2, "1111-2222-3333-4444"));
	}

	@Test
	public void testFindMovieById() throws InstanceNotFoundException {
		Movie movie = addMovie("Oppenheimer", 180);

		Movie foundMovie = cinemaService.findMovieById(movie.getId());

		assertNotNull(foundMovie);
		assertEquals(movie.getId(), foundMovie.getId());
		assertNotEquals("Andy y Lucas", foundMovie.getTitle());
		assertEquals("Oppenheimer", foundMovie.getTitle());
	}

	@Test
	public void testFindMovieByIdNotFound() {
		assertThrows(InstanceNotFoundException.class, () -> {
			cinemaService.findMovieById(NON_EXISTENT_ID);
		});
	}

	@Test
	public void testFindCarteleraSuccess() throws Exception {
		//Datos para la base de datos

		Movie movie1 = addMovie("Batman", 150);
		Room room1 = addRoom("Sala VIP", 20);

		Movie movie2 = addMovie("Cumbres Borrascosas", 90);
		Room room2 = addRoom("Sala 14", 60);

		// Sesiones para hoy (deben ser posteriores a LocalDateTime.now() porque la cartelera
		// de hoy filtra desde "ahora"; horarios tardíos evitan fallos según la hora del test).
		LocalDateTime hoySesion1 = LocalDate.now().atTime(23, 54).withNano(0);
		LocalDateTime hoySesion2 = LocalDate.now().atTime(23, 55).withNano(0);

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

		// Caso 2: Fecha demasiado lejana (más de 6 días)
		LocalDate futuroLejano = LocalDate.now().plusDays(8);
		assertThrows(InvalidDateException.class, () -> {
			cinemaService.findCartelera(futuroLejano);
		});
	}



	@Test
	public void testFindPurchasesNonExistentUser() {
		assertThrows(InstanceNotFoundException.class,
				() -> cinemaService.findPurchases(NON_EXISTENT_ID, 0, 10));
	}

	@Test
	public void testFindPurchasesSuccess() throws Exception {
		User user = createAndSignUpUser("viewerPurchases");
		Movie movie = addMovie("Duna", 155);
		Room room = addRoom("Sala 1", 20);
		Session session = addSession(movie, room, LocalDateTime.now().plusDays(1), "8.00");
		Purchase purchase = cinemaService.buyTickets(user.getId(), session.getId(), 2, "1111-2222-3333-4444");

		Block<Purchase> block = cinemaService.findPurchases(user.getId(), 0, 10);

		assertNotNull(block.getItems());
		assertEquals(1, block.getItems().size());
		assertEquals(purchase.getId(), block.getItems().get(0).getId());
		assertEquals("Duna", block.getItems().get(0).getSession().getMovie().getTitle());
		assertFalse(block.getExistMoreItems());
	}

	@Test
	public void testFindPurchasesEmpty() throws Exception {
		User user = createAndSignUpUser("viewerEmpty");
		Block<Purchase> block = cinemaService.findPurchases(user.getId(), 0, 10);
		assertNotNull(block.getItems());
		assertTrue(block.getItems().isEmpty());
		assertFalse(block.getExistMoreItems());
	}

	// --- FUNC-6: entregar las entradas de una compra ---

	@Test
	public void testDeliverTicketsNonExistentPurchase() {
		assertThrows(InstanceNotFoundException.class,
				() -> cinemaService.deliverTickets(NON_EXISTENT_ID, "1111-2222-3333-4444"));
	}

	@Test
	public void testDeliverTicketsIncorrectCreditCard() throws Exception {
		User user = createAndSignUpUser("viewerDeliverCard");
		Movie movie = addMovie("Gladiator", 155);
		Room room = addRoom("Sala 4", 25);
		Session session = addSession(movie, room, LocalDateTime.now().plusDays(1), "8.50");
		Purchase purchase = cinemaService.buyTickets(user.getId(), session.getId(), 2, "1111-2222-3333-4444");

		assertThrows(IncorrectCreditCardException.class,
				() -> cinemaService.deliverTickets(purchase.getId(), "9999-9999-9999-9999"));
	}

	@Test
	public void testDeliverTicketsAlreadyDelivered() throws Exception {
		User user = createAndSignUpUser("viewerDeliverTwice");
		Movie movie = addMovie("Titanic", 194);
		Room room = addRoom("Sala 5", 20);
		Session session = addSession(movie, room, LocalDateTime.now().plusDays(1), "7.00");
		Purchase purchase = cinemaService.buyTickets(user.getId(), session.getId(), 1, "1111-2222-3333-4444");

		cinemaService.deliverTickets(purchase.getId(), "1111-2222-3333-4444");
		assertThrows(AlreadyDeliveredException.class,
				() -> cinemaService.deliverTickets(purchase.getId(), "1111-2222-3333-4444"));
	}

	@Test
	public void testDeliverTicketsSessionAlreadyStarted() throws Exception {
		User user = createAndSignUpUser("viewerDeliverPast");
		Movie movie = addMovie("PastMovie", 90);
		Room room = addRoom("Sala 6", 10);
		LocalDateTime pastDate = LocalDateTime.now().minusHours(1).withNano(0);
		Session session = addSession(movie, room, pastDate, "5.00");
		// La compra se crea directamente en la BBDD (la sesión ya empezó y el servicio la rechazaría)
		Purchase purchase = addPurchase(user, session, 1, "1111-2222-3333-4444");

		assertThrows(SessionAlreadyStartedException.class,
				() -> cinemaService.deliverTickets(purchase.getId(), "1111-2222-3333-4444"));
	}

	@Test
	public void testDeliverTicketsSuccess() throws Exception {
		User user = createAndSignUpUser("viewerDeliverOk");
		Movie movie = addMovie("Avatar", 162);
		Room room = addRoom("Sala 7", 40);
		Session session = addSession(movie, room, LocalDateTime.now().plusDays(1), "10.00");
		Purchase purchase = cinemaService.buyTickets(user.getId(), session.getId(), 2, "1111-2222-3333-4444");
		assertFalse(purchase.isDelivered());

		cinemaService.deliverTickets(purchase.getId(), "1111-2222-3333-4444");

		Purchase found = purchaseDao.findById(purchase.getId()).get();
		assertTrue(found.isDelivered());
	}
}
