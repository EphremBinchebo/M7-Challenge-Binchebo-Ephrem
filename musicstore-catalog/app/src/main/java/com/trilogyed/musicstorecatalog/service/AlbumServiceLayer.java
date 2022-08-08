package com.trilogyed.musicstorecatalog.service;

import com.trilogyed.musicstorecatalog.model.Album;
import com.trilogyed.musicstorecatalog.model.Artist;
import com.trilogyed.musicstorecatalog.model.Label;
import com.trilogyed.musicstorecatalog.model.Track;
import com.trilogyed.musicstorecatalog.repository.AlbumRepository;
import com.trilogyed.musicstorecatalog.repository.ArtistRepository;
import com.trilogyed.musicstorecatalog.repository.LabelRepository;
import com.trilogyed.musicstorecatalog.repository.TrackRepository;
import com.trilogyed.musicstorecatalog.viewmodel.AlbumViewModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class AlbumServiceLayer {
    private AlbumRepository albumRepository;
    private LabelRepository labelRepository;
    private ArtistRepository artistRepository;
    private TrackRepository trackRepository;

    @Autowired
    public AlbumServiceLayer(AlbumRepository albumRepository, LabelRepository labelRepository, ArtistRepository artistRepository, TrackRepository trackRepository) {
        this.albumRepository = albumRepository;
        this.labelRepository = labelRepository;
        this.artistRepository = artistRepository;
        this.trackRepository = trackRepository;
    }

//Album service

    @Transactional
    public AlbumViewModel createAlbum(AlbumViewModel viewModel) {
        if (viewModel == null)
            throw new NullPointerException("Create Album failed. no album data.");
        Album album = new Album();
        album.setTitle(viewModel.getTitle());
        album.setReleaseDate(viewModel.getReleaseDate());
        album.setListPrice(viewModel.getListPrice());
        album.setLabelId(viewModel.getLabel().getId());
        album.setArtistId(viewModel.getArtist().getId());
        album = albumRepository.save(album);
        viewModel.setId(album.getId());

        List<Track> tracks = viewModel.getTracks();

        tracks.stream()
                .forEach(t ->
                {
                    t.setAlbumId(viewModel.getId());
                    trackRepository.save(t);
                });

        tracks = trackRepository.findAllTracksByAlbumId(viewModel.getId());
        viewModel.setTracks(tracks);

        return viewModel;
    }

    public AlbumViewModel getAlbum(int id) {
        Optional<Album> album = albumRepository.findById(id);
        return album.isPresent() ? buildAlbumViewModel(album.get()) : null;
    }

    public AlbumViewModel buildAlbumViewModel(Album album) {
        //Validaton
        Optional<Artist> artist = artistRepository.findById(album.getArtistId());
        Optional<Label> label = labelRepository.findById(album.getLabelId());
        List<Track> trackList = trackRepository.findAllTracksByAlbumId(album.getId());

        AlbumViewModel avm = new AlbumViewModel();
        avm.setId(album.getId());
        avm.setTitle(album.getTitle());
        avm.setReleaseDate(album.getReleaseDate());
        avm.setListPrice(album.getListPrice());
        avm.setArtist(artist.get());
        avm.setLabel(label.get());
        avm.setTracks(trackList);

        return avm;
    }

    public List<AlbumViewModel> getAllAlbums() {
        List<Album> albumList = albumRepository.findAll();

        List<AlbumViewModel> avmList = new ArrayList<>();

        for (Album album : albumList) {
            AlbumViewModel avm = buildAlbumViewModel(album);
            avmList.add(avm);
        }

        return avmList;
    }

    @Transactional
    public void updateAlbum(AlbumViewModel viewModel) {
        //update the album information
        Album album = new Album();
        album.setId(viewModel.getId());
        album.setTitle(viewModel.getTitle());
        album.setArtistId(viewModel.getArtist().getId());
        album.setLabelId(viewModel.getLabel().getId());
        album.setListPrice(viewModel.getListPrice());
        album.setReleaseDate(viewModel.getReleaseDate());
        albumRepository.save(album);

        List<Track> trackList = trackRepository.findAllTracksByAlbumId(album.getId());
        trackList.stream()
                .forEach(track -> trackRepository.deleteById(track.getId()));

        List<Track> tracks = viewModel.getTracks();
        tracks.stream()
                .forEach(t ->
                {
                    t.setAlbumId(viewModel.getId());
                    t = trackRepository.save(t);
                });
    }

    @Transactional
    public void removeAlbum(int id) {
        albumRepository.deleteById(id);
    }

    //Artist service

    public Artist saveArtist(Artist artist) {
        return artistRepository.save(artist);
    }

    public Artist getArtist(int id) {
        Optional<Artist> returnVal = artistRepository.findById(id);
        if (returnVal.isPresent()) {
            return returnVal.get();
        } else {
            return null;
        }
    }

    public List<Artist> getAllArtists() {
        return artistRepository.findAll();
    }

    public void updateArtist(Artist artist) {
        artistRepository.save(artist);
    }

    public void deleteArtist(int id) {
        artistRepository.deleteById(id);
    }

    //Label Service

    public Label saveLabel(Label label) {
        return labelRepository.save(label);
    }

    public Label getLabel(int id) {
        Optional<Label> returnVal = labelRepository.findById(id);
        if (returnVal.isPresent()) {
            return returnVal.get();
        } else {
            return null;
        }
    }

    public List<Label> getLabels() {
        return labelRepository.findAll();
    }

    public void updateLabel(Label label) {
        labelRepository.save(label);
    }

    public void deleteLabel(int id) {
        labelRepository.deleteById(id);
    }

}
