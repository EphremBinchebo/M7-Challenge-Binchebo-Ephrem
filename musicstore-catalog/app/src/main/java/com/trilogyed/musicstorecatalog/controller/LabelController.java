package com.trilogyed.musicstorecatalog.controller;

import com.trilogyed.musicstorecatalog.model.Label;
import com.trilogyed.musicstorecatalog.service.LabelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
public class LabelController {

    @Autowired
    LabelService service;

    @GetMapping("/labels")
    public List<Label> getLabels() {
        return service.getLabels();
    }

    @GetMapping("/labels/{id}")
    public Label getLabelById(@PathVariable int id) {
        return service.getLabel(id);
    }

    @PostMapping("/labels")
    @ResponseStatus(HttpStatus.CREATED)
    public Label addLabel(@RequestBody @Valid Label label) {
        return service.saveLabel(label);
    }

    @PutMapping("/labels")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateLabel(@RequestBody @Valid Label label) {
        service.updateLabel(label);
    }

    @DeleteMapping("/labels/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteLabel(@PathVariable int id) {
        service.deleteLabel(id);
    }
}


