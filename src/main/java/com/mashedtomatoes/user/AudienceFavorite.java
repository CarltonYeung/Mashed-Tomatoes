package com.mashedtomatoes.user;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "AudienceFavorites")
public class AudienceFavorite {
  private long id;
  private Audience audience;
  private String movie;
  private String tvShow;
  private String celebrity;
  private String genre;

  AudienceFavorite() {}

  AudienceFavorite(Audience audience) {
    this.audience = audience;
  }

  public void setId(long id) {
    this.id = id;
  }

  public void setAudience(Audience audience) {
    this.audience = audience;
  }

  public void setMovie(String movie) {
    this.movie = movie;
  }

  public void setTvShow(String tvShow) {
    this.tvShow = tvShow;
  }

  public void setCelebrity(String celebrity) {
    this.celebrity = celebrity;
  }

  public void setGenre(String genre) {
    this.genre = genre;
  }

  @Id
  public long getId() {
    return id;
  }

  @MapsId
  @OneToOne
  @JoinColumn(name = "audienceID")
  public Audience getAudience() {
    return audience;
  }

  public String getMovie() {
    return movie;
  }

  public String getTvShow() {
    return tvShow;
  }

  public String getCelebrity() {
    return celebrity;
  }

  public String getGenre() {
    return genre;
  }
}
