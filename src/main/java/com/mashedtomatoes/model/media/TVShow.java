package com.mashedtomatoes.model.media;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "TVShows")
@NoArgsConstructor
@Getter
@Setter
public class TVShow extends Media {

    @OneToMany(mappedBy = "tvShow", cascade = CascadeType.ALL)
    private Set<TVShowSeason> seasons = new HashSet<>();
}
