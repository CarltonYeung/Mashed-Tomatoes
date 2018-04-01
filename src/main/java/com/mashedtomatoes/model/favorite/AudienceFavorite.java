package com.mashedtomatoes.model.favorite;

import com.mashedtomatoes.model.celebrity.Celebrity;
import com.mashedtomatoes.model.media.*;
import com.mashedtomatoes.model.user.Audience;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "AudienceFavorites")
@Getter
@Setter
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
}
