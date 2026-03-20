package es.udc.paproject.backend.rest.controllers;

import static es.udc.paproject.backend.rest.dtos.SessionConversor.toSessionDto;

import es.udc.paproject.backend.model.entities.Purchase;
import es.udc.paproject.backend.model.exceptions.InstanceNotFoundException;
import es.udc.paproject.backend.model.exceptions.InvalidDateException;
import es.udc.paproject.backend.model.exceptions.SessionAlreadyStartedException;
import es.udc.paproject.backend.model.exceptions.NotEnoughSeatsException;
import es.udc.paproject.backend.model.services.CinemaService;
import es.udc.paproject.backend.rest.dtos.SessionDto;
import es.udc.paproject.backend.rest.dtos.MovieConversor;
import es.udc.paproject.backend.rest.dtos.MovieDto;
import es.udc.paproject.backend.rest.dtos.MovieSessionsConversor;
import es.udc.paproject.backend.rest.dtos.MovieSessionsDto;
import es.udc.paproject.backend.rest.dtos.BuyTicketsParamsDto;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/catalog")
public class CatalogController {

    @Autowired
    private CinemaService cinemaService;

    @GetMapping("/movies")
    @Transactional(readOnly = true)
    public List<MovieSessionsDto> getBilboard(
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date)
            throws InvalidDateException {

        LocalDate searchDate;

        if (date != null) {
            searchDate = date;
        } else {
            searchDate = LocalDate.now();
        }

        return MovieSessionsConversor.toMovieSessionsDtos(cinemaService.findCartelera(searchDate));
    }

    @GetMapping("/movies/{id}")
    @Transactional(readOnly = true)
    public MovieDto findMovie(@PathVariable Long id) throws InstanceNotFoundException {
        return MovieConversor.toMovieDto(cinemaService.findMovieById(id));
    }

    @GetMapping("/sessions/{id}")
    @Transactional(readOnly = true)
    public SessionDto getSession(@PathVariable Long id) throws InstanceNotFoundException,SessionAlreadyStartedException {
        return toSessionDto(cinemaService.findSession(id));
    }

    @PostMapping("/sessions/{sessionId}/buyTickets")
    @ResponseStatus(HttpStatus.CREATED)
    public Long buyTickets(@RequestAttribute Long userId, @PathVariable Long sessionId,
                           @Validated @RequestBody BuyTicketsParamsDto params)
            throws InstanceNotFoundException, NotEnoughSeatsException, SessionAlreadyStartedException {

        Purchase purchase = cinemaService.buyTickets(userId, sessionId, params.getNumTickets(), params.getBankCard());

        return purchase.getId();
    }
}
