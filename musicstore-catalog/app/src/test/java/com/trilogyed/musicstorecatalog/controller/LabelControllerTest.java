package com.trilogyed.musicstorecatalog.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.trilogyed.musicstorecatalog.model.Label;
import com.trilogyed.musicstorecatalog.service.LabelService;
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

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(LabelController.class)
public class LabelControllerTest {
    @MockBean
    private LabelService service;
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper mapper = new ObjectMapper();

    @Before
    public void setUp() throws Exception {
        setUpLabelServiceMock();
    }

    private void setUpLabelServiceMock() {
        Label label = new Label();
        label.setName("ABC");
        label.setWebsite("www.abc.com");


        Label label1 = new Label();
        label1.setId(1);
        label1.setName("ABC");
        label1.setWebsite("www.abc.com");

        List<Label> labelList = Arrays.asList(label);
        doReturn(labelList).when(service).getLabels();
        doReturn(label1).when(service).getLabel(label1.getId());
        doReturn(label1).when(service).saveLabel(label);
    }

    @Test
    public void getLabelsShouldReturnLabelListAnd200() throws Exception {
        Label label = new Label();
        label.setName("ABC");
        label.setWebsite("www.abc.com");
        service.saveLabel(label);
        List<Label> labelList = Arrays.asList(label);
        String expectedJson = mapper.writeValueAsString(labelList);
        //Act
        mockMvc.perform(get("/labels"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(expectedJson));
    }

    @Test
    public void addLabelsShouldAddNewLabelsAndStatus201() throws Exception {
        Label label = new Label();
        label.setName("ABC");
        label.setWebsite("www.abc.com");

        Label label1 = new Label();
        label1.setId(1);
        label1.setName("ABC");
        label1.setWebsite("www.abc.com");
        ;

        when(this.service.saveLabel(label)).thenReturn(label1);

        mockMvc.perform(
                        post("/labels")
                                .content(mapper.writeValueAsString(label)) //converts object to JSON and places into RequestBody
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(print()) //for debugging purposes. Prints the request, handler,... and response objects to the console below.
                .andExpect(status().isCreated()) //Expected response status code.
                .andExpect(content().json(mapper.writeValueAsString(label1))); //matches the outpu

    }

    @Test
    public void getLabelsByIdshouldReturnLabel() throws Exception {

        Label label1 = new Label();
        label1.setId(1);
        label1.setName("ABC");
        label1.setWebsite("www.abc.com");

        when(service.getLabel(1)).thenReturn(label1);
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/labels/{id}", 1)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print()) //for debugging purposes. Prints the request, handler,... and response objects to the console below.
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(1));
    }

    @Test
    public void updateLabelShouldReturn204ForGoodUpdate() throws Exception {
        Label label1 = new Label();
        label1.setId(1);
        label1.setName("ABC");
        label1.setWebsite("www.abc.com");

        doNothing().when(service).updateLabel(label1);

        mockMvc.perform(
                        MockMvcRequestBuilders.put("/labels")
                                .content(mapper.writeValueAsString(label1)) //converts object to JSON and places into RequestBody
                                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print()) //for debugging purposes. Prints the request, handler,... and response objects to the console below.
                .andExpect(status().isNoContent()); //Expected response status code.
    }

    @Test
    public void updateLabelShouldReturn404StatusForBadIdStatus() throws Exception {
        Label label1 = new Label();
        label1.setId(0);
        label1.setName("ABC");
        label1.setWebsite("www.abc.com");

        doThrow(new IllegalArgumentException("Label not found. Unable to update")).when(service).updateLabel(label1);
        mockMvc.perform(
                        MockMvcRequestBuilders.put("/labels")
                                .content(mapper.writeValueAsString(label1)) //converts object to JSON and places into RequestBody
                                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print()) //for debugging purposes. Prints the request, handler,... and response objects to the console below.
                .andExpect(status().isUnprocessableEntity()); //Expected response status code.
    }

    @Test
    public void deleteLabelByIdShouldDeleteLabelAndReturnNoContent() throws Exception {
        doNothing().when(service).deleteLabel(1);

        mockMvc.perform(
                        MockMvcRequestBuilders.delete("/labels/{id}", 1))
                .andDo(print())
                .andExpect(status().isNoContent()); //Expect
    }
}