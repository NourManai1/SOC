package com.ProjetSOC.geolocalisationservice.repository;

import com.ProjetSOC.geolocalisationservice.model.Localisation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LocalisationRepository extends JpaRepository<Localisation,Long> {
}
