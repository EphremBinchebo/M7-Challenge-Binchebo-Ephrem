package com.trilogyed.musicstorecatalog.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.trilogyed.musicstorecatalog.model.Artist;
import com.trilogyed.musicstorecatalog.service.ArtistService;
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
@WebMvcTest(ArtistController.class)
public class ArtistControllerTest {
    @MockBean
    private ArtistService service;
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper mapper = new ObjectMapper();

    @Before
    public void setUp() throws Exception {
        setUpArtistServiceMock();
    }

    private void setUpArtistServiceMock() {
        Artist artist = new Artist();
        artist.setName("Rofnane");
        artist.setTwitter("@Twitter");
        artist.setInstagram("@TheRofnane");

        Artist artist1 = new Artist();
        artist1.setId(1);
        artist1.setName("Rofnane");
        artist1.setTwitter("@Twitter");
        artist1.setInstagram("@TheRofnane");

        List<Artist> artistList = Arrays.asList(artist);
        doReturn(artistList).when(service).getAllArtists();
        doReturn(artist1).when(service).getArtist(artist1.getId());
        doReturn(artist1).when(service).saveArtist(artist);
    }

    @Test
    public void getAllArtistsShouldReturnAlistAnd200() throws Exception {
        Artist artist = new Artist();
        artist.setName("Rofnane");
        artist.setTwitter("@Twitter");
        artist.setInstagram("@TheRofnane");
        service.saveArtist(artist);
        List<Artist> artistList = Arrays.asList(artist);
        String expectedJson = mapper.writeValueAsString(artistList);
        //Act
        mockMvc.perform(get("/artists"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(expectedJson));
    }

    @Test
    public void addArtistsShouldAddNewArtistsAndStatus201() throws Exception {
        Artist outPutArtist = new Artist();
        outPutArtist.setId(1);
        outPutArtist.setName("Aster Aweke");
        outPutArtist.setTwitter("@AsterAweke");
        outPutArtist.setInstagram("TheAsterAweke");

        Artist inputArtist = new Artist();
        outPutArtist.setName("Aster Aweke");
        outPutArtist.setTwitter("@AsterAweke");
        outPutArtist.setInstagram("TheAsterAweke");

        when(this.service.saveArtist(inputArtist)).thenReturn(outPutArtist);

        mockMvc.perform(
                        post("/artists")
                                .content(mapper.writeValueAsString(inputArtist)) //converts object to JSON and places into RequestBody
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(print()) //for debugging purposes. Prints the request, handler,... and response objects to the console below.
                .andExpect(status().isCreated()) //Expected response status code.
                .andExpect(content().json(mapper.writeValueAsString(outPutArtist))); //matches the outpu

    }

    @Test
    public void getArtistByIdshouldReturnArtistById() throws Exception {
        Artist outPutArtist = new Artist();
        outPutArtist.setId(1);
        outPutArtist.setName("Aster Aweke");
        outPutArtist.setTwitter("@AsterAweke");
        outPutArtist.setInstagram("TheAsterAweke");

        when(service.getArtist(1)).thenReturn(outPutArtist);
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/artists/{id}", 1)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print()) //for debugging purposes. Prints the request, handler,... and response objects to the console below.
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(1));
    }

    @Test
    public void updateArtistShouldReturn204ForGoodUpdate() throws Exception {
        Artist inPutArtist = new Artist();
        inPutArtist.setId(1);
        inPutArtist.setName("Aster Aweke");
        inPutArtist.setTwitter("@AsterAweke");
        inPutArtist.setInstagram("TheAsterAweke");

        doNothing().when(service).updateArtist(inPutArtist);

        mockMvc.perform(
                        MockMvcRequestBuilders.put("/artists")
                                .content(mapper.writeValueAsString(inPutArtist)) //converts object to JSON and places into RequestBody
                                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print()) //for debugging purposes. Prints the request, handler,... and response objects to the console below.
                .andExpect(status().isNoContent()); //Expected response status code.
    }

    @Test
    public void updateArtistShouldReturn404StatusForBadIdStatus() throws Exception {
        Artist inPutArtist = new Artist();
        inPutArtist.setId(0);
        inPutArtist.setName("Aster Aweke");
        inPutArtist.setTwitter("@AsterAweke");
        inPutArtist.setInstagram("TheAsterAweke");

        doThrow(new IllegalArgumentException("Artist not found. Unable to update")).when(service).updateArtist(inPutArtist);
        mockMvc.perform(
                        MockMvcRequestBuilders.put("/artists")
                                .content(mapper.writeValueAsString(inPutArtist)) //converts object to JSON and places into RequestBody
                                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print()) //for debugging purposes. Prints the request, handler,... and response objects to the console below.
                .andExpect(status().isUnprocessableEntity()); //Expected response status code.
    }

    @Test
    public void deleteArtistByIdShouldDeleteArtistAndReturnNoContent() throws Exception {
        doNothing().when(service).deleteArtist(1);

        mockMvc.perform(
                        MockMvcRequestBuilders.delete("/artists/{id}", 1))
                .andDo(print())
                .andExpect(status().isNoContent()); //Expect
    }

}