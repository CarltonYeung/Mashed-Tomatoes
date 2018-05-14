package com.mashedtomatoes.media;

import com.mashedtomatoes.celebrity.Celebrity;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "Movies")
public class Movie extends Media {
  private double boxOffice;
  private double budget;
  private int runTime;
  private Date releaseDate;
  private Celebrity director;
  private Celebrity producer;
  private Celebrity writer;
  private MovieTrailer movieTrailer;

  private Set<OscarWinnerSet> bestPicture;
  private Set<OscarWinnerSet> bestAnimatedFeature;
  private Set<OscarWinnerSet> bestCinematography;
  private Set<OscarWinnerSet> bestDocumentaryFeature;
  private Set<OscarWinnerSet> bestFilmEditing;

public Movie() {
    bestPicture = new HashSet<>();
    bestAnimatedFeature = new HashSet<>();
    bestCinematography = new HashSet<>();
    bestDocumentaryFeature = new HashSet<>();
    bestFilmEditing = new HashSet<>();
  }

  public void setBoxOffice(double boxOffice) {
    this.boxOffice = boxOffice;
  }

  public void setBudget(double budget) {
    this.budget = budget;
  }

  public void setRunTime(int runTime) {
    this.runTime = runTime;
  }

  public void setReleaseDate(Date releaseDate) {
    this.releaseDate = releaseDate;
  }

  public void setDirector(Celebrity director) {
    this.director = director;
  }

  public void setProducer(Celebrity producer) {
    this.producer = producer;
  }

  public void setWriter(Celebrity writer) {
    this.writer = writer;
  }

  public void setMovieTrailer(MovieTrailer movieTrailer) {
    this.movieTrailer = movieTrailer;
  }

  public double getBoxOffice() {
    return boxOffice;
  }

  public double getBudget() {
    return budget;
  }

  public int getRunTime() {
    return runTime;
  }

  public Date getReleaseDate() {
    return releaseDate;
  }

  @ManyToOne
  @JoinColumn(name = "directorId")
  public Celebrity getDirector() {
    return director;
  }

  @ManyToOne
  @JoinColumn(name = "producerId")
  public Celebrity getProducer() {
    return producer;
  }

  @ManyToOne
  @JoinColumn(name = "writerId")
  public Celebrity getWriter() {
    return writer;
  }

  @OneToOne(
    mappedBy = "movie",
    cascade = CascadeType.ALL,
    fetch = FetchType.EAGER,
    optional = false
  )
  public MovieTrailer getMovieTrailer() {
    return movieTrailer;
  }

  @OneToMany(mappedBy = "bestPicture", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
  public Set<OscarWinnerSet> getBestPicture() {
    return bestPicture;
  }

  public void setBestPicture(Set<OscarWinnerSet> bestPicture) {
    this.bestPicture = bestPicture;
  }

  @OneToMany(mappedBy = "bestAnimatedFeature", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
  public Set<OscarWinnerSet> getBestAnimatedFeature() {
    return bestAnimatedFeature;
  }

  public void setBestAnimatedFeature(Set<OscarWinnerSet> bestAnimatedFeature) {
    this.bestAnimatedFeature = bestAnimatedFeature;
  }

  @OneToMany(mappedBy = "bestCinematography", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
  public Set<OscarWinnerSet> getBestCinematography() {
    return bestCinematography;
  }

  public void setBestCinematography(Set<OscarWinnerSet> bestCinematography) {
    this.bestCinematography = bestCinematography;
  }

  @OneToMany(mappedBy = "bestDocumentaryFeature", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
  public Set<OscarWinnerSet> getBestDocumentaryFeature() {
    return bestDocumentaryFeature;
  }

  public void setBestDocumentaryFeature(Set<OscarWinnerSet> bestDocumentaryFeature) {
    this.bestDocumentaryFeature = bestDocumentaryFeature;
  }

  @OneToMany(mappedBy = "bestFilmEditing", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
  public Set<OscarWinnerSet> getBestFilmEditing() {
    return bestFilmEditing;
  }

  public void setBestFilmEditing(Set<OscarWinnerSet> bestFilmEditing) {
    this.bestFilmEditing = bestFilmEditing;
  }
}
