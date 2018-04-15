package com.mashedtomatoes.media;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.HashSet;

@Entity
@Table(name = "TVShows")
public class TVShow extends Media {

    public TVShow() {
        super.ratings = new HashSet<>();
        super.genres = new HashSet<>();
    }
}
