package com.ProjetSOC.geolocalisationservice.service;

import com.ProjetSOC.geolocalisationservice.model.Localisation;

import java.util.Optional;

public interface LocalisationService {
    Localisation saveLocalisation(Localisation localisation);
    Optional<Localisation> getLocalisationById(Long id);
    void deleteLocalisation(Long id);
    String getAdresse(double latitude, double longitude);
}
