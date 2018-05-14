package com.mashedtomatoes.media;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "OscarWinnerSet")
public class OscarWinnerSet {
  private Date year;
  private Movie bestPicture;
  private Movie bestAnimatedFeature;
  private Movie bestCinematography;
  private Movie bestDocumentaryFeature;
  private Movie bestFilmEditing;

  public OscarWinnerSet() {}

  public void setYear(Date year) {
    this.year = year;
  }

  public void setBestPicture(Movie bestPicture) {
    this.bestPicture = bestPicture;
  }

  public void setBestAnimatedFeature(Movie bestAnimatedFeature) {
    this.bestAnimatedFeature = bestAnimatedFeature;
  }

  public void setBestCinematography(Movie bestCinematography) {
    this.bestCinematography = bestCinematography;
  }

  public void setBestDocumentaryFeature(Movie bestDocumentaryFeature) {
    this.bestDocumentaryFeature = bestDocumentaryFeature;
  }

  public void setBestFilmEditing(Movie bestFilmEditing) {
    this.bestFilmEditing = bestFilmEditing;
  }

  @Id
  @Column(nullable = false)
  public Date getYear() {
    return year;
  }

  @ManyToOne
  @JoinColumn(name = "bestPicture")
  public Movie getBestPicture() {
    return bestPicture;
  }

  @ManyToOne
  @JoinColumn(name = "bestAnimatedFeature")
  public Movie getBestAnimatedFeature() {
    return bestAnimatedFeature;
  }

  @ManyToOne
  @JoinColumn(name = "bestCinematography")
  public Movie getBestCinematography() {
    return bestCinematography;
  }

  @ManyToOne
  @JoinColumn(name = "bestDocumentaryFeature")
  public Movie getBestDocumentaryFeature() {
    return bestDocumentaryFeature;
  }

  @ManyToOne
  @JoinColumn(name = "bestFilmEditing")
  public Movie getBestFilmEditing() {
    return bestFilmEditing;
  }
}
