package com.trilogyed.musicstorerecommendations.controller;

import com.trilogyed.musicstorerecommendations.model.ArtistRecommendation;
import com.trilogyed.musicstorerecommendations.service.ArtistRecommendationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
@RestController
public class ArtistRecommendationController {

        @Autowired
        ArtistRecommendationService service;

        @GetMapping("/artistRecommendations")
        @ResponseStatus(HttpStatus.OK)
        public List<ArtistRecommendation> getArtistRecommendations() {
            return service.getAllArtistRecommendations();
        }

        @GetMapping("/artistRecommendations/{id}")
        @ResponseStatus(HttpStatus.OK)
        public ArtistRecommendation getAllArtistRecommendationById(@PathVariable int id) {
            return service.getArtistRecommendation(id);
        }

        @PostMapping("/artistRecommendations")
        @ResponseStatus(HttpStatus.CREATED)
        public ArtistRecommendation addArtistRecommendation(@RequestBody @Valid ArtistRecommendation artist) {
            return service.saveArtistRecommendation(artist);
        }

        @PutMapping("/artistRecommendations")
        @ResponseStatus(HttpStatus.NO_CONTENT)
        public void updateArtistRecommendation(@RequestBody @Valid ArtistRecommendation artist) {
            service.updateArtistRecommendation(artist);
        }

        @DeleteMapping("/artistRecommendations/{id}")
        @ResponseStatus(HttpStatus.NO_CONTENT)
        public void deleteArtistRecommendation(@PathVariable int id) {
            service.deleteArtistRecommendation(id);
        }
    }


