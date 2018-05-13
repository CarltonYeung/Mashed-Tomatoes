package com.mashedtomatoes.media;

import java.util.Date;
import javax.persistence.*;

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

  @ManyToOne(fetch= FetchType.LAZY)
  @JoinColumn(name = "bestPictureId")
  public Movie getBestPicture() {
    return bestPicture;
  }

  @ManyToOne(fetch=FetchType.LAZY)
  @JoinColumn(name = "bestAnimatedFeatureId")
  public Movie getBestAnimatedFeature() {
    return bestAnimatedFeature;
  }

  @ManyToOne(fetch=FetchType.LAZY)
  @JoinColumn(name = "bestCinematographyId")
  public Movie getBestCinematography() {
    return bestCinematography;
  }

  @ManyToOne(fetch=FetchType.LAZY)
  @JoinColumn(name = "bestDocumetaryFeatureId")
  public Movie getBestDocumentaryFeature() {
    return bestDocumentaryFeature;
  }

  @ManyToOne(fetch=FetchType.LAZY)
  @JoinColumn(name = "bestFilmEditingId")
  public Movie getBestFilmEditing() {
    return bestFilmEditing;
  }
}
