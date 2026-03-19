package es.udc.paproject.backend.rest.controllers;

import es.udc.paproject.backend.model.services.CinemaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/catalog")
public class CatalogController {

    @Autowired
    private CinemaService cinemaService;
}
