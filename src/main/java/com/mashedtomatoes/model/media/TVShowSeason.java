package com.mashedtomatoes.model.media;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "TVShowSeasons")
@Getter
@Setter
public class TVShowSeason extends Media {

    @ManyToOne
    @JoinColumn(name = "tvShowID", nullable = false)
    private TVShow tvShow;

    @OneToMany(mappedBy = "tvShowSeason", cascade = CascadeType.ALL)
    private Set<TVShowEpisode> episodes = new HashSet<>();

    @Column(nullable = false)
    private int seasonNo;
}
