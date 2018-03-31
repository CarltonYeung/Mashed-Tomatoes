package com.mashedtomatoes.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "TVShowSeasons")
public class TVShowSeason extends Media {
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tvShowID", nullable = false)
    @Getter @Setter
    private TVShow tvShow;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "tvShowSeason", cascade = CascadeType.ALL)
    @Getter @Setter
    private Set<TVShowEpisode> episodes = new HashSet<>();

    @Column(nullable = false)
    @Getter @Setter
    private int seasonNo;
}
