package com.mashedtomatoes.media;

import org.hibernate.search.annotations.Indexed;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "Movies")
@Indexed
public class Movie extends Media {
  private double boxOffice;
  private double budget;
  private String trailerPath;

  public Movie() {}

  public double getBoxOffice() {
    return boxOffice;
  }

  public void setBoxOffice(double boxOffice) {
    this.boxOffice = boxOffice;
  }

  public double getBudget() {
    return budget;
  }

  public void setBudget(double budget) {
    this.budget = budget;
  }

  public String getTrailerPath() {
    return trailerPath;
  }

  public void setTrailerPath(String trailerPath) {
    this.trailerPath = trailerPath;
  }
}
