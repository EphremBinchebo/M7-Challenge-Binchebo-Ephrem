package com.trilogyed.musicstorerecommendations.controller;

//import static org.junit.Assert.*;

import com.fasterxml.jackson.databind.ObjectMapper;


import com.trilogyed.musicstorerecommendations.model.TrackRecommendation;
import com.trilogyed.musicstorerecommendations.service.TrackRecommendationService;
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
@WebMvcTest(TrackRecommendationController.class)
public class TrackRecommendationControllerTest {
    @MockBean
    private TrackRecommendationService service;
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper mapper = new ObjectMapper();

    @Before
    public void setUp() throws Exception {
        setUpTrackRecommendationServiceMock();
    }
    private void setUpTrackRecommendationServiceMock() {

        TrackRecommendation trackRec = new TrackRecommendation();
        trackRec.setTrackId(1);
        trackRec.setUserId(12);
        trackRec.setLiked(true);

        TrackRecommendation trackRec1 = new TrackRecommendation();
        trackRec1.setTrackId(1);
        trackRec1.setTrackId(1);
        trackRec1.setUserId(12);
        trackRec1.setLiked(true);


        List<TrackRecommendation> trackRecList = Arrays.asList(trackRec);
        doReturn(trackRecList).when(service).getAllTrackRecommendations();
        doReturn(trackRec1).when(service).getTrackRecommendation(trackRec1.getId());
        doReturn(trackRec1).when(service).saveTrackRecommendation(trackRec);
    }
    @Test
    public void getAllTrackRecommendationShouldReturnTrackReclistAnd200() throws Exception{
        TrackRecommendation trackRec = new TrackRecommendation();
        trackRec.setTrackId(1);
        trackRec.setUserId(12);
        trackRec.setLiked(true);
        service.saveTrackRecommendation(trackRec);
        List<TrackRecommendation> trackRecList = Arrays.asList(trackRec);
        String expectedJson = mapper.writeValueAsString(trackRecList);
//        //Act
        mockMvc.perform(get("/trackRecommendations"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(expectedJson));
    }
    @Test
    public void addTrackRecommendationShouldAddNewTrackRecommendationAndStatus201() throws Exception{
        TrackRecommendation trackRec = new TrackRecommendation();
        trackRec.setId(1);
        trackRec.setTrackId(1);
        trackRec.setUserId(12);
        trackRec.setLiked(true);


        TrackRecommendation trackRec1 = new TrackRecommendation();
        trackRec1.setTrackId(1);
        trackRec1.setUserId(12);
        trackRec1.setLiked(true);


        when(this.service.saveTrackRecommendation(trackRec1)).thenReturn(trackRec);

        mockMvc.perform(
                        post("/trackRecommendations")
                                .content(mapper.writeValueAsString(trackRec1)) //converts object to JSON and places into RequestBody
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(print()) //for debugging purposes. Prints the request, handler,... and response objects to the console below.
                .andExpect(status().isCreated()) //Expected response status code.
                .andExpect(content().json(mapper.writeValueAsString(trackRec))); //matches the outpu

    }

    @Test
    public void getTrackRecommendationByIdShouldReturnTrackRecommendationById() throws Exception {
        TrackRecommendation outPutTrackRec = new TrackRecommendation();
        outPutTrackRec.setId(1);
        outPutTrackRec.setTrackId(1);
        outPutTrackRec.setUserId(12);
        outPutTrackRec.setLiked(true);

        when(service.getTrackRecommendation(1)).thenReturn(outPutTrackRec);
        mockMvc.perform( MockMvcRequestBuilders
                        .get("/trackRecommendations/{id}", 1)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print()) //for debugging purposes. Prints the request, handler,... and response objects to the console below.
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(1));
    }

    @Test
    public void updateTrackRecommendationShouldReturn204ForGoodUpdate() throws Exception{
        TrackRecommendation inPutTrackRec = new TrackRecommendation();
        inPutTrackRec.setId(1);
        inPutTrackRec.setTrackId(1);
        inPutTrackRec.setUserId(12);
        inPutTrackRec.setLiked(true);

        doNothing().when(service).updateTrackRecommendation(inPutTrackRec);

        mockMvc.perform(
                        MockMvcRequestBuilders.put("/trackRecommendations")
                                .content(mapper.writeValueAsString(inPutTrackRec)) //converts object to JSON and places into RequestBody
                                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print()) //for debugging purposes. Prints the request, handler,... and response objects to the console below.
                .andExpect(status().isNoContent()); //Expected response status code.
    }

    @Test
    public void updateTrackRecommendationShouldReturn404StatusForBadIdStatus() throws Exception {
        TrackRecommendation inPutTrackRec = new TrackRecommendation();
        inPutTrackRec.setId(0);
        inPutTrackRec.setTrackId(1);
        inPutTrackRec.setUserId(12);
        inPutTrackRec.setLiked(true);

        doThrow(new IllegalArgumentException("TrackRecommendation not found. Unable to update")).when(service).updateTrackRecommendation(inPutTrackRec);
        mockMvc.perform(
                        MockMvcRequestBuilders.put("/trackRecommendations")
                                .content(mapper.writeValueAsString(inPutTrackRec)) //converts object to JSON and places into RequestBody
                                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print()) //for debugging purposes. Prints the request, handler,... and response objects to the console below.
                .andExpect(status().isUnprocessableEntity()); //Expected response status code.
    }

    @Test
    public void deleteTrackRecommendationByIdShouldDeleteArtistRecommendationAndReturnNoContent() throws Exception{
        doNothing().when(service).deleteTrackRecommendation(1);

        mockMvc.perform(
                        MockMvcRequestBuilders.delete("/trackRecommendations/{id}",1))
                .andDo(print())
                .andExpect(status().isNoContent()); //Expect
    }
}