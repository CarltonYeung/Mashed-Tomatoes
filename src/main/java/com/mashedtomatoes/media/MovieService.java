package com.mashedtomatoes.media;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MovieService {
  @Autowired
  MovieRepository movieRepository;

  Iterable<Movie> getAllMovies(String expr) {
    if (expr == null) {
      return movieRepository.findAll();
    }
    return movieRepository.findSimilarMovies(expr);
  }

  Movie getMovieBySlug(String slug) {
    return movieRepository.findFirstBySlug(slug);
  }

  void addMovie(Movie movie) {
    movieRepository.save(movie);
  }

  void updateMovie(Movie movie) {
    addMovie(movie);
  }

  void deleteMovie(Movie movie) {
    movieRepository.delete(movie);
  }

  Boolean deleteMovieBySlug(String slug) {
    Movie movie = getMovieBySlug(slug);
    if (movie == null) {
      return false;
    }

    deleteMovie(movie);
    return true;
  }
}
