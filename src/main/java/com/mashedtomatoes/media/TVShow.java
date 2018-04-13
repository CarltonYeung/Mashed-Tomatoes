package com.mashedtomatoes.media;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "TVShows")
public class TVShow extends Media {

    private Set<TVShowSeason> seasons = new HashSet<>();

    public TVShow() {
    }

    @OneToMany(mappedBy = "tvShow", cascade = CascadeType.ALL)
    public Set<TVShowSeason> getSeasons() {
        return this.seasons;
    }

    public void setSeasons(Set<TVShowSeason> seasons) {
        this.seasons = seasons;
    }
}
