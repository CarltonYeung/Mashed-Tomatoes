package com.mashedtomatoes.media;

import javax.persistence.*;

@Entity
@Table(name = "TVShowEpisodes")
public class TVShowEpisode extends Media {

    private TVShowSeason tvShowSeason;
    private int episodeNumber;

    @ManyToOne
    @JoinColumn(name = "tvShowSeasonID", nullable = false)
    public TVShowSeason getTvShowSeason() {
        return tvShowSeason;
    }

    @Column(nullable = false)
    public int getEpisodeNumber() {
        return episodeNumber;
    }

    public void setTvShowSeason(TVShowSeason tvShowSeason) {
        this.tvShowSeason = tvShowSeason;
    }

    public void setEpisodeNumber(int episodeNumber) {
        this.episodeNumber = episodeNumber;
    }
}
