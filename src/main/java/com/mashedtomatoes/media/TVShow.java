package com.mashedtomatoes.media;

import org.hibernate.search.annotations.Indexed;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.HashSet;

@Entity
@Table(name = "TVShows")
@Indexed
public class TVShow extends Media {
  public TVShow() {
    super.ratings = new HashSet<>();
    super.genres = new HashSet<>();
  }
}
