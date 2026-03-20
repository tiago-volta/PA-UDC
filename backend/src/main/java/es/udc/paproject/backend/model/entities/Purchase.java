package es.udc.paproject.backend.model.entities;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;

@Entity
@Table(name = "Compra")
public class Purchase {

	private Long id;
	private User user;
	private Session session;
	private int numTickets;
	private String bankCard;
	private LocalDateTime purchaseDate;
	 //Calcular el totalPrice en el momento de la compra
	private boolean delivered;

	public Purchase() {
	}

	public Purchase(User user, Session session, int numTickets, String bankCard, LocalDateTime purchaseDate,
			boolean delivered) {
		this.user = user;
		this.session = session;
		this.numTickets = numTickets;
		this.bankCard = bankCard;
		this.purchaseDate = purchaseDate.withNano(0);
		this.delivered = delivered;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@ManyToOne(optional = false, fetch = FetchType.LAZY)
	@JoinColumn(name = "userId")
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	@ManyToOne(optional = false, fetch = FetchType.LAZY)
	@JoinColumn(name = "sessionId")
	public Session getSession() {
		return session;
	}

	public void setSession(Session session) {
		this.session = session;
	}

	public int getNumTickets() {
		return numTickets;
	}

	public void setNumTickets(int numTickets) {
		this.numTickets = numTickets;
	}

	public String getBankCard() {
		return bankCard;
	}

	public void setBankCard(String bankCard) {
		this.bankCard = bankCard;
	}

	public LocalDateTime getPurchaseDate() {
		return purchaseDate;
	}

	public void setPurchaseDate(LocalDateTime purchaseDate) {
		this.purchaseDate = purchaseDate.withNano(0);
	}

	@Transient
	public BigDecimal getTotalPrice() {
		return session.getPrice().multiply(new BigDecimal(numTickets));
	}

	public void setTotalPrice(BigDecimal totalPrice) {
		// No hace nada porque totalPrice se calcula dinámicamente
	}

	public boolean isDelivered() {
		return delivered;
	}

	public void setDelivered(boolean delivered) {
		this.delivered = delivered;
	}

}