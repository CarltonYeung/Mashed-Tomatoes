package com.mashedtomatoes.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "TVShows")
@NoArgsConstructor
@Getter @Setter
public class TVShow extends Media {

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "tvShow", cascade = CascadeType.ALL)
    private Set<TVShowSeason> seasons = new HashSet<>();
}
