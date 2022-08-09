package com.trilogyed.musicstorerecommendations.repository;

import com.trilogyed.musicstorerecommendations.model.LabelRecommendation;
import org.springframework.beans.factory.annotation.Autowired;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class LabelRecommendationRepositoryTest {
    @Autowired
    LabelRecommendationRepository repo;
    @Before
    public void setUp() throws Exception {
        repo.deleteAll();
    }

    @Test
    public void addAndDeleteLabelRecommendation(){
        LabelRecommendation labelRec = new LabelRecommendation();
        labelRec.setLabelId(1);
        labelRec.setUserId(2);
        labelRec.setLiked(true);
        labelRec = repo.save(labelRec);

        Optional<LabelRecommendation> labelRec1 = repo.findById(labelRec.getId());
        assertEquals(labelRec1.get(), labelRec);

        repo.deleteById(labelRec.getId());
        labelRec1 = repo.findById(labelRec.getId());
        assertFalse(labelRec1.isPresent());
    }

    @Test
    public void getAllLabelRecommendationShouldReturnAllLabelRecommendation(){
        LabelRecommendation labelRec = new LabelRecommendation();
        labelRec.setLabelId(1);
        labelRec.setUserId(2);
        labelRec.setLiked(true);
        labelRec = repo.save(labelRec);

        labelRec = new LabelRecommendation();
        labelRec.setLabelId(12);
        labelRec.setUserId(22);
        labelRec.setLiked(true);
        labelRec = repo.save(labelRec);

        List<LabelRecommendation> artistList = repo.findAll();
        assertEquals(artistList.size(), 2);
    }

    @Test
    public void updateLabelRecommendationShouldUpdateLabelRecommendation(){
        LabelRecommendation labelRec = new LabelRecommendation();
        labelRec.setLabelId(1);
        labelRec.setUserId(2);
        labelRec.setLiked(true);
        labelRec = repo.save(labelRec);

        labelRec.setLabelId(11);
        labelRec.setUserId(22);
        labelRec.setLiked(true);
        labelRec = repo.save(labelRec);

        Optional<LabelRecommendation> labelRec1 = repo.findById(labelRec.getId());
        assertEquals(labelRec1.get(), labelRec);
    }
}