package com.mashedtomatoes.media;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "MovieTrailers")
public class MovieTrailer {
  private long id;
  private Movie movie;
  private String trailerPath;

  public MovieTrailer() {}

  public void setId(long id) {
    this.id = id;
  }

  public void setMovie(Movie movie) {
    this.movie = movie;
  }

  public void setTrailerPath(String trailerPath) {
    this.trailerPath = trailerPath;
  }

  @Id
  public long getId() {
    return id;
  }

  @MapsId
  @OneToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "movieId")
  public Movie getMovie() {
    return movie;
  }

  @Column(nullable = false)
  public String getTrailerPath() {
    return trailerPath;
  }
}
