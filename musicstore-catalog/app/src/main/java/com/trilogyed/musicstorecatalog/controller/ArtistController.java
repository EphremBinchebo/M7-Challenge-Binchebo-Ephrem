package com.trilogyed.musicstorecatalog.controller;

import com.trilogyed.musicstorecatalog.model.Artist;
import com.trilogyed.musicstorecatalog.service.ArtistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
public class ArtistController {
    @Autowired
    ArtistService service;

    @GetMapping("/artists")
    @ResponseStatus(HttpStatus.OK)
    public List<Artist> getArtists() {
        return service.getAllArtists();
    }

    @GetMapping("/artists/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Artist getArtistById(@PathVariable int id) {
        return service.getArtist(id);
    }

    @PostMapping("/artists")
    @ResponseStatus(HttpStatus.CREATED)
    public Artist addArtist(@RequestBody @Valid Artist artist) {
        return service.saveArtist(artist);
    }

    @PutMapping("/artists")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateArtist(@RequestBody @Valid Artist artist) {
        service.updateArtist(artist);
    }

    @DeleteMapping("/artists/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteArtist(@PathVariable int id) {
        service.deleteArtist(id);
    }
}



