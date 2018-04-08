package com.mashedtomatoes.user;

import com.mashedtomatoes.celebrity.Celebrity;
import com.mashedtomatoes.media.*;

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

    public void setID(long ID) {
        this.ID = ID;
    }

    public Audience getAudience() {
        return this.audience;
    }

    public void setAudience(Audience audience) {
        this.audience = audience;
    }

    public Movie getMovie() {
        return this.movie;
    }

    public void setMovie(Movie movie) {
        this.movie = movie;
    }

    public TVShow getTvShow() {
        return this.tvShow;
    }

    public void setTvShow(TVShow tvShow) {
        this.tvShow = tvShow;
    }

    public TVShowSeason getTvShowSeason() {
        return this.tvShowSeason;
    }

    public void setTvShowSeason(TVShowSeason tvShowSeason) {
        this.tvShowSeason = tvShowSeason;
    }

    public TVShowEpisode getTvShowEpisode() {
        return this.tvShowEpisode;
    }

    public void setTvShowEpisode(TVShowEpisode tvShowEpisode) {
        this.tvShowEpisode = tvShowEpisode;
    }

    public Celebrity getCelebrity() {
        return this.celebrity;
    }

    public void setCelebrity(Celebrity celebrity) {
        this.celebrity = celebrity;
    }

    public Genre getGenre() {
        return this.genre;
    }

    public void setGenre(Genre genre) {
        this.genre = genre;
    }
}
