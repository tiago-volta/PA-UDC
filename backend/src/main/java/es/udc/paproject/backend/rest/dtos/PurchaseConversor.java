package es.udc.paproject.backend.rest.dtos;

import java.util.List;
import java.util.stream.Collectors;

import es.udc.paproject.backend.model.entities.Purchase;

public class PurchaseConversor {

	private PurchaseConversor() {}

	public static List<PurchaseSummaryDto> toPurchaseSummaryDtos(List<Purchase> purchases) {
		return purchases.stream().map(PurchaseConversor::toPurchaseSummaryDto).collect(Collectors.toList());
	}

	public static PurchaseDto toPurchaseDto(Purchase purchase) {
		return new PurchaseDto(
			purchase.getId(),
			purchase.getPurchaseDate(),
			purchase.getSession().getMovie().getTitle(),
			purchase.getNumTickets(),
			purchase.getTotalPrice(),
			purchase.getSession().getDate(),
			purchase.getSession().getRoom().getName(),
			purchase.getSession().getId(),
			purchase.isDelivered());
	}

	private static PurchaseSummaryDto toPurchaseSummaryDto(Purchase purchase) {
		return new PurchaseSummaryDto(
			purchase.getId(),
			purchase.getPurchaseDate(),
			purchase.getSession().getMovie().getTitle(),
			purchase.getNumTickets(),
			purchase.getTotalPrice(),
			purchase.getSession().getDate(),
			purchase.isDelivered());
	}
}

