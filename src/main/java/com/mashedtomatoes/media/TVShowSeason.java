package com.mashedtomatoes.media;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "TVShowSeasons")
public class TVShowSeason extends Media {

    @ManyToOne
    @JoinColumn(name = "tvShowID", nullable = false)
    private TVShow tvShow;

    @OneToMany(mappedBy = "tvShowSeason", cascade = CascadeType.ALL)
    private Set<TVShowEpisode> episodes = new HashSet<>();

    @Column(nullable = false)
    private int seasonNo;

    public TVShow getTvShow() {
        return this.tvShow;
    }

    public void setTvShow(TVShow tvShow) {
        this.tvShow = tvShow;
    }

    public Set<TVShowEpisode> getEpisodes() {
        return this.episodes;
    }

    public void setEpisodes(Set<TVShowEpisode> episodes) {
        this.episodes = episodes;
    }

    public int getSeasonNo() {
        return this.seasonNo;
    }

    public void setSeasonNo(int seasonNo) {
        this.seasonNo = seasonNo;
    }
}
