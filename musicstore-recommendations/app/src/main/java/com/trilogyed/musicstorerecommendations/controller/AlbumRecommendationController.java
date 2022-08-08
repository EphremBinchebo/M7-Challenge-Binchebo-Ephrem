package com.trilogyed.musicstorerecommendations.controller;

import com.trilogyed.musicstorerecommendations.model.AlbumRecommendation;
import com.trilogyed.musicstorerecommendations.model.ArtistRecommendation;
import com.trilogyed.musicstorerecommendations.service.AlbumRecommendationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
public class AlbumRecommendationController {

    @Autowired
    AlbumRecommendationService service;

    @GetMapping("/albumRecommendations")
    @ResponseStatus(HttpStatus.OK)
    public List<AlbumRecommendation> getAllRecommendations() {
        return service.getAllAlbumRecommendations();
    }

    @GetMapping("/albumRecommendations/{id}")
    @ResponseStatus(HttpStatus.OK)
    public AlbumRecommendation getAlbumRecommendationById(@PathVariable int id) {
        return service.getAlbumRecommendation(id);
    }

    @PostMapping("/albumRecommendations")
    @ResponseStatus(HttpStatus.CREATED)
    public AlbumRecommendation addAlbumRecommendation(@RequestBody @Valid AlbumRecommendation album) {
        return service.saveAlbumRecommendation(album);
    }

    @PutMapping("/albumRecommendations")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateAlbumRecommendation(@RequestBody @Valid AlbumRecommendation album) {
        service.updateAlbumRecommendation(album);
    }

    @DeleteMapping("/albumRecommendations/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteAlbumRecommendation(@PathVariable int id) {
        service.deleteAlbumRecommendation(id);
    }
}

