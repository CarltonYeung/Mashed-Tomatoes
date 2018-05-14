package com.mashedtomatoes.media;

import java.util.Date;
import javax.persistence.*;

@Entity
@Table(name = "OscarWinnerSet")
public class OscarWinnerSet {
  private Date year;
  private Long bestPicture;
  private Long bestAnimatedFeature;
  private Long bestCinematography;
  private Long bestDocumentaryFeature;
  private Long bestFilmEditing;

  public OscarWinnerSet() {}

  public void setYear(Date year) {
    this.year = year;
  }

  public void setBestPicture(Long bestPicture) {
    this.bestPicture = bestPicture;
  }

  public void setBestAnimatedFeature(Long bestAnimatedFeature) {
    this.bestAnimatedFeature = bestAnimatedFeature;
  }

  public void setBestCinematography(Long bestCinematography) {
    this.bestCinematography = bestCinematography;
  }

  public void setBestDocumentaryFeature(Long bestDocumentaryFeature) {
    this.bestDocumentaryFeature = bestDocumentaryFeature;
  }

  public void setBestFilmEditing(Long bestFilmEditing) {
    this.bestFilmEditing = bestFilmEditing;
  }

  @Id
  @Column(nullable = false)
  public Date getYear() {
    return year;
  }

  public Long getBestPicture() {
    return bestPicture;
  }

  public Long getBestAnimatedFeature() {
    return bestAnimatedFeature;
  }

  public Long getBestCinematography() {
    return bestCinematography;
  }

  public Long getBestDocumentaryFeature() {
    return bestDocumentaryFeature;
  }

  public Long getBestFilmEditing() {
    return bestFilmEditing;
  }
}
