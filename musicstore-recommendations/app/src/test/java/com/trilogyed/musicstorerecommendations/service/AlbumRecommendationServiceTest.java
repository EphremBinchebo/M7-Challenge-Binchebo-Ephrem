package com.trilogyed.musicstorerecommendations.service;

import com.trilogyed.musicstorerecommendations.model.AlbumRecommendation;
import com.trilogyed.musicstorerecommendations.repository.AlbumRecommendationRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
public class AlbumRecommendationServiceTest {
    AlbumRecommendationRepository albumRecommendationRepository;

    AlbumRecommendationService service;

    @Before
    public void setUp() throws Exception {
        setUpAlbumRecommendationRepositoryMock();
        service = new AlbumRecommendationService(albumRecommendationRepository);
    }

    private void setUpAlbumRecommendationRepositoryMock() {
        this.albumRecommendationRepository = mock(AlbumRecommendationRepository.class);


        AlbumRecommendation newAlbumRec1 = new AlbumRecommendation(1, 2, true);

        AlbumRecommendation savedAlbumRec1 = new AlbumRecommendation(1, 1, 2, true);


        AlbumRecommendation newAlbumRec2 = new AlbumRecommendation(11, 22, true);

        AlbumRecommendation savedAlbumRec2 = new AlbumRecommendation(5, 11, 22, true);

        List<AlbumRecommendation> albumRecList = Arrays.asList(savedAlbumRec1, savedAlbumRec2);
        Optional<AlbumRecommendation> findByIdResult = Optional.of(savedAlbumRec1);


        doReturn(savedAlbumRec1).when(albumRecommendationRepository).save(newAlbumRec1);
        doReturn(savedAlbumRec2).when(albumRecommendationRepository).save(newAlbumRec2);
        doReturn(findByIdResult).when(albumRecommendationRepository).findById(1);
        doReturn(albumRecList).when(albumRecommendationRepository).findAll();
    }

    @Test
    public void shouldCreateAlbumRecommendation() {
        AlbumRecommendation albumIn = new AlbumRecommendation(1, 2, true);

        AlbumRecommendation albumOut = new AlbumRecommendation(1, 1, 2, true);
        AlbumRecommendation actualResult = service.saveAlbumRecommendation(albumIn);

        assertEquals(albumOut, actualResult);
    }

    @Test
    public void shouldReturnNullForInvalidAlbumId() {
        AlbumRecommendation albumRecWithoutAlbumId = new AlbumRecommendation(0, 2, true);
        AlbumRecommendation expectedResult = null;
        AlbumRecommendation actualResult = service.saveAlbumRecommendation(albumRecWithoutAlbumId);

        assertEquals(null, actualResult);

    }

    @Test
    public void shouldReturnNullForInvalidUserId() {
        AlbumRecommendation albumRecWithoutAlbumId = new AlbumRecommendation(1, 0, true);
        AlbumRecommendation expectedResult = null;
        AlbumRecommendation actualResult = service.saveAlbumRecommendation(albumRecWithoutAlbumId);

        assertEquals(null, actualResult);

    }


    @Test
    public void shouldReturnAllAlbumRecommendationList() {
        List<AlbumRecommendation> albumRecList = service.getAllAlbumRecommendations();
        assertEquals(2, albumRecList.size());
    }

    @Test
    public void shouldReturnAlbumRecommendationById() {
        AlbumRecommendation albumIn = new AlbumRecommendation(1, 2, true);
        albumIn = service.saveAlbumRecommendation(albumIn);

        AlbumRecommendation actualResult = service.getAlbumRecommendation(albumIn.getId());

        assertEquals(albumIn, actualResult);
    }

    @Test
    public void shouldUpdateAlbumRecommendation() {
        AlbumRecommendation album1 = new AlbumRecommendation(1, 2, true);
        album1 = service.saveAlbumRecommendation(album1);

        album1.setAlbumId(11);
        album1.setLiked(false);
        service.updateAlbumRecommendation(album1);
        verify(albumRecommendationRepository, times(2)).save(any(AlbumRecommendation.class));
    }

    @Test
    public void shouldDeleteAlbumRecommendation() {
        AlbumRecommendation album1 = new AlbumRecommendation(1, 2, true);
        album1 = service.saveAlbumRecommendation(album1);
        service.deleteAlbumRecommendation(album1.getId());
        verify(albumRecommendationRepository).deleteById(album1.getId());
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldFailToUpdateAlbumRecommendationWithBadId() {
        AlbumRecommendation album1 = new AlbumRecommendation(1, 2, true);
        album1 = service.saveAlbumRecommendation(album1);

        album1.setAlbumId(1);
        album1.setLiked(false);
        album1.setId(album1.getId() + 1);
        service.updateAlbumRecommendation(album1);
    }

    @Test(expected = NullPointerException.class)
      public void shouldFailToUpdateWithNullAlbumRecommendation() {
        AlbumRecommendation albumRec1 = null;
        service.updateAlbumRecommendation(albumRec1);
    }
    }