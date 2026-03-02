package es.udc.paproject.backend.model.entities;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.repository.CrudRepository;

//Operacions CRUD (Create, Read, Update, Delete) para Session.
//Incluye consulta para recuperar todas las sesiones de un día concreto,
//buscando entre el inicio (00:00) y el final (23:59:59) de ese día.
public interface SessionDao extends CrudRepository<Session, Long> {

	List<Session> findByDateBetween(LocalDateTime start, LocalDateTime end);

}
