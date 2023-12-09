package com.ProjetSOC.geolocalisationservice.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Setter
@Getter
@NoArgsConstructor
public class Localisation {
    @Id
    private Long id;
    private double latitude;
    private double longitude;
    private  String adresse;

}
