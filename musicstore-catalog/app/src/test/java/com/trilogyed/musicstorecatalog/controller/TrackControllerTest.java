package com.trilogyed.musicstorecatalog.controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.trilogyed.musicstorecatalog.model.Track;
import com.trilogyed.musicstorecatalog.service.TrackService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;
import java.util.List;

@RunWith(SpringRunner.class)
@WebMvcTest(TrackController.class)
public class TrackControllerTest {
    @MockBean
    private TrackService service;
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper mapper = new ObjectMapper();

    @Before
    public void setUp() throws Exception {
        setUpTrackServiceMock();
    }

    private void setUpTrackServiceMock() {
        Track track = new Track();
        track.setId(1);
        track.setAlbumId(1);
        track.setRuntime(180);
        track.setTitle("Number 1 Hit!");

        Track track2 = new Track();
        track2.setAlbumId(1);
        track2.setRuntime(180);
        track2.setTitle("Number 1 Hit!");

        List<Track> trackList = Arrays.asList(track);
        doReturn(trackList).when(service).getAllTracks();
        doReturn(track).when(service).getTrack(track2.getId());
        doReturn(track).when(service).saveTrack(track2);
    }

    @Test
    public void getAllTracksShouldReturnAlistAnd200() throws Exception {
        Track track = new Track();
        track.setAlbumId(1);
        track.setRuntime(180);
        track.setTitle("Number 1 Hit!");
        track = service.saveTrack(track);
        List<Track> trackList = Arrays.asList(track);
        String expectedJson = mapper.writeValueAsString(trackList);
        //Act
        mockMvc.perform(get("/tracks"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(expectedJson));
    }

    @Test
    public void addTracksShouldAddNewTracksAndStatus201() throws Exception {
        Track outPutTrack = new Track();
        outPutTrack.setId(1);
        outPutTrack.setAlbumId(1);
        outPutTrack.setRuntime(180);
        outPutTrack.setTitle("Number 1 Hit!");

        Track inputTrack = new Track();
        inputTrack.setAlbumId(1);
        inputTrack.setRuntime(180);
        inputTrack.setTitle("Number 1 Hit!");

        when(this.service.saveTrack(inputTrack)).thenReturn(outPutTrack);

        mockMvc.perform(
                        post("/tracks")
                                .content(mapper.writeValueAsString(inputTrack)) //converts object to JSON and places into RequestBody
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(print()) //for debugging purposes. Prints the request, handler,... and response objects to the console below.
                .andExpect(status().isCreated()) //Expected response status code.
                .andExpect(content().json(mapper.writeValueAsString(outPutTrack))); //matches the outpu

    }

    @Test
    public void getTrackByIdshouldReturnTrackById() throws Exception {
        Track outPutTrack = new Track();
        outPutTrack.setId(1);
        outPutTrack.setAlbumId(1);
        outPutTrack.setRuntime(180);
        outPutTrack.setTitle("Number 1 Hit!");

        when(service.getTrack(1)).thenReturn(outPutTrack);
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/tracks/{id}", 1)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print()) //for debugging purposes. Prints the request, handler,... and response objects to the console below.
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(1));
    }

    @Test
    public void updateTrackShouldReturn204ForGoodUpdate() throws Exception {
        Track inPutTrack = new Track();
        inPutTrack.setId(1);
        inPutTrack.setAlbumId(1);
        inPutTrack.setRuntime(180);
        inPutTrack.setTitle("Number 1 Hit!");

        doNothing().when(service).updateTrack(inPutTrack);

        mockMvc.perform(
                        MockMvcRequestBuilders.put("/tracks")
                                .content(mapper.writeValueAsString(inPutTrack)) //converts object to JSON and places into RequestBody
                                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print()) //for debugging purposes. Prints the request, handler,... and response objects to the console below.
                .andExpect(status().isNoContent()); //Expected response status code.
    }

    @Test
    public void updateTrackShouldReturn404StatusForBadIdStatus() throws Exception {
        Track inPutTrack = new Track();
        inPutTrack.setId(0);
        inPutTrack.setAlbumId(1);
        inPutTrack.setRuntime(180);
        inPutTrack.setTitle("Number 1 Hit!");

        doThrow(new IllegalArgumentException("Track not found. Unable to update")).when(service).updateTrack(inPutTrack);
        mockMvc.perform(
                        MockMvcRequestBuilders.put("/tracks")
                                .content(mapper.writeValueAsString(inPutTrack)) //converts object to JSON and places into RequestBody
                                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print()) //for debugging purposes. Prints the request, handler,... and response objects to the console below.
                .andExpect(status().isUnprocessableEntity()); //Expected response status code.
    }

    @Test
    public void deleteTrackByIdShouldDeleteTrackAndReturnNoContent() throws Exception {
        doNothing().when(service).deleteTrackById(1);

        mockMvc.perform(
                        MockMvcRequestBuilders.delete("/tracks/{id}", 1))
                .andDo(print())
                .andExpect(status().isNoContent()); //Expect
    }

    @Test
    public void getTrackByAlbumIdshouldReturnTrackList() throws Exception {
        Track outPutTrack = new Track();
        outPutTrack.setId(1);
        outPutTrack.setAlbumId(1);
        outPutTrack.setRuntime(180);
        outPutTrack.setTitle("Number 1 Hit!");
        List<Track> trackList = Arrays.asList(outPutTrack);

        when(service.getTrackByAlbumId(1)).thenReturn(trackList);

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/tracks/albumId/{id}", 1)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print()) //
                .andExpect(status().isOk())
                .andExpect(content().json(mapper.writeValueAsString(trackList)));
    }

}