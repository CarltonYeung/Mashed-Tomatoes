package com.mashedtomatoes.media;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "BestPictureWinners")
public class BestPictureWinner {
  private long id;
  private Movie movie;
  private Date year;

  public BestPictureWinner() {}

  public void setId(long id) {
    this.id = id;
  }

  public void setMovie(Movie movie) {
    this.movie = movie;
  }

  public void setYear(Date year) {
    this.year = year;
  }

  @Id
  public long getId() {
    return id;
  }

  @MapsId
  @OneToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "movieId")
  public Movie getMovie() {
    return movie;
  }

  @Column(nullable = false)
  public Date getYear() {
    return year;
  }
}
