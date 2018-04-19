package com.mashedtomatoes.media;

import com.mashedtomatoes.celebrity.Celebrity;
import java.util.Date;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

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
  private BestPictureWinner bestPictureWinner;

  public Movie() {}

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

  public void setBestPictureWinner(BestPictureWinner bestPictureWinner) {
    this.bestPictureWinner = bestPictureWinner;
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

  @OneToOne(
    mappedBy = "movie",
    cascade = CascadeType.ALL,
    fetch = FetchType.EAGER,
    optional = false
  )
  public BestPictureWinner getBestPictureWinner() {
    return bestPictureWinner;
  }
}
