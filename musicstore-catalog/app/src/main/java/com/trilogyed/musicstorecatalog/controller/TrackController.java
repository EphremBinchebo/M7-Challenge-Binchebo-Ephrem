package com.trilogyed.musicstorecatalog.controller;

import com.trilogyed.musicstorecatalog.model.Track;
import com.trilogyed.musicstorecatalog.service.TrackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
public class TrackController {
    @Autowired
    TrackService service;

    @GetMapping("/tracks")
    @ResponseStatus(HttpStatus.OK)
    public List<Track> getAllTracks() {
        return service.getAllTracks();
    }

    @GetMapping("/tracks/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Track getTrack(@PathVariable int id) {
        return service.getTrack(id);
    }

    @GetMapping("/tracks/albumId/{albumId}")
    @ResponseStatus(HttpStatus.OK)
    public List<Track> getTrackByAlbumId(@PathVariable int albumId) {
        return service.getTrackByAlbumId(albumId);
    }

    @PostMapping("/tracks")
    @ResponseStatus(HttpStatus.CREATED)
    public Track addTrack(@RequestBody @Valid Track track) {
        return service.saveTrack(track);
    }

    @PutMapping("/tracks")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateTrack(@RequestBody @Valid Track track) {
        service.updateTrack(track);
    }

    @DeleteMapping("/tracks/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteTrack(@PathVariable int id) {
        service.deleteTrackById(id);
    }
}
