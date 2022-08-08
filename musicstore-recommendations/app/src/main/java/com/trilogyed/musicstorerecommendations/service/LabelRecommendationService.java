package com.trilogyed.musicstorerecommendations.service;

import com.trilogyed.musicstorerecommendations.model.LabelRecommendation;
import com.trilogyed.musicstorerecommendations.repository.LabelRecommendationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class LabelRecommendationService {
   private LabelRecommendationRepository repo;
     @Autowired
    public LabelRecommendationService(LabelRecommendationRepository repo) {
        this.repo = repo;
    }

    public List<LabelRecommendation> getAllLabelRecommendations() {
        return repo.findAll();
    }

    public LabelRecommendation getLabelRecommendation(int id) {
        Optional<LabelRecommendation> labelRec = repo.findById(id);
        if (labelRec.isPresent()) {
            return labelRec.get();
        } else {
            return null;
        }
    }

    public LabelRecommendation saveLabelRecommendation(LabelRecommendation label) {

         return repo.save(label);
    }

    public void updateLabelRecommendation(LabelRecommendation label) {
        if (this.getLabelRecommendation(label.getId()) == null)
            throw new IllegalArgumentException("No such LabelRecommendation to update.");

         repo.save(label);
    }

    public void deleteLabelRecommendation(int id) {
        repo.deleteById(id);
    }
}
