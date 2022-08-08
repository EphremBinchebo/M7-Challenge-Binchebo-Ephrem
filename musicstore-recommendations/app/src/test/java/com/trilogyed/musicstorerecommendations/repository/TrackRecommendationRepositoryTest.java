package com.trilogyed.musicstorerecommendations.repository;

import static org.junit.Assert.*;
import com.trilogyed.musicstorerecommendations.model.TrackRecommendation;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.List;
import java.util.Optional;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class TrackRecommendationRepositoryTest {
    @Autowired
    TrackRecommendationRepository repo;
    @Before
    public void setUp() throws Exception {
        repo.deleteAll();
    }

    @Test
    public void addAndDeleteTrackRecommendation(){
        TrackRecommendation trackRec = new TrackRecommendation();
        trackRec.setTrackId(1);
        trackRec.setUserId(12);
        trackRec.setLiked(true);
        trackRec = repo.save(trackRec);

        Optional<TrackRecommendation> trackRec1 = repo.findById(trackRec.getId());
        assertEquals(trackRec1.get(), trackRec);

        repo.deleteById(trackRec.getId());
        trackRec1 = repo.findById(trackRec.getId());
        assertFalse(trackRec1.isPresent());
    }

    @Test
    public void getAllTrackRecommendationsShouldReturnListOfTrackRecommendations(){
        TrackRecommendation trackRec = new TrackRecommendation();
        trackRec.setTrackId(1);
        trackRec.setUserId(12);
        trackRec.setLiked(true);
        trackRec = repo.save(trackRec);

        trackRec = new TrackRecommendation();
        trackRec.setTrackId(12);
        trackRec.setUserId(14);
        trackRec.setLiked(true);
        trackRec = repo.save(trackRec);


        List<TrackRecommendation> trackList = repo.findAll();
        assertEquals(trackList.size(), 2);
    }

    @Test
    public void updateTrackRecommendationsShouldUpdateTrackRecommendation(){
        TrackRecommendation trackRec = new TrackRecommendation();
        trackRec.setTrackId(1);
        trackRec.setUserId(12);
        trackRec.setLiked(true);
        trackRec = repo.save(trackRec);


        trackRec.setTrackId(11);
        trackRec.setUserId(18);
        trackRec.setLiked(true);
        trackRec = repo.save(trackRec);

        Optional<TrackRecommendation> track1 = repo.findById(trackRec.getId());
        assertEquals(track1.get(), trackRec);
    }
}