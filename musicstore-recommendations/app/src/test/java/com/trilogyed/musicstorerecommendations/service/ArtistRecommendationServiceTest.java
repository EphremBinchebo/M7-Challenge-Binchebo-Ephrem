package com.trilogyed.musicstorerecommendations.service;

//import static org.junit.Assert.*;

import com.trilogyed.musicstorerecommendations.model.ArtistRecommendation;
import com.trilogyed.musicstorerecommendations.repository.ArtistRecommendationRepository;
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
public class ArtistRecommendationServiceTest {
    ArtistRecommendationRepository artistRecommendationRepository;

    ArtistRecommendationService service;

    @Before
    public void setUp() throws Exception {
        setUpArtistRecommendationRepositoryMock();
        service = new ArtistRecommendationService(artistRecommendationRepository);
    }

    private void setUpArtistRecommendationRepositoryMock() {
        this.artistRecommendationRepository = mock(ArtistRecommendationRepository.class);


        ArtistRecommendation newArtistRec1 = new ArtistRecommendation(1, 2, true);

        ArtistRecommendation savedArtistRec1 = new ArtistRecommendation(1, 1, 2, true);


        ArtistRecommendation newArtistRec2 = new ArtistRecommendation(11, 22, true);

        ArtistRecommendation savedArtistRec2 = new ArtistRecommendation(5, 11, 22, true);

        List<ArtistRecommendation> artistRecList = Arrays.asList(savedArtistRec1, savedArtistRec2);
        Optional<ArtistRecommendation> findByIdResult = Optional.of(savedArtistRec1);


        doReturn(savedArtistRec1).when(artistRecommendationRepository).save(newArtistRec1);
        doReturn(savedArtistRec2).when(artistRecommendationRepository).save(newArtistRec2);
        doReturn(findByIdResult).when(artistRecommendationRepository).findById(1);
        doReturn(artistRecList).when(artistRecommendationRepository).findAll();
        doReturn(artistRecList).when(artistRecommendationRepository).findAll();
    }

    @Test
    public void shouldCreateArtistRecommendation() {
        ArtistRecommendation artistIn = new ArtistRecommendation(1, 2, true);

        ArtistRecommendation artistOut = new ArtistRecommendation(1, 1, 2, true);
        ArtistRecommendation actualResult = service.saveArtistRecommendation(artistIn);

        assertEquals(artistOut, actualResult);
    }

    @Test
    public void shouldReturnNullForInvalidArtistId() {
        ArtistRecommendation artistRecWithoutArtistId = new ArtistRecommendation(0, 2, true);
        ArtistRecommendation expectedResult = null;
        ArtistRecommendation actualResult = service.saveArtistRecommendation(artistRecWithoutArtistId);

        assertEquals(null, actualResult);
    }

    @Test
    public void shouldReturnNullForInvalidUserId() {
        ArtistRecommendation artistRecWithoutUserId = new ArtistRecommendation(1, 0, true);
        ArtistRecommendation expectedResult = null;
        ArtistRecommendation actualResult = service.saveArtistRecommendation(artistRecWithoutUserId);

        assertEquals(null, actualResult);
    }

    @Test
    public void shouldReturnAllArtistRecommendationList() {
        List<ArtistRecommendation> artistRecList = service.getAllArtistRecommendations();
        assertEquals(2, artistRecList.size());
    }

    @Test
    public void shouldReturnArtistRecommendationById() {
        ArtistRecommendation artistIn = new ArtistRecommendation(1, 2, true);
        artistIn = service.saveArtistRecommendation(artistIn);

        ArtistRecommendation actualResult = service.getArtistRecommendation(artistIn.getId());

        assertEquals(artistIn, actualResult);
    }

    @Test
    public void shouldUpdateArtistRecommendation() {
        ArtistRecommendation artist1 = new ArtistRecommendation(1, 2, true);
        artist1 = service.saveArtistRecommendation(artist1);

        artist1.setArtistId(11);
        artist1.setLiked(false);
        service.updateArtistRecommendation(artist1);
        verify(artistRecommendationRepository, times(2)).save(any(ArtistRecommendation.class));
    }

    @Test
    public void shouldDeleteArtistRecommendation() {
        ArtistRecommendation artist1 = new ArtistRecommendation(1, 2, true);
        artist1 = service.saveArtistRecommendation(artist1);
        service.deleteArtistRecommendation(artist1.getId());
        verify(artistRecommendationRepository).deleteById(artist1.getId());
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldFailToUpdateArtistRecommendationWithBadId() {
        ArtistRecommendation artist1 = new ArtistRecommendation(1, 2, true);
        artist1 = service.saveArtistRecommendation(artist1);

        artist1.setArtistId(1);
        artist1.setLiked(false);
        artist1.setId(artist1.getId() + 1);
        service.updateArtistRecommendation(artist1);
    }

    @Test(expected = NullPointerException.class)
    public void shouldFailToUpdateWithNullArtistRecommendation() {
        ArtistRecommendation artistRec1 = null;
        service.updateArtistRecommendation(artistRec1);
    }
}