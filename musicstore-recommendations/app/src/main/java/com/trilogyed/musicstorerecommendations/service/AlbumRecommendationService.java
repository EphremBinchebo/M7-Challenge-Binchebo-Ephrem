package com.trilogyed.musicstorerecommendations.service;

import com.trilogyed.musicstorerecommendations.model.AlbumRecommendation;
import com.trilogyed.musicstorerecommendations.repository.AlbumRecommendationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AlbumRecommendationService {
    private AlbumRecommendationRepository repo;
    @Autowired
    public AlbumRecommendationService(AlbumRecommendationRepository repo) {
        this.repo = repo;
    }
    public AlbumRecommendation getAlbumRecommendation(int id) {
        Optional<AlbumRecommendation> albumRec = repo.findById(id);
        if (albumRec.isPresent()) {
            return albumRec.get();
        } else {
            return null;
        }
    }

    public AlbumRecommendation saveAlbumRecommendation(AlbumRecommendation album) {

        return repo.save(album);
    }

    public void updateAlbumRecommendation(AlbumRecommendation album) {
        if (this.getAlbumRecommendation(album.getId()) == null)
            throw new IllegalArgumentException("No such AlbumRecommendation to update.");
       repo .save(album);
    }

    public void deleteAlbumRecommendation(int id) {
      repo.deleteById(id);
    }

    public List<AlbumRecommendation> getAllAlbumRecommendations() {
        return repo.findAll();
    }
}
