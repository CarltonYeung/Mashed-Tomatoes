package com.mashedtomatoes.model.media;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "TVShows")
public class TVShow extends Media {

    @OneToMany(mappedBy = "tvShow", cascade = CascadeType.ALL)
    private Set<TVShowSeason> seasons = new HashSet<>();

    public TVShow() {
    }

    public Set<TVShowSeason> getSeasons() {
        return this.seasons;
    }

    public void setSeasons(Set<TVShowSeason> seasons) {
        this.seasons = seasons;
    }
}
