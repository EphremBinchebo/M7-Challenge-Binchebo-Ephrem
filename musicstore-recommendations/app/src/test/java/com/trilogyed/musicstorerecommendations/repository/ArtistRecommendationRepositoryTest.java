package com.trilogyed.musicstorerecommendations.repository;


import com.trilogyed.musicstorerecommendations.model.ArtistRecommendation;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.List;
import java.util.Optional;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.*;
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class ArtistRecommendationRepositoryTest {
    @Autowired
    ArtistRecommendationRepository repo;
    @Before
    public void setUp() throws Exception {
        repo.deleteAll();
    }

    @Test
    public void addAndDeleteArtistRecommendation(){
        ArtistRecommendation artistRec = new ArtistRecommendation();
        artistRec.setArtistId(1);
        artistRec.setUserId(12);
        artistRec.setLiked(true);
        artistRec = repo.save(artistRec);

        Optional<ArtistRecommendation> artistRec1 = repo.findById(artistRec.getId());
        assertEquals(artistRec1.get(), artistRec);

        repo.deleteById(artistRec.getId());
        artistRec1 = repo.findById(artistRec.getId());
        assertFalse(artistRec1.isPresent());
    }

    @Test
    public void getAllArtistRecommendationsShouldGetAllRecommendations(){
        ArtistRecommendation artistRec = new ArtistRecommendation();
        artistRec.setArtistId(1);
        artistRec.setUserId(12);
        artistRec.setLiked(true);
        artistRec = repo.save(artistRec);

        artistRec = new ArtistRecommendation();
        artistRec.setArtistId(2);
        artistRec.setUserId(15);
        artistRec.setLiked(true);
        artistRec = repo.save(artistRec);

        List<ArtistRecommendation> artistList = repo.findAll();
        assertEquals(artistList.size(), 2);
    }

    @Test
    public void updateArtistRecommendationShouldUpdateArtistRecommendation(){
        ArtistRecommendation artistRec = new ArtistRecommendation();
        artistRec.setArtistId(1);
        artistRec.setUserId(12);
        artistRec.setLiked(true);
        artistRec = repo.save(artistRec);

        artistRec.setArtistId(33);
        artistRec.setUserId(15);
        artistRec.setLiked(false);
        artistRec = repo.save(artistRec);

        Optional<ArtistRecommendation> artist1 = repo.findById(artistRec.getId());
        assertEquals(artist1.get(), artistRec);
    }
}