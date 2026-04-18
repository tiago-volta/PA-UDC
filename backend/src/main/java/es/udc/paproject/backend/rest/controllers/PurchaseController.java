package es.udc.paproject.backend.rest.controllers;

import static es.udc.paproject.backend.rest.dtos.PurchaseConversor.toPurchaseSummaryDtos;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import es.udc.paproject.backend.model.entities.Purchase;
import es.udc.paproject.backend.model.exceptions.AlreadyDeliveredException;
import es.udc.paproject.backend.model.exceptions.IncorrectCreditCardException;
import es.udc.paproject.backend.model.exceptions.InstanceNotFoundException;
import es.udc.paproject.backend.model.exceptions.SessionAlreadyStartedException;
import es.udc.paproject.backend.model.services.Block;
import es.udc.paproject.backend.model.services.CinemaService;
import es.udc.paproject.backend.rest.dtos.BlockDto;
import es.udc.paproject.backend.rest.dtos.DeliverTicketsParamsDto;
import es.udc.paproject.backend.rest.dtos.PurchaseSummaryDto;

@RestController
@RequestMapping("/purchases")
public class PurchaseController {

	@Autowired
	private CinemaService cinemaService;

	@GetMapping
	@Transactional(readOnly = true)
	public BlockDto<PurchaseSummaryDto> findPurchases(@RequestAttribute Long userId,
			@RequestParam(defaultValue = "0") int page) throws InstanceNotFoundException {

		Block<Purchase> block = cinemaService.findPurchases(userId, page, 2);
		return new BlockDto<>(toPurchaseSummaryDtos(block.getItems()), block.getExistMoreItems());
	}

	@PostMapping("/{purchaseId}/deliver")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void deliverTickets(@PathVariable Long purchaseId,
			@Validated @RequestBody DeliverTicketsParamsDto params)
			throws InstanceNotFoundException, IncorrectCreditCardException,
			AlreadyDeliveredException, SessionAlreadyStartedException {

		cinemaService.deliverTickets(purchaseId, params.getBankCard());
	}
}
