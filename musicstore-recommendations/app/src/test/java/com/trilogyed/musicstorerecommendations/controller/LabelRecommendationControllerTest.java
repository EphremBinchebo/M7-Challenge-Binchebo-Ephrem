package com.trilogyed.musicstorerecommendations.controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.trilogyed.musicstorerecommendations.model.LabelRecommendation;
import com.trilogyed.musicstorerecommendations.service.LabelRecommendationService;
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
@WebMvcTest(LabelRecommendationController.class)
public class LabelRecommendationControllerTest {
    @MockBean
    private LabelRecommendationService service;
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper mapper = new ObjectMapper();

    @Before
    public void setUp() throws Exception {
        setUpLabelRecommendationServiceMock();
    }

    private void setUpLabelRecommendationServiceMock() {
        LabelRecommendation labelRec = new LabelRecommendation();
        labelRec.setLabelId(1);
        labelRec.setUserId(2);
        labelRec.setLiked(true);


        LabelRecommendation labelRec1 = new LabelRecommendation();
        labelRec1.setLabelId(1);
        labelRec1.setLabelId(1);
        labelRec1.setUserId(2);
        labelRec1.setLiked(true);


        List<LabelRecommendation> labelRecList = Arrays.asList(labelRec);
        doReturn(labelRecList).when(service).getAllLabelRecommendations();
        doReturn(labelRec1).when(service).getLabelRecommendation(labelRec1.getId());
        doReturn(labelRec1).when(service).saveLabelRecommendation(labelRec);
    }

    @Test
    public void getAllLabelRecommendationShouldReturnLabelRecommendationlistAnd200() throws Exception {
        LabelRecommendation labelRec = new LabelRecommendation();
        labelRec.setLabelId(1);
        labelRec.setUserId(2);
        labelRec.setLiked(true);

        service.saveLabelRecommendation(labelRec);
        List<LabelRecommendation> labelRecList = Arrays.asList(labelRec);
        String expectedJson = mapper.writeValueAsString(labelRecList);
//        //Act
        mockMvc.perform(get("/labelRecommendations"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(expectedJson));
    }

    @Test
    public void addLabelRecommendationShouldAddNewLabelRecommendationAndStatus201() throws Exception {
        LabelRecommendation labelRec = new LabelRecommendation();
        labelRec.setId(1);
        labelRec.setLabelId(1);
        labelRec.setUserId(2);
        labelRec.setLiked(true);

        LabelRecommendation inPutLabelRec = new LabelRecommendation();
        inPutLabelRec.setLabelId(1);
        inPutLabelRec.setUserId(2);
        inPutLabelRec.setLiked(true);
        ;

        when(this.service.saveLabelRecommendation(inPutLabelRec)).thenReturn(labelRec);

        mockMvc.perform(
                        post("/labelRecommendations")
                                .content(mapper.writeValueAsString(inPutLabelRec)) //converts object to JSON and places into RequestBody
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(print()) //for debugging purposes. Prints the request, handler,... and response objects to the console below.
                .andExpect(status().isCreated()) //Expected response status code.
                .andExpect(content().json(mapper.writeValueAsString(labelRec))); //matches the outpu

    }

    @Test
    public void getLabelRecommendationByIdShouldReturnLabelRecommendationById() throws Exception {
        LabelRecommendation labelRec = new LabelRecommendation();
        labelRec.setId(1);
        labelRec.setLabelId(1);
        labelRec.setUserId(2);
        labelRec.setLiked(true);

        when(service.getLabelRecommendation(1)).thenReturn(labelRec);
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/labelRecommendations/{id}", 1)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print()) //for debugging purposes. Prints the request, handler,... and response objects to the console below.
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(1));
    }

    @Test
    public void updateLabelRecommendationShouldUpdateLabelRecommendation204ForGoodUpdate() throws Exception {
        LabelRecommendation labelRec = new LabelRecommendation();
        labelRec.setId(1);
        labelRec.setLabelId(1);
        labelRec.setUserId(2);
        labelRec.setLiked(true);

        doNothing().when(service).updateLabelRecommendation(labelRec);

        mockMvc.perform(
                        MockMvcRequestBuilders.put("/labelRecommendations")
                                .content(mapper.writeValueAsString(labelRec)) //converts object to JSON and places into RequestBody
                                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print()) //for debugging purposes. Prints the request, handler,... and response objects to the console below.
                .andExpect(status().isNoContent()); //Expected response status code.
    }

    @Test
    public void updateArtistRecommendationShouldReturn404StatusForBadIdStatus() throws Exception {
        LabelRecommendation labelRec = new LabelRecommendation();
        labelRec.setId(0);
        labelRec.setLabelId(1);
        labelRec.setUserId(2);
        labelRec.setLiked(true);


        doThrow(new IllegalArgumentException("LabelRecommendation not found. Unable to update")).when(service).updateLabelRecommendation(labelRec);
        mockMvc.perform(
                        MockMvcRequestBuilders.put("/labelRecommendations")
                                .content(mapper.writeValueAsString(labelRec)) //converts object to JSON and places into RequestBody
                                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print()) //for debugging purposes. Prints the request, handler,... and response objects to the console below.
                .andExpect(status().isUnprocessableEntity()); //Expected response status code.
    }

    @Test
    public void deleteLabelRecommendationByIdShouldDeleteLabelRecommendationAndReturnNoContent() throws Exception {
        doNothing().when(service).deleteLabelRecommendation(1);

        mockMvc.perform(
                        MockMvcRequestBuilders.delete("/labelRecommendations/{id}", 1))
                .andDo(print())
                .andExpect(status().isNoContent()); //Expect
    }

}