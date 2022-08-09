package com.trilogyed.musicstorerecommendations.controller;

import com.trilogyed.musicstorerecommendations.model.ArtistRecommendation;
import com.trilogyed.musicstorerecommendations.model.LabelRecommendation;
import com.trilogyed.musicstorerecommendations.service.ArtistRecommendationService;
import com.trilogyed.musicstorerecommendations.service.LabelRecommendationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
@RestController
public class LabelRecommendationController {

    @Autowired
    LabelRecommendationService service;

    @GetMapping("/labelRecommendations")
    @ResponseStatus(HttpStatus.OK)
    public List<LabelRecommendation> getLabelRecommendations() {
        return service.getAllLabelRecommendations();
    }

    @GetMapping("/labelRecommendations/{id}")
    @ResponseStatus(HttpStatus.OK)
    public LabelRecommendation getAllLabelRecommendationById(@PathVariable int id) {
        return service.getLabelRecommendation(id);
    }

    @PostMapping("/labelRecommendations")
    @ResponseStatus(HttpStatus.CREATED)
    public LabelRecommendation addLabelRecommendation(@RequestBody @Valid LabelRecommendation label) {
        return service.saveLabelRecommendation(label);
    }

    @PutMapping("/labelRecommendations")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateLabelRecommendation(@RequestBody @Valid LabelRecommendation label) {
        service.updateLabelRecommendation(label);
    }

    @DeleteMapping("/labelRecommendations/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteLabelRecommendation(@PathVariable int id) {
        service.deleteLabelRecommendation(id);
    }
}
