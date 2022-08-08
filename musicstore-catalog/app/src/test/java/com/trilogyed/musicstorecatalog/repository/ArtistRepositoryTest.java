package com.trilogyed.musicstorecatalog.repository;

import com.trilogyed.musicstorecatalog.model.Artist;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class ArtistRepositoryTest {
    @Autowired
    ArtistRepository artistRepository;

    @Before
    public void setUp() throws Exception {
        artistRepository.deleteAll();
    }

    @Test
    public void addAndDeleteArtist() {
        Artist artist = new Artist();
        artist.setName("Tedy Afro");
        artist.setInstagram("@TedyAfro");
        artist.setTwitter("@TheTedyAfro");
        artist = artistRepository.save(artist);

        Optional<Artist> artist1 = artistRepository.findById(artist.getId());
        assertEquals(artist1.get(), artist);

        artistRepository.deleteById(artist.getId());
        artist1 = artistRepository.findById(artist.getId());
        assertFalse(artist1.isPresent());
    }

    @Test
    public void getAllArtists() {
        Artist artist = new Artist();
        artist.setName("Tedy Afro");
        artist.setInstagram("@TedyAfro");
        artist.setTwitter("@TheTedyAfro");
        artist = artistRepository.save(artist);

        artist = new Artist();
        artist.setName("Ephrem Tamiru");
        artist.setInstagram("@EphremTamiru");
        artist.setTwitter("@EphremTamiru");
        artist = artistRepository.save(artist);

        List<Artist> artistList = artistRepository.findAll();
        assertEquals(artistList.size(), 2);
    }

    @Test
    public void updateArtist() {
        Artist artist = new Artist();
        artist.setName("Ephrem Tamiru");
        artist.setInstagram("@EphremTamiru");
        artist.setTwitter("@EphremTamiru");
        artist = artistRepository.save(artist);

        artist.setName("Shewandage Hailu");
        artist.setInstagram("@ShewandagneHailu");
        artist.setTwitter("@ShewandagneHailu");
        artistRepository.save(artist);
        Optional<Artist> artist1 = artistRepository.findById(artist.getId());
        assertEquals(artist1.get(), artist);
    }
}