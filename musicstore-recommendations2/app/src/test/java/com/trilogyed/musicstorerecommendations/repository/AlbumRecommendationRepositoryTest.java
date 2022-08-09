package com.trilogyed.musicstorerecommendations.repository;

//import static org.junit.Assert.*;


import com.trilogyed.musicstorerecommendations.model.AlbumRecommendation;
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
public class AlbumRecommendationRepositoryTest {
    @Autowired
    AlbumRecommendationRepository repo;
    @Before
    public void setUp() throws Exception {
        repo.deleteAll();
    }

    @Test
    public void addAndDeleteAlbumRecommendation(){
        AlbumRecommendation albumRec = new AlbumRecommendation();
        albumRec.setAlbumId(1);
        albumRec.setUserId(12);
        albumRec.setLiked(true);
        albumRec = repo.save(albumRec);

        Optional<AlbumRecommendation> albumRec1 = repo.findById(albumRec.getId());
        assertEquals(albumRec1.get(), albumRec);

        repo.deleteById(albumRec.getId());
        albumRec1 = repo.findById(albumRec.getId());
        assertFalse(albumRec1.isPresent());
    }

    @Test
    public void getAllAlbumRecommendationShouldReturnAllAlbumRecommendations(){
        AlbumRecommendation albumRec = new AlbumRecommendation();
        albumRec.setAlbumId(1);
        albumRec.setUserId(12);
        albumRec.setLiked(true);
        albumRec = repo.save(albumRec);

        albumRec = new AlbumRecommendation();
        albumRec.setAlbumId(2);
        albumRec.setUserId(22);
        albumRec.setLiked(false);
        albumRec = repo.save(albumRec);


        List<AlbumRecommendation> albumList = repo.findAll();
        assertEquals(albumList.size(), 2);
    }

    @Test
    public void updateAlbumRecommendationShouldUpdateAlbumRecommendation(){
        AlbumRecommendation albumRec = new AlbumRecommendation();
        albumRec.setAlbumId(1);
        albumRec.setUserId(12);
        albumRec.setLiked(true);
        albumRec = repo.save(albumRec);

        albumRec.setAlbumId(12);
        albumRec.setUserId(14);
        albumRec.setLiked(true);
        albumRec = repo.save(albumRec);

        Optional<AlbumRecommendation> albumRec1 = repo.findById(albumRec.getId());
        assertEquals(albumRec1.get(), albumRec);
    }
}