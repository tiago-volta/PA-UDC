package es.udc.paproject.backend.model.entities;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface SessionDao extends CrudRepository<Session, Long> {
	
	@Query("SELECT s FROM Session s WHERE s.date >= ?1 AND s.date <= ?2 " +
			"ORDER BY s.movie.title ASC, s.date ASC")
	List<Session> findSessionsByDate(LocalDateTime start, LocalDateTime end);
}