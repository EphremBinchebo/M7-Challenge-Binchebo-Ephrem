package com.trilogyed.musicstorecatalog.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Table(name = "track")
public class Track implements Serializable {
    @Id
    @Column(name = "track_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "album_id")
    private int albumId;
    private String title;
    @Column(name = "run_time")
    private int runtime;


    public Track(int albumId, String title, int runtime) {
        this.albumId = albumId;
        this.title = title;
        this.runtime = runtime;
    }

    public Track(int id, int albumId, String title, int runtime) {
        this.id = id;
        this.albumId = albumId;
        this.title = title;
        this.runtime = runtime;
    }

    public Track() {
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getAlbumId() {
        return albumId;
    }

    public void setAlbumId(int albumId) {
        this.albumId = albumId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getRuntime() {
        return runtime;
    }

    public void setRuntime(int runtime) {
        this.runtime = runtime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Track)) return false;
        Track track = (Track) o;
        return id == track.id && albumId == track.albumId && runtime == track.runtime && Objects.equals(title, track.title);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, albumId, title, runtime);
    }

    @Override
    public String toString() {
        return "Track{" +
                "id=" + id +
                ", albumId=" + albumId +
                ", title='" + title + '\'' +
                ", runtime=" + runtime +
                '}';
    }
}
