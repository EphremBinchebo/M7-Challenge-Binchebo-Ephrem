package com.trilogyed.musicstorerecommendations.service;
import com.trilogyed.musicstorerecommendations.model.TrackRecommendation;
import com.trilogyed.musicstorerecommendations.repository.TrackRecommendationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TrackRecommendationService {
    private TrackRecommendationRepository repo;
    @Autowired
    public TrackRecommendationService(TrackRecommendationRepository repo) {
        this.repo = repo;
    }

    public List<TrackRecommendation> getAllTrackRecommendations() {
        return repo.findAll();
    }

    public TrackRecommendation getTrackRecommendation(int id) {
        Optional<TrackRecommendation> trackRec = repo.findById(id);
        if (trackRec.isPresent()) {
            return trackRec.get();
        } else {
            return null;
        }
    }

    public TrackRecommendation saveTrackRecommendation(TrackRecommendation track) {

        return repo.save(track);   }

    public void updateTrackRecommendation(TrackRecommendation track) {
        if (this.getTrackRecommendation(track.getId()) == null)
            throw new IllegalArgumentException("No such TrackRecommendation to update.");
        repo.save(track);
    }

    public void deleteTrackRecommendation(int id) {
        repo.deleteById(id);
    }
}
