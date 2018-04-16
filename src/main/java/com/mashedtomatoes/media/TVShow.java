package com.mashedtomatoes.media;

import java.util.HashSet;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "TVShows")
public class TVShow extends Media {
  public TVShow() {
    super.ratings = new HashSet<>();
    super.genres = new HashSet<>();
  }
}
