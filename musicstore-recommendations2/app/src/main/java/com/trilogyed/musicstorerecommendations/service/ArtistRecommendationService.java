package com.trilogyed.musicstorerecommendations.service;

import com.trilogyed.musicstorerecommendations.model.ArtistRecommendation;
import com.trilogyed.musicstorerecommendations.repository.ArtistRecommendationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ArtistRecommendationService {
     private ArtistRecommendationRepository repo;
    @Autowired
    public ArtistRecommendationService(ArtistRecommendationRepository repo) {
        this.repo = repo;
    }

    public List<ArtistRecommendation> getAllArtistRecommendations() {
        return repo.findAll();
    }

    public ArtistRecommendation getArtistRecommendation(int id) {
        Optional<ArtistRecommendation> artistRec = repo.findById(id);
        if (artistRec.isPresent()) {
            return artistRec.get();
        } else {
            return null;
        }
    }

    public ArtistRecommendation saveArtistRecommendation(ArtistRecommendation artist) {

        return repo.save(artist);
    }

    public void updateArtistRecommendation(ArtistRecommendation artist) {
        if (this.getArtistRecommendation(artist.getId()) == null)
            throw new IllegalArgumentException("No such ArtistRecommendation to update.");

        repo.save(artist);
    }

    public void deleteArtistRecommendation(int id) {
        repo.deleteById(id);
    }
}
