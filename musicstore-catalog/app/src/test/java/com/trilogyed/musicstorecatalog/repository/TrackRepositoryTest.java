package com.trilogyed.musicstorecatalog.repository;

import com.trilogyed.musicstorecatalog.model.Album;
import com.trilogyed.musicstorecatalog.model.Artist;
import com.trilogyed.musicstorecatalog.model.Label;
import com.trilogyed.musicstorecatalog.model.Track;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class TrackRepositoryTest {

    @Autowired
    TrackRepository trackRepository;
    @Autowired
    AlbumRepository albumRepository;
    @Autowired
    ArtistRepository artistRepository;
    @Autowired
    LabelRepository labelRepository;

    @Before
    public void setUp() throws Exception {
        trackRepository.deleteAll();
        albumRepository.deleteAll();
        artistRepository.deleteAll();
        labelRepository.deleteAll();
    }

    @Test
    public void addGetDeleteTrack() {

        // Need to create a Label, Artist, and Album first
        Label label = new Label();
        label.setName("Amharic Music");
        label.setWebsite("www.amharicmusic.com");
        label = labelRepository.save(label);

        Artist artist = new Artist();
        artist.setName("Tedy Afro");
        artist.setInstagram("@TedyAfro");
        artist.setTwitter("@TheTedyAfro");
        artist = artistRepository.save(artist);

        Album album = new Album();
        album.setTitle("Lambadina");
        album.setArtistId(artist.getId());
        album.setLabelId(label.getId());
        album.setReleaseDate(LocalDate.of(2006, 1, 15));
        album.setListPrice(new BigDecimal("18.00"));

        album = albumRepository.save(album);

        Track track = new Track();
        track.setTitle("Ja Yasteseryale");
        track.setRuntime(120);
        track.setAlbumId(album.getId());
        track = trackRepository.save(track);

        Optional<Track> track1 = trackRepository.findById(track.getId());

        assertEquals(track1.get(), track);

        trackRepository.deleteById(track.getId());

        track1 = trackRepository.findById(track.getId());

        assertFalse(track1.isPresent());
    }

    @Test
    public void updateTrack() {

        // Need to create a Label, Artist, and Album first
        Label label = new Label();
        label.setName("Amharic Music");
        label.setWebsite("www.amharicmusic.com");
        label = labelRepository.save(label);

        Artist artist = new Artist();
        artist.setName("Tedy Afro");
        artist.setInstagram("@TedyAfro");
        artist.setTwitter("@TheTedyAfro");
        artist = artistRepository.save(artist);

        Album album = new Album();
        album.setTitle("Lambadina");
        album.setArtistId(artist.getId());
        album.setLabelId(label.getId());
        album.setReleaseDate(LocalDate.of(2006, 1, 15));
        album.setListPrice(new BigDecimal("18.00"));

        album = albumRepository.save(album);

        Track track = new Track();
        track.setTitle("Ja Yasteseryale");
        track.setRuntime(120);
        track.setAlbumId(album.getId());
        track = trackRepository.save(track);

        track.setTitle("Tikure sew");
        track.setRuntime(20);

        trackRepository.save(track);

        Optional<Track> track1 = trackRepository.findById(track.getId());

        assertEquals(track1.get(), track);

    }

    @Test
    public void getAllTracks() {

        // Need to create a Label, Artist, and Album first
        Label label = new Label();
        label.setName("Amharic Music");
        label.setWebsite("www.amharicmusic.com");
        label = labelRepository.save(label);

        Artist artist = new Artist();
        artist.setName("Tedy Afro");
        artist.setInstagram("@TedyAfro");
        artist.setTwitter("@TheTedyAfro");
        artist = artistRepository.save(artist);

        Album album = new Album();
        album.setTitle("Lambadina");
        album.setArtistId(artist.getId());
        album.setLabelId(label.getId());
        album.setReleaseDate(LocalDate.of(2006, 1, 15));
        album.setListPrice(new BigDecimal("18.00"));

        album = albumRepository.save(album);

        Album album1 = new Album();
        album1.setTitle("Ethiopia");
        album1.setArtistId(artist.getId());
        album1.setLabelId(label.getId());
        album1.setReleaseDate(LocalDate.of(2016, 3, 10));
        album1.setListPrice(new BigDecimal("19.95"));

        album1 = albumRepository.save(album1);


        Track track = new Track();
        track.setTitle("Haile Haile");
        track.setRuntime(130);
        track.setAlbumId(album.getId());
        track = trackRepository.save(track);

        track = new Track();
        track.setTitle("Naete");
        track.setRuntime(120);
        track.setAlbumId(album1.getId());
        track = trackRepository.save(track);

        List<Track> tList = trackRepository.findAll();

        assertEquals(tList.size(), 2);

    }

    @Test
    public void getTracksByAlbum() {

        // Need to create a Label, Artist, and Album first
        Label label = new Label();
        label.setName("Oromic Music");
        label.setWebsite("www.oromicmusic.com");
        label = labelRepository.save(label);

        Artist artist = new Artist();
        artist.setName("Hachalu Hundensa");
        artist.setInstagram("@HachaluHundensa");
        artist.setTwitter("@TheHachaluHundensa");
        artist = artistRepository.save(artist);

        Album album = new Album();
        album.setTitle("Ya Jima");
        album.setArtistId(artist.getId());
        album.setLabelId(label.getId());
        album.setReleaseDate(LocalDate.of(2012, 1, 15));
        album.setListPrice(new BigDecimal("11.95"));

        album = albumRepository.save(album);

        Album album1 = new Album();
        album1.setTitle("Ya Abichu");
        album1.setArtistId(artist.getId());
        album1.setLabelId(label.getId());
        album1.setReleaseDate(LocalDate.of(2015, 6, 25));
        album1.setListPrice(new BigDecimal("19.95"));

        album1 = albumRepository.save(album1);


        Track track = new Track();
        track.setTitle("Oromiya oromiya");
        track.setRuntime(160);
        track.setAlbumId(album.getId());
        track = trackRepository.save(track);

        track = new Track();
        track.setTitle("Ya Bale");
        track.setRuntime(120);
        track.setAlbumId(album1.getId());
        track = trackRepository.save(track);

        track = new Track();
        track.setTitle("Arsi");
        track.setRuntime(100);
        track.setAlbumId(album1.getId());
        track = trackRepository.save(track);

        List<Track> tList = trackRepository.findAllTracksByAlbumId(album.getId());
        assertEquals(tList.size(), 1);

        tList = trackRepository.findAllTracksByAlbumId(album1.getId());
        assertEquals(tList.size(), 2);

    }
}