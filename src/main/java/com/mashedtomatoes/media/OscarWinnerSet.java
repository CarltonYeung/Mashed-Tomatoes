package com.mashedtomatoes.media;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

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
  @JoinColumn(name = "bestPictureId")
  public Movie getBestPicture() {
    return bestPicture;
  }

  @ManyToOne
  @JoinColumn(name = "bestAnimatedFeatureId")
  public Movie getBestAnimatedFeature() {
    return bestAnimatedFeature;
  }

  @ManyToOne
  @JoinColumn(name = "bestCinematographyId")
  public Movie getBestCinematography() {
    return bestCinematography;
  }

  @ManyToOne
  @JoinColumn(name = "bestDocumetaryFeatureId")
  public Movie getBestDocumentaryFeature() {
    return bestDocumentaryFeature;
  }

  @ManyToOne
  @JoinColumn(name = "bestFilmEditingId")
  public Movie getBestFilmEditing() {
    return bestFilmEditing;
  }
}
