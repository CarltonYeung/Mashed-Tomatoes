package com.mashedtomatoes.http;

public class ChangeFavoritesRequest {
  private String movie;
  private String tvShow;
  private String celebrity;
  private String genre;

  public ChangeFavoritesRequest() {
  }

  public String getMovie() {
    return movie;
  }

  public void setMovie(String movie) {
    this.movie = movie;
  }

  public String getTvShow() {
    return tvShow;
  }

  public void setTvShow(String tvShow) {
    this.tvShow = tvShow;
  }

  public String getCelebrity() {
    return celebrity;
  }

  public void setCelebrity(String celebrity) {
    this.celebrity = celebrity;
  }

  public String getGenre() {
    return genre;
  }

  public void setGenre(String genre) {
    this.genre = genre;
  }
}
