package com.ProjetSOC.geolocalisationservice.service;

import com.ProjetSOC.geolocalisationservice.model.Localisation;
import com.ProjetSOC.geolocalisationservice.repository.LocalisationRepository;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;

@Service
public class LocalisationServiceImpl implements LocalisationService{
    private static final Logger logger = LoggerFactory.getLogger(LocalisationServiceImpl.class);
    @Value("${osm.api.url}")
    private String osmApiUrl;
    @Autowired
    LocalisationRepository localisationRepository;

    @Override
    public Localisation saveLocalisation(Localisation localisation) {
        logger.info("Début de la méthode saveLocalisation");

        // Obtenez la forme normalisée de l'adresse
        String adresse = getAdresse(localisation.getLatitude(), localisation.getLongitude());

        // Extraire la ville et la rue de display_name
        String villeEtRue = extractVilleEtRueFromDisplayName(adresse);

        localisation.setAdresse(villeEtRue);

        // Utilisation du repository pour sauvegarder en base de données
        Localisation savedLocalisation = localisationRepository.save(localisation);

        logger.info("Localisation sauvegardée en base de données avec ID : {}", savedLocalisation.getId());
        logger.info("Fin de la méthode saveLocalisation");

        return savedLocalisation;

    }
    private String extractVilleEtRueFromDisplayName(String displayName) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode rootNode = objectMapper.readTree(displayName);

            if (rootNode.isArray() && rootNode.size() > 0) {
                JsonNode premierElement = rootNode.get(0);
                JsonNode villeEtRueNode = premierElement.get("display_name");

                if (villeEtRueNode != null) {
                    return villeEtRueNode.asText();
                }
            }
        } catch (Exception e) {
            logger.error("Erreur lors de l'extraction de la ville et de la rue : {}", e.getMessage());
        }

        // En cas d'erreur, retournez l'adresse complète
        return displayName;
    }


    @Override
    public Optional<Localisation> getLocalisationById(Long id) {
        return localisationRepository.findById(id);
    }

    @Override
    public void deleteLocalisation(Long id) {
        localisationRepository.deleteById(id);
    }

    @Override
    public String getAdresse(double latitude, double longitude) {
        try {
            String apiUrl = osmApiUrl + "/search?format=json&q=" + latitude + "," + longitude;

            RestTemplate restTemplate = new RestTemplate();
            return restTemplate.getForObject(apiUrl, String.class);
        } catch (Exception e) {
            // Gérer les exceptions (ex: RestClientException)
            e.printStackTrace();
            return "Erreur lors de la récupération de l'adresse formatée.";
        }
    }
    }

