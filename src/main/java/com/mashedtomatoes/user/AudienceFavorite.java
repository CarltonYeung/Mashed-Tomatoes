package com.mashedtomatoes.user;

import javax.persistence.*;

@Entity
@Table(name = "AudienceFavorites")
public class AudienceFavorite {

    private long id;
    private Audience audience;
    private String movie;
    private String tvShow;
    private String celebrity;
    private String genre;

    AudienceFavorite() {}

    AudienceFavorite(Audience audience) {
        this.audience = audience;
    }

    @Id
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @MapsId
    @OneToOne
    @JoinColumn(name = "audienceID")
    public Audience getAudience() {
        return audience;
    }

    public void setAudience(Audience audience) {
        this.audience = audience;
    }

    public String getMovie() {
        return movie;
    }

    public void setMovie(String movie) {
        this.movie = movie;
    }

    public String getTvShow() {
        return tvShow;
    }

    public void setTvShow(String tvShow) {
        this.tvShow = tvShow;
    }

    public String getCelebrity() {
        return celebrity;
    }

    public void setCelebrity(String celebrity) {
        this.celebrity = celebrity;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }
}
