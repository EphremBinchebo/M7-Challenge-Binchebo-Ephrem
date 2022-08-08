package com.trilogyed.musicstorerecommendations.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.util.Objects;

@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Table(name = "track_recommendation ")
public class TrackRecommendation {
    @Id
    @Column(name = "track_recommendation_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "track_id")
    private int trackId;
    @Column(name = "user_id")
    private int userId;
    private boolean liked;

    public TrackRecommendation() {
    }

    public TrackRecommendation(int trackId, int userId, boolean liked) {
        this.trackId = trackId;
        this.userId = userId;
        this.liked = liked;
    }

    public TrackRecommendation(int id, int trackId, int userId, boolean liked) {
        this.id = id;
        this.trackId = trackId;
        this.userId = userId;
        this.liked = liked;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getTrackId() {
        return trackId;
    }

    public void setTrackId(int trackId) {
        this.trackId = trackId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public boolean isLiked() {
        return liked;
    }

    public void setLiked(boolean liked) {
        this.liked = liked;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TrackRecommendation)) return false;
        TrackRecommendation that = (TrackRecommendation) o;
        return id == that.id && trackId == that.trackId && userId == that.userId && liked == that.liked;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, trackId, userId, liked);
    }

    @Override
    public String toString() {
        return "TrackRecommendation{" +
                "id=" + id +
                ", trackId=" + trackId +
                ", userId=" + userId +
                ", liked=" + liked +
                '}';
    }
}
