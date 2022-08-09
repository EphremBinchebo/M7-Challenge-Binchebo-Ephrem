package com.trilogyed.musicstorerecommendations.controller;

import com.fasterxml.jackson.databind.ObjectMapper;;
import com.trilogyed.musicstorerecommendations.model.AlbumRecommendation;
import com.trilogyed.musicstorerecommendations.service.AlbumRecommendationService;
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
@WebMvcTest(AlbumRecommendationController.class)
public class AlbumRecommendationControllerTest {
    @MockBean
    private AlbumRecommendationService service;
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper mapper = new ObjectMapper();

    @Before
    public void setUp() throws Exception {
        setUpAlbumRecommendationServiceMock();
    }

    //
    private void setUpAlbumRecommendationServiceMock() {
        AlbumRecommendation albumRec = new AlbumRecommendation();
        albumRec.setAlbumId(1);
        albumRec.setUserId(12);
        albumRec.setLiked(true);



        AlbumRecommendation albumRec1 = new AlbumRecommendation();
        albumRec1.setId(1);
        albumRec1.setAlbumId(1);
        albumRec1.setUserId(12);
        albumRec1.setLiked(true);

        // service.saveArtist(artist1);

        List<AlbumRecommendation> albumRecList = Arrays.asList(albumRec);
        doReturn(albumRecList).when(service).getAllAlbumRecommendations();
        doReturn(albumRec1).when(service).getAlbumRecommendation(albumRec1.getId());
        doReturn(albumRec1).when(service).saveAlbumRecommendation(albumRec);
    }
    @Test
    public void getAllAlbumRecommendationShouldReturnAlbumReclistAnd200() throws Exception{
        AlbumRecommendation albumRec = new AlbumRecommendation();
        albumRec.setAlbumId(1);
        albumRec.setUserId(12);
        albumRec.setLiked(true);
        service.saveAlbumRecommendation(albumRec);
        List<AlbumRecommendation> albumRecList = Arrays.asList(albumRec);
        String expectedJson = mapper.writeValueAsString(albumRecList);
//        //Act
        mockMvc.perform(get("/albumRecommendations"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(expectedJson));
    }
    @Test
    public void addAlbumRecommendationShouldAddNewAlbumRecommendationAndStatus201() throws Exception{
        AlbumRecommendation outPutAlbumRec = new AlbumRecommendation();
        outPutAlbumRec.setId(1);
        outPutAlbumRec.setAlbumId(1);
        outPutAlbumRec.setUserId(12);
        outPutAlbumRec.setLiked(true);


        AlbumRecommendation inPutAlbumRec = new AlbumRecommendation();
        inPutAlbumRec.setAlbumId(1);
        inPutAlbumRec.setUserId(12);
        inPutAlbumRec.setLiked(true);
        when(this.service.saveAlbumRecommendation(inPutAlbumRec)).thenReturn(outPutAlbumRec);

        mockMvc.perform(
                        post("/albumRecommendations")
                                .content(mapper.writeValueAsString(inPutAlbumRec)) //converts object to JSON and places into RequestBody
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(print()) //for debugging purposes. Prints the request, handler,... and response objects to the console below.
                .andExpect(status().isCreated()) //Expected response status code.
                .andExpect(content().json(mapper.writeValueAsString(outPutAlbumRec))); //matches the outpu

    }

    @Test
    public void getAlbumRecommendationByIdShouldReturnAlbumRecommendationById() throws Exception {
        AlbumRecommendation outPutAlbumRec = new AlbumRecommendation();
        outPutAlbumRec.setId(1);
        outPutAlbumRec.setAlbumId(1);
        outPutAlbumRec.setUserId(12);
        outPutAlbumRec.setLiked(true);

        when(service.getAlbumRecommendation(1)).thenReturn(outPutAlbumRec);
        mockMvc.perform( MockMvcRequestBuilders
                        .get("/albumRecommendations/{id}", 1)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print()) //for debugging purposes. Prints the request, handler,... and response objects to the console below.
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(1));
    }

    @Test
    public void updateAlbumRecommendationShouldReturn204ForGoodUpdate() throws Exception{
        AlbumRecommendation inPutAlbumRec = new AlbumRecommendation();
        inPutAlbumRec.setId(1);
        inPutAlbumRec.setAlbumId(1);
        inPutAlbumRec.setUserId(12);
        inPutAlbumRec.setLiked(true);

        doNothing().when(service).updateAlbumRecommendation(inPutAlbumRec);

        mockMvc.perform(
                        MockMvcRequestBuilders.put("/albumRecommendations")
                                .content(mapper.writeValueAsString(inPutAlbumRec)) //converts object to JSON and places into RequestBody
                                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print()) //for debugging purposes. Prints the request, handler,... and response objects to the console below.
                .andExpect(status().isNoContent()); //Expected response status code.
    }

    @Test
    public void updateAlbumRecommendationShouldReturn404StatusForBadIdStatus() throws Exception {
        AlbumRecommendation inPutAlbumRec = new AlbumRecommendation();
        inPutAlbumRec.setId(0);
        inPutAlbumRec.setAlbumId(1);
        inPutAlbumRec.setUserId(12);
        inPutAlbumRec.setLiked(true);


        doThrow(new IllegalArgumentException("AlbumRecommendation not found. Unable to update")).when(service).updateAlbumRecommendation(inPutAlbumRec);
        mockMvc.perform(
                        MockMvcRequestBuilders.put("/albumRecommendations")
                                .content(mapper.writeValueAsString(inPutAlbumRec)) //converts object to JSON and places into RequestBody
                                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print()) //for debugging purposes. Prints the request, handler,... and response objects to the console below.
                .andExpect(status().isUnprocessableEntity()); //Expected response status code.
    }

    @Test
    public void deleteAlbumRecommendationByIdShouldDeleteAlbumRecommendationAndReturnNoContent() throws Exception{
        doNothing().when(service).deleteAlbumRecommendation(1);

        mockMvc.perform(
                        MockMvcRequestBuilders.delete("/albumRecommendations/{id}",1))
                .andDo(print())
                .andExpect(status().isNoContent()); //Expect
    }

    }

