package com.mashedtomatoes.user;

import com.mashedtomatoes.celebrity.Celebrity;
import com.mashedtomatoes.media.*;

import javax.persistence.*;

@Entity
@Table(name = "AudienceFavorites")
public class AudienceFavorite {

    private long ID;
    private Audience audience;
    private Movie movie;
    private TVShow tvShow;
    private TVShowSeason tvShowSeason;
    private TVShowEpisode tvShowEpisode;
    private Celebrity celebrity;
    private Genre genre;

    @Id
    public long getID() {
        return ID;
    }

    @MapsId
    @OneToOne(mappedBy = "favorites")
    @JoinColumn(name = "audienceID")
    public Audience getAudience() {
        return audience;
    }

    @ManyToOne
    @JoinColumn(name = "movie")
    public Movie getMovie() {
        return movie;
    }

    @ManyToOne
    @JoinColumn(name = "tvShow")
    public TVShow getTvShow() {
        return tvShow;
    }

    @ManyToOne
    @JoinColumn(name = "tvShowSeason")
    public TVShowSeason getTvShowSeason() {
        return tvShowSeason;
    }

    @ManyToOne
    @JoinColumn(name = "tvShowEpisode")
    public TVShowEpisode getTvShowEpisode() {
        return tvShowEpisode;
    }

    @ManyToOne
    @JoinColumn(name = "celebrity")
    public Celebrity getCelebrity() {
        return celebrity;
    }

    @Column(name = "genre", length = 32)
    @Enumerated(EnumType.STRING)
    public Genre getGenre() {
        return genre;
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
