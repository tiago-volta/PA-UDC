package es.udc.paproject.backend.model.entities;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.repository.CrudRepository;

public interface PurchaseDao extends CrudRepository<Purchase, Long> {

	/**
	 * Devuelve las compras de un usuario ordenadas por fecha de compra
	 * descendente, paginadas.
	 */
	Slice<Purchase> findByUserIdOrderByPurchaseDateDesc(Long userId, Pageable pageable);

}

