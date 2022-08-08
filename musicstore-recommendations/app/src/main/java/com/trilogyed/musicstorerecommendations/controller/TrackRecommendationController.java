package com.trilogyed.musicstorerecommendations.controller;


import com.trilogyed.musicstorerecommendations.model.AlbumRecommendation;
import com.trilogyed.musicstorerecommendations.model.TrackRecommendation;
import com.trilogyed.musicstorerecommendations.service.ArtistRecommendationService;
import com.trilogyed.musicstorerecommendations.service.TrackRecommendationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
public class TrackRecommendationController {

    @Autowired
    TrackRecommendationService service;

    @GetMapping("/trackRecommendations")
    @ResponseStatus(HttpStatus.OK)
    public List<TrackRecommendation> getAllTrackRecommendations() {
        return service.getAllTrackRecommendations();
    }

    @GetMapping("/trackRecommendations/{id}")
    @ResponseStatus(HttpStatus.OK)
    public TrackRecommendation getTrackRecommendationById(@PathVariable int id) {
        return service.getTrackRecommendation(id);
    }

    @PostMapping("/trackRecommendations")
    @ResponseStatus(HttpStatus.CREATED)
    public TrackRecommendation addTrackRecommendation(@RequestBody @Valid TrackRecommendation track) {
        return service.saveTrackRecommendation(track);
    }

    @PutMapping("/trackRecommendations")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateTrackRecommendation(@RequestBody @Valid TrackRecommendation track) {
        service.updateTrackRecommendation(track);
    }

    @DeleteMapping("/trackRecommendations/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteTrackRecommendation(@PathVariable int id) {
        service.deleteTrackRecommendation(id);
    }
}
