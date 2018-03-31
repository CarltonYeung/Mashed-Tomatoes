package com.mashedtomatoes.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "TVShowEpisodes")
public class TVShowEpisode extends Media {
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tvShowSeasonID", nullable = false)
    @Getter @Setter
    private TVShowSeason tvShowSeason;

    @Column(nullable = false)
    @Getter @Setter
    private int episodeNo;
}
