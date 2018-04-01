package com.mashedtomatoes.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "TVShowEpisodes")
@Getter @Setter
public class TVShowEpisode extends Media {

    @ManyToOne
    @JoinColumn(name = "tvShowSeasonID", nullable = false)
    private TVShowSeason tvShowSeason;

    @Column(nullable = false)
    private int episodeNo;
}
