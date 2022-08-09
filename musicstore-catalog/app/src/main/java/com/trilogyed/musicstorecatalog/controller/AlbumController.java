package com.trilogyed.musicstorecatalog.controller;

import com.trilogyed.musicstorecatalog.service.AlbumServiceLayer;
import com.trilogyed.musicstorecatalog.viewmodel.AlbumViewModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
public class AlbumController {
    @Autowired
    AlbumServiceLayer service;

    @GetMapping("/albums")
    @ResponseStatus(HttpStatus.OK)
    public List<AlbumViewModel> getAlbums() {
        return service.getAllAlbums();
    }

    @GetMapping("/albums/{id}")
    @ResponseStatus(HttpStatus.OK)
    public AlbumViewModel getAlbumById(@PathVariable int id) {
        return service.getAlbum(id);
    }

    @PostMapping("/albums")
    @ResponseStatus(HttpStatus.CREATED)
    public AlbumViewModel addAlbum(@RequestBody @Valid AlbumViewModel albumViewModel) {
        return service.createAlbum(albumViewModel);
    }

    public AlbumController() {
    }

    @PutMapping("/albums")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateAlbum(@RequestBody @Valid AlbumViewModel albumViewModel) {
        service.updateAlbum(albumViewModel);
    }

    @DeleteMapping("/albums/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteAlbum(@PathVariable int id) {
        service.removeAlbum(id);
    }
}
