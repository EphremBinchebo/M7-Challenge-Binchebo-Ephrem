package com.trilogyed.musicstorecatalog.repository;

import com.trilogyed.musicstorecatalog.model.Label;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class LabelRepositoryTest {
    @Autowired
    LabelRepository labelRepository;

    @Before
    public void setUp() throws Exception {
        labelRepository.deleteAll();
    }

    @Test
    public void addAndDeleteLabel() {
        Label label = new Label();
        label.setName("ABC");
        label.setWebsite("www.abc.com");
        label = labelRepository.save(label);

        Optional<Label> label1 = labelRepository.findById(label.getId());
        assertEquals(label1.get(), label);
        labelRepository.deleteById(label.getId());
        label1 = labelRepository.findById(label.getId());
        assertFalse(label1.isPresent());
    }

    @Test
    public void getAllLabels() {
        Label label = new Label();
        label.setName("Amharic Music");
        label.setWebsite("www.amharicmusic.com");
        label = labelRepository.save(label);

        label = new Label();
        label.setName("Oromic Music");
        label.setWebsite("www.oromicmusic.com");
        label = labelRepository.save(label);

        List<Label> labelList = labelRepository.findAll();
        assertEquals(labelList.size(), 2);
    }

    @Test
    public void updateLabel() {
        Label label = new Label();
        label.setName("Amharic Music");
        label.setWebsite("www.amharicmusic.com");
        label = labelRepository.save(label);

        label.setName("Tigrian Music");
        label.setWebsite("www.tigrianmusic.com");
        labelRepository.save(label);
        Optional<Label> label1 = labelRepository.findById(label.getId());
        assertEquals(label1.get(), label);
    }
}