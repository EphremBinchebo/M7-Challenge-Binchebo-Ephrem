package com.trilogyed.musicstorecatalog.controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.trilogyed.musicstorecatalog.model.Artist;
import com.trilogyed.musicstorecatalog.model.Label;
import com.trilogyed.musicstorecatalog.service.AlbumServiceLayer;
import com.trilogyed.musicstorecatalog.viewmodel.AlbumViewModel;
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

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

@RunWith(SpringRunner.class)
@WebMvcTest(AlbumController.class)
public class AlbumControllerTest {
    @MockBean
    private AlbumServiceLayer service;
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper mapper = new ObjectMapper();

    @Before
    public void setUp() throws Exception {
        setUpAlbumServiceMock();
    }

    private void setUpAlbumServiceMock() {
        //first create artist and label object
        Artist artist = new Artist();
        artist.setId(1);
        artist.setName("Rofnane");
        artist.setTwitter("@Twitter");
        artist.setInstagram("@TheRofnane");

        Label label1 = new Label();
        label1.setId(1);
        label1.setName("ABC");
        label1.setWebsite("www.abc.com");

        AlbumViewModel album = new AlbumViewModel();
        album.setId(1);
        album.setTitle("Hagere");
        album.setReleaseDate(LocalDate.of(2016, 3, 7));
        album.setArtist(artist);
        album.setLabel(label1);
        album.setListPrice(new BigDecimal(20.00));

        AlbumViewModel album2 = new AlbumViewModel();
        album2.setTitle("Hagere");
        album2.setReleaseDate(LocalDate.of(2016, 3, 7));
        album2.setArtist(artist);
        album2.setLabel(label1);
        album2.setListPrice(new BigDecimal(20.00));


        List<AlbumViewModel> albumList = Arrays.asList(album);
        doReturn(albumList).when(service).getAllAlbums();
        doReturn(album).when(service).getAlbum(album.getId());
        doReturn(album).when(service).createAlbum(album2);
    }

    @Test
    public void getAllAlbumsShouldReturnAlistAnd200() throws Exception {
        Artist artist = new Artist();
        artist.setId(1);
        artist.setName("Rofnane");
        artist.setTwitter("@Twitter");
        artist.setInstagram("@TheRofnane");

        Label label1 = new Label();
        label1.setId(1);
        label1.setName("ABC");
        label1.setWebsite("www.abc.com");


        AlbumViewModel album = new AlbumViewModel();
        album.setTitle("Hagere");
        album.setReleaseDate(LocalDate.of(2016, 3, 7));
        album.setArtist(artist);
        album.setLabel(label1);
        album.setListPrice(new BigDecimal(20.00));
        album = service.createAlbum(album);

        List<AlbumViewModel> albumList = Arrays.asList(album);
        String expectedJson = mapper.writeValueAsString(albumList);
        //Act
        mockMvc.perform(get("/albums"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(expectedJson));
    }

    @Test
    public void createAlbumsShoulCreateNewAlbumsAndStatus201() throws Exception {
        Artist artist = new Artist();
        artist.setId(1);
        artist.setName("Rofnane");
        artist.setTwitter("@Twitter");
        artist.setInstagram("@TheRofnane");

        Label label1 = new Label();
        label1.setId(1);
        label1.setName("ABC");
        label1.setWebsite("www.abc.com");

        AlbumViewModel album = new AlbumViewModel();
        album.setTitle("Hagere");
        album.setReleaseDate(LocalDate.of(2016, 3, 7));
        album.setArtist(artist);
        album.setLabel(label1);
        album.setListPrice(new BigDecimal(20.00));
        album = service.createAlbum(album);

        AlbumViewModel album2 = new AlbumViewModel();
        album2.setTitle("Hagere");
        album2.setReleaseDate(LocalDate.of(2016, 3, 7));
        album2.setArtist(artist);
        album2.setLabel(label1);
        album2.setListPrice(new BigDecimal(20.00));

        when(this.service.createAlbum(album2)).thenReturn(album);

        mockMvc.perform(
                        post("/albums")
                                .content(mapper.writeValueAsString(album2))
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isCreated()) //Expected response status code.
                .andExpect(content().json(mapper.writeValueAsString(album))); //matches the outpu

    }

    @Test
    public void getAlbumByIdshouldReturnAlbumById() throws Exception {
        Artist artist = new Artist();
        artist.setId(1);
        artist.setName("Rofnane");
        artist.setTwitter("@Twitter");
        artist.setInstagram("@TheRofnane");

        Label label1 = new Label();
        label1.setId(1);
        label1.setName("ABC");
        label1.setWebsite("www.abc.com");


        AlbumViewModel album = new AlbumViewModel();
        album.setTitle("Hagere");
        album.setReleaseDate(LocalDate.of(2016, 3, 7));
        album.setArtist(artist);
        album.setLabel(label1);
        album.setListPrice(new BigDecimal(20.00));
        album = service.createAlbum(album);

        when(service.getAlbum(1)).thenReturn(album);
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/albums/{id}", 1)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print()) //for debugging purposes. Prints the request, handler,... and response objects to the console below.
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(1));
    }

    @Test
    public void updateAlbumShouldReturn204ForGoodUpdate() throws Exception {
        Artist artist = new Artist();
        artist.setId(1);
        artist.setName("Rofnane");
        artist.setTwitter("@Twitter");
        artist.setInstagram("@TheRofnane");

        Label label1 = new Label();
        label1.setId(1);
        label1.setName("ABC");
        label1.setWebsite("www.abc.com");

        AlbumViewModel album = new AlbumViewModel();
        album.setId(1);
        album.setTitle("Hagere");
        album.setReleaseDate(LocalDate.of(2016, 3, 7));
        album.setArtist(artist);
        album.setLabel(label1);
        album.setListPrice(new BigDecimal(20.00));

        doNothing().when(service).updateAlbum(album);

        mockMvc.perform(
                        MockMvcRequestBuilders.put("/albums")
                                .content(mapper.writeValueAsString(album)) //converts object to JSON and places into RequestBody
                                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print()) //for debugging purposes. Prints the request, handler,... and response objects to the console below.
                .andExpect(status().isNoContent()); //Expected response status code.
    }

    @Test
    public void updateAlbumShouldReturn404StatusForBadIdStatus() throws Exception {
        Artist artist = new Artist();
        artist.setId(1);
        artist.setName("Rofnane");
        artist.setTwitter("@Twitter");
        artist.setInstagram("@TheRofnane");

        Label label1 = new Label();
        label1.setId(1);
        label1.setName("ABC");
        label1.setWebsite("www.abc.com");


        AlbumViewModel album = new AlbumViewModel();
        album.setId(0);
        album.setTitle("Hagere");
        album.setReleaseDate(LocalDate.of(2016, 3, 7));
        album.setArtist(artist);
        album.setLabel(label1);
        album.setListPrice(new BigDecimal(20.00));

        doThrow(new IllegalArgumentException("Album not found. Unable to update")).when(service).updateAlbum(album);
        mockMvc.perform(
                        MockMvcRequestBuilders.put("/albums")
                                .content(mapper.writeValueAsString(album)) //converts object to JSON and places into RequestBody
                                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print()) //for debugging purposes. Prints the request, handler,... and response objects to the console below.
                .andExpect(status().isUnprocessableEntity()); //Expected response status code.
    }

    @Test
    public void deleteAlbumByIdShouldDeleteAlbumAndReturnNoContent() throws Exception {
        doNothing().when(service).removeAlbum(1);


        mockMvc.perform(
                        MockMvcRequestBuilders.delete("/albums/{id}", 1))
                .andDo(print())
                .andExpect(status().isNoContent()); //Expect
    }


}
