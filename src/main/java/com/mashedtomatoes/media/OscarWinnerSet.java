package com.mashedtomatoes.media;

import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import java.util.Date;
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

  @ManyToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "bestPictureId", referencedColumnName = "id")
  public Movie getBestPicture() {
    return bestPicture;
  }

  @ManyToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "bestAnimatedFeatureId", referencedColumnName = "id")
  public Movie getBestAnimatedFeature() {
    return bestAnimatedFeature;
  }

  @ManyToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "bestCinematographyId", referencedColumnName = "id")
  public Movie getBestCinematography() {
    return bestCinematography;
  }

  @ManyToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "bestDocumetaryFeatureId", referencedColumnName = "id")
  public Movie getBestDocumentaryFeature() {
    return bestDocumentaryFeature;
  }

  @ManyToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "bestFilmEditingId", referencedColumnName = "id")
  public Movie getBestFilmEditing() {
    return bestFilmEditing;
  }
}
