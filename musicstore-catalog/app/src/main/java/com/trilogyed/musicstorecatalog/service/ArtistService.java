package com.trilogyed.musicstorecatalog.service;

import com.trilogyed.musicstorecatalog.model.Artist;
import com.trilogyed.musicstorecatalog.repository.ArtistRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ArtistService {
    ArtistRepository artistRepository;

    @Autowired
    public ArtistService(ArtistRepository artistRepository) {
        this.artistRepository = artistRepository;
    }

    public Artist saveArtist(Artist artist) {
        return artistRepository.save(artist);
    }

    public Artist getArtist(int id) {
        Optional<Artist> artist = artistRepository.findById(id);
        // return artist.isPresent() ? artist.get() : null;
        if (artist.isPresent()) {
            return artist.get();
        } else {
            return null;
        }
    }

    public List<Artist> getAllArtists() {
        return artistRepository.findAll();
    }

    public void updateArtist(Artist artist) {
        if (this.getArtist(artist.getId()) == null)
            throw new IllegalArgumentException("No such Artist to update.");

        artistRepository.save(artist);
    }

    public void deleteArtist(int id) {
        artistRepository.deleteById(id);
    }

}
