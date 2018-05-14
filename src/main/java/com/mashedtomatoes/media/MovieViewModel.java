package com.mashedtomatoes.media;

import com.mashedtomatoes.celebrity.Celebrity;
import com.mashedtomatoes.util.Util;
import java.util.Date;

public class MovieViewModel extends MediaViewModel {
  private final double boxOffice;
  private final double budget;
  private final int runTime;
  private final Date releaseDate;
  private final Celebrity director;
  private final Celebrity producer;
  private final Celebrity writer;
  private final MovieTrailer movieTrailer;

  public MovieViewModel(String fileUri, Integer smashThreshold, Movie base) {
    super(fileUri, smashThreshold, base);
    this.releaseDate = base.getReleaseDate();
    this.runTime = base.getRunTime();
    if (base.getDirector() != null) {
      base.getDirector()
          .setProfilePath(Util.resolveFilesUrl(fileUri, base.getDirector().getProfilePath()));
    }
    this.director = base.getDirector();
    if (base.getProducer() != null) {
      base.getProducer()
          .setProfilePath(Util.resolveFilesUrl(fileUri, base.getProducer().getProfilePath()));
    }
    this.producer = base.getProducer();
    if (base.getWriter() != null) {
      base.getWriter()
          .setProfilePath(Util.resolveFilesUrl(fileUri, base.getWriter().getProfilePath()));
    }
    this.writer = base.getWriter();
    this.boxOffice = base.getBoxOffice();
    this.budget = base.getBudget();
    this.movieTrailer = base.getMovieTrailer();
    if (this.movieTrailer != null) {
      this.movieTrailer.setTrailerPath(Util.resolveFilesUrl(fileUri, this.movieTrailer.getTrailerPath()));
    }
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

  public Celebrity getDirector() {
    return director;
  }

  public Celebrity getProducer() {
    return producer;
  }

  public Celebrity getWriter() {
    return writer;
  }

  public MovieTrailer getMovieTrailer() {
    return movieTrailer;
  }
}
