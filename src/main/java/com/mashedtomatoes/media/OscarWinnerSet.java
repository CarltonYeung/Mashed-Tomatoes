package com.mashedtomatoes.media;

import com.mashedtomatoes.celebrity.Celebrity;
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
  private Movie bestOriginalScreenplay;
  private Celebrity bestDirector;
  private Celebrity bestActor;
  private Celebrity bestActress;

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

  public void setBestOriginalScreenplay(Movie bestOriginalScreenplay) {
    this.bestOriginalScreenplay = bestOriginalScreenplay;
  }

  public void setBestDirector(Celebrity bestDirector) {
    this.bestDirector = bestDirector;
  }

  public void setBestActor(Celebrity bestActor) {
    this.bestActor = bestActor;
  }

  public void setBestActress(Celebrity bestActress) {
    this.bestActress = bestActress;
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

  @ManyToOne
  @JoinColumn(name = "bestOriginalScreenplayId")
  public Movie getBestOriginalScreenplay() {
    return bestOriginalScreenplay;
  }

  @ManyToOne
  @JoinColumn(name = "bestDirectorId")
  public Celebrity getBestDirector() {
    return bestDirector;
  }

  @ManyToOne
  @JoinColumn(name = "bestActorId")
  public Celebrity getBestActor() {
    return bestActor;
  }

  @ManyToOne
  @JoinColumn(name = "bestActressId")
  public Celebrity getBestActress() {
    return bestActress;
  }
}
