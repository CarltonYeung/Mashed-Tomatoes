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

    private Set<TVShowSeason> seasons;

    public TVShow() {
        super.celebrities = new HashSet<>();
        super.ratings = new HashSet<>();
        super.genres = new HashSet<>();
        this.seasons = new HashSet<>();
    }

    @OneToMany(mappedBy = "tvShow", cascade = CascadeType.ALL)
    public Set<TVShowSeason> getSeasons() {
        return this.seasons;
    }

    public void setSeasons(Set<TVShowSeason> seasons) {
        this.seasons = seasons;
    }
}
