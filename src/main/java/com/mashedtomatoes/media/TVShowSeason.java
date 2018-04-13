package com.mashedtomatoes.media;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "TVShowSeasons")
public class TVShowSeason extends Media {

    private TVShow tvShow;
    private Set<TVShowEpisode> episodes = new HashSet<>();
    private int seasonNumber;

    @ManyToOne
    @JoinColumn(name = "tvShowID", nullable = false)
    public TVShow getTvShow() {
        return tvShow;
    }

    @OneToMany(mappedBy = "tvShowSeason", cascade = CascadeType.ALL)
    public Set<TVShowEpisode> getEpisodes() {
        return episodes;
    }

    @Column(nullable = false)
    public int getSeasonNumber() {
        return seasonNumber;
    }

    public void setTvShow(TVShow tvShow) {
        this.tvShow = tvShow;
    }

    public void setEpisodes(Set<TVShowEpisode> episodes) {
        this.episodes = episodes;
    }

    public void setSeasonNumber(int seasonNumber) {
        this.seasonNumber = seasonNumber;
    }
}
