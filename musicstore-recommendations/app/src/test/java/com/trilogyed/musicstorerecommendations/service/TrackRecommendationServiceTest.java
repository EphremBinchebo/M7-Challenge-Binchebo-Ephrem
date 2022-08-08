package com.trilogyed.musicstorerecommendations.service;

import com.trilogyed.musicstorerecommendations.model.TrackRecommendation;
import com.trilogyed.musicstorerecommendations.repository.TrackRecommendationRepository;
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
public class TrackRecommendationServiceTest {
    TrackRecommendationRepository trackRecommendationRepository;

    TrackRecommendationService service;

    @Before
    public void setUp() throws Exception {
        setUpTrackRecommendationRepositoryMock();
        service = new TrackRecommendationService(trackRecommendationRepository);
    }

    private void setUpTrackRecommendationRepositoryMock() {
        this.trackRecommendationRepository = mock(TrackRecommendationRepository.class);


        TrackRecommendation newTrackRec1 = new TrackRecommendation(1, 2, true);

        TrackRecommendation savedTrackRec1 = new TrackRecommendation(1, 1, 2, true);


        TrackRecommendation newTrackRec2 = new TrackRecommendation(11, 22, true);

        TrackRecommendation savedTrackRec2 = new TrackRecommendation(5, 11, 22, true);

        List<TrackRecommendation> trackRecList = Arrays.asList(savedTrackRec1, savedTrackRec2);
        Optional<TrackRecommendation> findByIdResult = Optional.of(savedTrackRec1);


        doReturn(savedTrackRec1).when(trackRecommendationRepository).save(newTrackRec1);
        doReturn(savedTrackRec2).when(trackRecommendationRepository).save(newTrackRec2);
        doReturn(findByIdResult).when(trackRecommendationRepository).findById(1);
        doReturn(trackRecList).when(trackRecommendationRepository).findAll();
    }

    @Test
    public void shouldCreateTrackRecommendation() {
        TrackRecommendation trackIn = new TrackRecommendation(1, 2, true);

        TrackRecommendation trackOut = new TrackRecommendation(1, 1, 2, true);
        TrackRecommendation actualResult = service.saveTrackRecommendation(trackIn);

        assertEquals(trackOut, actualResult);
    }

    @Test
    public void shouldReturnNullForInvalidTrackId() {
        TrackRecommendation trackRecWithoutTrackId = new TrackRecommendation(0, 2, true);
        TrackRecommendation expectedResult = null;
        TrackRecommendation actualResult = service.saveTrackRecommendation(trackRecWithoutTrackId);

        assertEquals(null, actualResult);

    }

    @Test
    public void shouldReturnNullForInvalidUserId() {
        TrackRecommendation trackRecWithoutUserId = new TrackRecommendation(3, 0, true);
        TrackRecommendation expectedResult = null;
        TrackRecommendation actualResult = service.saveTrackRecommendation(trackRecWithoutUserId);

        assertEquals(null, actualResult);

    }
    @Test
    public void shouldReturnAllTrackRecommendationList() {
        List<TrackRecommendation> trackRecList = service.getAllTrackRecommendations();
        assertEquals(2, trackRecList.size());
    }

    @Test
    public void shouldReturnTrackRecommendationById() {
        TrackRecommendation trackIn = new TrackRecommendation(1, 2, true);
        trackIn = service.saveTrackRecommendation(trackIn);

        TrackRecommendation actualResult = service.getTrackRecommendation(trackIn.getId());

        assertEquals(trackIn, actualResult);
    }

    @Test
    public void shouldUpdateTrackRecommendation() {
        TrackRecommendation track1 = new TrackRecommendation(1, 2, true);
        track1 = service.saveTrackRecommendation(track1);

        track1.setTrackId(11);
        track1.setLiked(false);
        service.updateTrackRecommendation(track1);
        verify(trackRecommendationRepository, times(2)).save(any(TrackRecommendation.class));
    }

    @Test
    public void shouldDeleteTrackRecommendation() {
        TrackRecommendation track1 = new TrackRecommendation(1, 2, true);
        track1 = service.saveTrackRecommendation(track1);
        service.deleteTrackRecommendation(track1.getId());
        verify(trackRecommendationRepository).deleteById(track1.getId());
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldFailToUpdateTrackRecommendationWithBadId() {
        TrackRecommendation track1 = new TrackRecommendation(1, 2, true);
        track1 = service.saveTrackRecommendation(track1);

        track1.setTrackId(1);
        track1.setLiked(false);
        track1.setId(track1.getId() + 1);
        service.updateTrackRecommendation(track1);
    }

    @Test(expected = NullPointerException.class)
    public void shouldFailToUpdateWithNullTrackRecommendation() {
        TrackRecommendation trackRec1 = null;
        service.updateTrackRecommendation(trackRec1);
    }
}