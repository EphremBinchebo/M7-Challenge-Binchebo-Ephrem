package com.trilogyed.musicstorecatalog.service;

import com.trilogyed.musicstorecatalog.model.Track;
import com.trilogyed.musicstorecatalog.repository.TrackRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TrackService {

    TrackRepository trackRepository;

    @Autowired
    public TrackService(TrackRepository trackRepository) {
        this.trackRepository = trackRepository;
    }

    public Track saveTrack(Track track) {
        return trackRepository.save(track);
    }

    public Track getTrack(int id) {
        Optional<Track> returnVal = trackRepository.findById(id);
        if (returnVal.isPresent()) {
            return returnVal.get();
        } else {
            return null;
        }
    }

    public List<Track> getAllTracks() {
        return trackRepository.findAll();
    }

    public void updateTrack(Track track) {
        if (this.getTrack(track.getId()) == null)
            throw new IllegalArgumentException("No such Track to update.");
        trackRepository.save(track);
    }

    public void deleteTrackById(int id) {
        trackRepository.deleteById(id);
    }

    public List<Track> getTrackByAlbumId(int albumId) {
        return trackRepository.findAllTracksByAlbumId(albumId);
    }
}
