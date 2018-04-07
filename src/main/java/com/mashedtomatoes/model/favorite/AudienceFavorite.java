package com.mashedtomatoes.model.favorite;

import com.mashedtomatoes.model.celebrity.Celebrity;
import com.mashedtomatoes.model.media.*;
import com.mashedtomatoes.model.user.Audience;

import javax.persistence.*;

@Entity
@Table(name = "AudienceFavorites")
public class AudienceFavorite {

    @Id
    private long ID;

    @MapsId
    @OneToOne(mappedBy = "favorites")
    @JoinColumn(name = "audienceID")
    private Audience audience;

    @ManyToOne
    @JoinColumn(name = "movie")
    private Movie movie;

    @ManyToOne
    @JoinColumn(name = "tvShow")
    private TVShow tvShow;

    @ManyToOne
    @JoinColumn(name = "tvShowSeason")
    private TVShowSeason tvShowSeason;

    @ManyToOne
    @JoinColumn(name = "tvShowEpisode")
    private TVShowEpisode tvShowEpisode;

    @ManyToOne
    @JoinColumn(name = "celebrity")
    private Celebrity celebrity;

    @Column(name = "genre", length = 32)
    @Enumerated(EnumType.STRING)
    private Genre genre;

    public long getID() {
        return this.ID;
    }

    public Audience getAudience() {
        return this.audience;
    }

    public Movie getMovie() {
        return this.movie;
    }

    public TVShow getTvShow() {
        return this.tvShow;
    }

    public TVShowSeason getTvShowSeason() {
        return this.tvShowSeason;
    }

    public TVShowEpisode getTvShowEpisode() {
        return this.tvShowEpisode;
    }

    public Celebrity getCelebrity() {
        return this.celebrity;
    }

    public Genre getGenre() {
        return this.genre;
    }

    public void setID(long ID) {
        this.ID = ID;
    }

    public void setAudience(Audience audience) {
        this.audience = audience;
    }

    public void setMovie(Movie movie) {
        this.movie = movie;
    }

    public void setTvShow(TVShow tvShow) {
        this.tvShow = tvShow;
    }

    public void setTvShowSeason(TVShowSeason tvShowSeason) {
        this.tvShowSeason = tvShowSeason;
    }

    public void setTvShowEpisode(TVShowEpisode tvShowEpisode) {
        this.tvShowEpisode = tvShowEpisode;
    }

    public void setCelebrity(Celebrity celebrity) {
        this.celebrity = celebrity;
    }

    public void setGenre(Genre genre) {
        this.genre = genre;
    }
}
