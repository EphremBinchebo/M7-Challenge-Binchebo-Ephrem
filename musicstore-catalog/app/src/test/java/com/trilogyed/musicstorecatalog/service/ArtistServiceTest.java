package com.trilogyed.musicstorecatalog.service;

import com.trilogyed.musicstorecatalog.model.Artist;
import com.trilogyed.musicstorecatalog.repository.ArtistRepository;
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
public class ArtistServiceTest {

    private ArtistRepository artistRepository;

    private ArtistService service;

    @Before
    public void setUp() throws Exception {
        setUpArtistRepository();
        service = new ArtistService(artistRepository);
    }

    private void setUpArtistRepository() {
        this.artistRepository = mock(ArtistRepository.class);
        Artist newArtist1 = new Artist("Tedy Afro", "@TedyAfro", "@TheTedyAfro");

        Artist savedArtist1 = new Artist(1, "Tedy Afro", "@TedyAfro", "@TheTedyAfro");

        Artist newArtist2 = new Artist("Aster Aweke", "@AsterAweke", "@TheAsterAweke");

        Artist savedArtist2 = new Artist(2, "Aster Aweke", "@AsterAweke", "@TheAsterAweke");

        List<Artist> artistList = Arrays.asList(savedArtist1, savedArtist2);

        Optional<Artist> findByIdResult = Optional.of(savedArtist1);

        doReturn(savedArtist1).when(artistRepository).save(newArtist1);
        doReturn(savedArtist2).when(artistRepository).save(newArtist2);
        doReturn(findByIdResult).when(artistRepository).findById(1);
        doReturn(artistList).when(artistRepository).findAll();

    }


    @Test
    public void getAllArtistShouldReturnAListOfArtists() {
        List<Artist> actualResult = service.getAllArtists();
        assertEquals(2, actualResult.size());
    }

    @Test
    public void AddAndGetArtist() {
        Artist artist1 = new Artist("Tedy Afro", "@TedyAfro", "@TheTedyAfro");
        artist1 = service.saveArtist(artist1);
        Artist actualResult = service.getArtist(artist1.getId());
        assertEquals(artist1, actualResult);

    }

    @Test
    public void shouldUpdateArtist() {
        Artist artist1 = new Artist("Tedy Afro", "@TedyAfro", "@TheTedyAfro");
        artist1 = service.saveArtist(artist1);
        artist1.setTwitter("@MyTedyAfro");
        artist1.setInstagram("@AfroBand");
        service.updateArtist(artist1);
        verify(artistRepository, times(2)).save(any(Artist.class));
    }

    @Test(expected = NullPointerException.class)
    public void shouldFailToUpdateWithNullArtist() {
        Artist artist = null;

        service.updateArtist(artist);
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldFailToUpdateArtistWithBadId() {
        Artist artist1 = new Artist("Tedy Afro", "@TedyAfro", "@TheTedyAfro");
        artist1 = service.saveArtist(artist1);
        artist1.setName("Aster Aweke");
        artist1.setId(artist1.getId() + 1);
        service.updateArtist(artist1);
    }

    @Test
    public void shouldDeleteArtistById() {
        Artist artist1 = new Artist("Tedy Afro", "@TedyAfro", "@TheTedyAfro");
        artist1 = service.saveArtist(artist1);
        service.deleteArtist(artist1.getId());
        verify(artistRepository).deleteById(artist1.getId());
    }

}


