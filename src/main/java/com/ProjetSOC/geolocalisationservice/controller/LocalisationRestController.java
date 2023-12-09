package com.ProjetSOC.geolocalisationservice.controller;

import com.ProjetSOC.geolocalisationservice.model.Localisation;
import com.ProjetSOC.geolocalisationservice.service.LocalisationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;

@RestController
@CrossOrigin("*")
@RequestMapping(value="/localisation")
public class LocalisationRestController {
    @Autowired
    private LocalisationService localisationService;
    private static final Logger logger = LoggerFactory.getLogger(LocalisationRestController.class);
    @PostMapping
    public Localisation saveLocalisation(@RequestBody Localisation localisation) {
        logger.info("Avant l'ajout des données à la base de données");
        Localisation savedLocalisation = localisationService.saveLocalisation(localisation);
        logger.info("Après l'ajout des données à la base de données");
        return savedLocalisation;

    }

    @GetMapping("/{id}")
    public Optional<Localisation> getLocalisationById(@PathVariable("id") Long id){

        Optional<Localisation> localisation = localisationService.getLocalisationById(id);
        return localisation;
    }

    @DeleteMapping("/{id}")
    public void deleteLocalisation(@PathVariable Long id) {
        localisationService.deleteLocalisation(id);
    }
}
