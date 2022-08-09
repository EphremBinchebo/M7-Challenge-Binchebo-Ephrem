package com.trilogyed.musicstorecatalog.service;

import com.trilogyed.musicstorecatalog.model.Track;
import com.trilogyed.musicstorecatalog.repository.TrackRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;


import java.util.ArrayList;

import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
public class TrackServiceTest {

    private TrackRepository trackRepository;

    private TrackService service;

    @Before
    public void setUp() throws Exception {
        setUpTrackRepository();
        service = new TrackService(trackRepository);
    }

    private void setUpTrackRepository() {

        this.trackRepository = mock(TrackRepository.class);
        Track track = new Track();
        track.setId(1);
        track.setAlbumId(1);
        track.setRuntime(180);
        track.setTitle("Number 1 Hit!");

        Track track2 = new Track();
        track2.setAlbumId(1);
        track2.setRuntime(180);
        track2.setTitle("Number 1 Hit!");

        List tList = new ArrayList<>();
        tList.add(track);

        doReturn(track).when(trackRepository).save(track2);
        doReturn(Optional.of(track)).when(trackRepository).findById(1);
        doReturn(tList).when(trackRepository).findAll();
        doReturn(tList).when(trackRepository).findAllTracksByAlbumId(1);

    }


    @Test
    public void getAllTrackByAlbumIdShouldReturnAListOfTracks() {

        List<Track> actualResult = service.getTrackByAlbumId(1);
        assertEquals(1, actualResult.size());
    }

    @Test
    public void shouldGetAllTracksShouldReturnAListOfTracks() {
        List<Track> actualResult = service.getAllTracks();
        assertEquals(1, actualResult.size());

    }

    @Test
    public void shouldUpdateTrack() {
        Track track = new Track();
        track.setAlbumId(1);
        track.setRuntime(180);
        track.setTitle("Number 1 Hit!");
        track = service.saveTrack(track);
        track.setRuntime(20);
        service.updateTrack(track);
        verify(trackRepository, times(2)).save(any(Track.class));
    }

    @Test(expected = NullPointerException.class)
    public void shouldFailToUpdateWithNullTrack() {
        Track track = null;

        service.updateTrack(track);
    }

    @Test(expected = NullPointerException.class)
    public void shouldFailToUpdateTrackWithBadId() {
        Track Track1 = new Track(1, 10, "Tikure Sew", 120);
        Track1 = service.saveTrack(Track1);
        Track1.setTitle("Ja");
        Track1.setId(Track1.getId() + 1);
        service.updateTrack(Track1);
    }

    @Test
    public void shouldDeleteTrackById() {
        Track track = new Track();
        //track.setId(1);
        track.setAlbumId(1);
        track.setRuntime(180);
        track.setTitle("Number 1 Hit!");

        track = service.saveTrack(track);

        service.deleteTrackById(track.getId());

        verify(trackRepository).deleteById(any(Integer.class));
    }

    @Test
    public void shouldAddAndGetTrack() {
        Track track = new Track();
        track.setAlbumId(1);
        track.setRuntime(180);
        track.setTitle("Number 1 Hit!");
        track = service.saveTrack(track);
        Track actualResult = service.getTrack(track.getId());
        assertEquals(track, actualResult);

    }
}
