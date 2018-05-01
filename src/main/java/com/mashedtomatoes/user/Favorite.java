package com.mashedtomatoes.user;

import javax.persistence.*;

@Entity
@Table(name = "Favorites")
public class Favorite {
  private long id;
  private User user;
  private String movie;
  private String tvShow;
  private String celebrity;
  private String genre;

  Favorite() {}

  Favorite(User user) {
    this.user = user;
  }

  public void setId(long id) {
    this.id = id;
  }

  public void setUser(User user) {
    this.user = user;
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
  @JoinColumn(name = "userID")
  public User getUser() {
    return user;
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
