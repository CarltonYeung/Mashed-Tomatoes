package com.mashedtomatoes.media;

import javax.persistence.CascadeType;
import javax.persistence.FetchType;
import javax.persistence.OneToOne;
import org.hibernate.search.annotations.Indexed;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "Movies")
@Indexed
public class Movie extends Media {
  private double boxOffice;
  private double budget;
  private MovieTrailer movieTrailer;
  private BestPictureWinner bestPictureWinner;

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

	@OneToOne(mappedBy = "movie", cascade = CascadeType.ALL,
			fetch = FetchType.EAGER, optional = false)
	public MovieTrailer getMovieTrailer() {
		return movieTrailer;
	}

	public void setMovieTrailer(MovieTrailer movieTrailer) {
		this.movieTrailer = movieTrailer;
	}

	@OneToOne(mappedBy = "movie", cascade = CascadeType.ALL,
			fetch = FetchType.EAGER, optional = false)
	public BestPictureWinner getBestPictureWinner() {
		return bestPictureWinner;
	}

	public void setBestPictureWinner(BestPictureWinner bestPictureWinner) {
		this.bestPictureWinner = bestPictureWinner;
	}
}
