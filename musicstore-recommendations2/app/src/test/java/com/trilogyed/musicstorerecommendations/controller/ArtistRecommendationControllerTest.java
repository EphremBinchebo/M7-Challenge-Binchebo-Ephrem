package com.trilogyed.musicstorerecommendations.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.trilogyed.musicstorerecommendations.model.ArtistRecommendation;
import com.trilogyed.musicstorerecommendations.service.ArtistRecommendationService;
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
@WebMvcTest(ArtistRecommendationController.class)
public class ArtistRecommendationControllerTest {
    @MockBean
    private ArtistRecommendationService service;
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper mapper = new ObjectMapper();

    @Before
    public void setUp() throws Exception {
        setUpArtistRecommendationServiceMock();
    }

    private void setUpArtistRecommendationServiceMock() {
        ArtistRecommendation artistRec = new ArtistRecommendation();
        artistRec.setArtistId(1);
        artistRec.setUserId(12);
        artistRec.setLiked(true);

        //service.saveArtist(artist);
        ArtistRecommendation artistRec1 = new ArtistRecommendation();
        artistRec1.setId(1);
        artistRec.setArtistId(1);
        artistRec.setUserId(12);
        artistRec.setLiked(true);

        // service.saveArtist(artist1);

        List<ArtistRecommendation> artistRecList = Arrays.asList(artistRec);
        doReturn(artistRecList).when(service).getAllArtistRecommendations();
        doReturn(artistRec1).when(service).getArtistRecommendation(artistRec1.getId());
        doReturn(artistRec1).when(service).saveArtistRecommendation(artistRec);
    }
    @Test
    public void getAllArtistsShouldReturnAlistAnd200() throws Exception{
        ArtistRecommendation artistRec = new ArtistRecommendation();
        artistRec.setArtistId(1);
        artistRec.setUserId(12);
        artistRec.setLiked(true);
        service.saveArtistRecommendation(artistRec);
        List<ArtistRecommendation> artistRecList = Arrays.asList(artistRec);
        String expectedJson = mapper.writeValueAsString(artistRecList);
//        //Act
        mockMvc.perform(get("/artistRecommendations"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(expectedJson));
    }
    @Test
    public void addArtistRecommendationShouldAddNewArtistRecommendationAndStatus201() throws Exception{
        ArtistRecommendation outPutArtistRec = new ArtistRecommendation();
        outPutArtistRec.setId(1);
        outPutArtistRec.setArtistId(1);
        outPutArtistRec.setUserId(12);
        outPutArtistRec.setLiked(true);


        ArtistRecommendation inPutArtistRec = new ArtistRecommendation();
        inPutArtistRec.setArtistId(1);
        inPutArtistRec.setUserId(12);
        inPutArtistRec.setLiked(true);

        when(this.service.saveArtistRecommendation(inPutArtistRec)).thenReturn(outPutArtistRec);

        mockMvc.perform(
                        post("/artistRecommendations")
                                .content(mapper.writeValueAsString(inPutArtistRec)) //converts object to JSON and places into RequestBody
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(print()) //for debugging purposes. Prints the request, handler,... and response objects to the console below.
                .andExpect(status().isCreated()) //Expected response status code.
                .andExpect(content().json(mapper.writeValueAsString(outPutArtistRec))); //matches the outpu

    }

    @Test
    public void getArtistRecommendationByIdShouldReturnArtistRecommendationById() throws Exception {
        ArtistRecommendation outPutArtistRec = new ArtistRecommendation();
        outPutArtistRec.setId(1);
        outPutArtistRec.setArtistId(1);
        outPutArtistRec.setUserId(12);
        outPutArtistRec.setLiked(true);

        when(service.getArtistRecommendation(1)).thenReturn(outPutArtistRec);
        mockMvc.perform( MockMvcRequestBuilders
                        .get("/artistRecommendations/{id}", 1)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print()) //for debugging purposes. Prints the request, handler,... and response objects to the console below.
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(1));
    }

    @Test
    public void updateArtistRecommendationShouldReturn204ForGoodUpdate() throws Exception{
        ArtistRecommendation inPutArtistRec = new ArtistRecommendation();
        inPutArtistRec.setId(1);
        inPutArtistRec.setArtistId(1);
        inPutArtistRec.setUserId(12);
        inPutArtistRec.setLiked(true);

        doNothing().when(service).updateArtistRecommendation(inPutArtistRec);

        mockMvc.perform(
                        MockMvcRequestBuilders.put("/artistRecommendations")
                                .content(mapper.writeValueAsString(inPutArtistRec)) //converts object to JSON and places into RequestBody
                                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print()) //for debugging purposes. Prints the request, handler,... and response objects to the console below.
                .andExpect(status().isNoContent()); //Expected response status code.
    }

    @Test
    public void updateArtistRecommendationShouldReturn404StatusForBadIdStatus() throws Exception {
        ArtistRecommendation inPutArtistRec = new ArtistRecommendation();
        inPutArtistRec.setId(0);
        inPutArtistRec.setArtistId(1);
        inPutArtistRec.setUserId(12);
        inPutArtistRec.setLiked(true);

        doThrow(new IllegalArgumentException("ArtistRecommendation not found. Unable to update")).when(service).updateArtistRecommendation(inPutArtistRec);
        mockMvc.perform(
                        MockMvcRequestBuilders.put("/artistRecommendations")
                                .content(mapper.writeValueAsString(inPutArtistRec)) //converts object to JSON and places into RequestBody
                                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print()) //for debugging purposes. Prints the request, handler,... and response objects to the console below.
                .andExpect(status().isUnprocessableEntity()); //Expected response status code.
    }

    @Test
    public void deleteArtistRecommendationByIdShouldDeleteArtistRecommendationAndReturnNoContent() throws Exception{
        doNothing().when(service).deleteArtistRecommendation(1);

        mockMvc.perform(
                        MockMvcRequestBuilders.delete("/artistRecommendations/{id}",1))
                .andDo(print())
                .andExpect(status().isNoContent()); //Expect
    }


}