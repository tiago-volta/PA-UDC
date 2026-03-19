package es.udc.paproject.backend.rest.controllers;

import es.udc.paproject.backend.model.exceptions.InvalidDateException;
import es.udc.paproject.backend.model.services.CinemaService;
import es.udc.paproject.backend.rest.dtos.MovieSessionsConversor;
import es.udc.paproject.backend.rest.dtos.MovieSessionsDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
}
