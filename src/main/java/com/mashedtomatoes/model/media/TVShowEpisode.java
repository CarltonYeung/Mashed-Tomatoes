package com.mashedtomatoes.model.media;

import javax.persistence.*;

@Entity
@Table(name = "TVShowEpisodes")
public class TVShowEpisode extends Media {

    @ManyToOne
    @JoinColumn(name = "tvShowSeasonID", nullable = false)
    private TVShowSeason tvShowSeason;

    @Column(nullable = false)
    private int episodeNo;

    public TVShowSeason getTvShowSeason() {
        return this.tvShowSeason;
    }

    public void setTvShowSeason(TVShowSeason tvShowSeason) {
        this.tvShowSeason = tvShowSeason;
    }

    public int getEpisodeNo() {
        return this.episodeNo;
    }

    public void setEpisodeNo(int episodeNo) {
        this.episodeNo = episodeNo;
    }
}
