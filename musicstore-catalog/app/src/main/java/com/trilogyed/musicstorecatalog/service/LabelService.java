package com.trilogyed.musicstorecatalog.service;

import com.trilogyed.musicstorecatalog.model.Label;
import com.trilogyed.musicstorecatalog.repository.ArtistRepository;
import com.trilogyed.musicstorecatalog.repository.LabelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class LabelService {

    LabelRepository labelRepository;

    @Autowired
    public LabelService(LabelRepository labelRepository) {
        this.labelRepository = labelRepository;
    }

    public Label saveLabel(Label label) {
        return labelRepository.save(label);
    }

    public Label getLabel(int id) {
        Optional<Label> returnVal = labelRepository.findById(id);
        if (returnVal.isPresent()) {
            return returnVal.get();
        } else {
            return null;
        }
    }

    public List<Label> getLabels() {
        return labelRepository.findAll();
    }

    public void updateLabel(Label label) {
        if (this.getLabel(label.getId()) == null)
            throw new IllegalArgumentException("No such Label to update.");

        labelRepository.save(label);
    }

    public void deleteLabel(int id) {
        labelRepository.deleteById(id);
    }
}
