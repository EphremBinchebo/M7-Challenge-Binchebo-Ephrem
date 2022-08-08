package com.trilogyed.musicstorecatalog.repository;

import com.trilogyed.musicstorecatalog.model.Track;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface TrackRepository extends JpaRepository<Track, Integer> {
    List<Track> findAllTracksByAlbumId(int albumId);
}
