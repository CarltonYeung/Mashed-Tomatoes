package com.mashedtomatoes.media;

import com.mashedtomatoes.search.SearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
public class MovieService {

  @Autowired
  MovieRepository movieRepository;

  @Autowired
  SearchService searchService;

  @Cacheable("movies")
  public Iterable<Movie> getAllMovies(String expr) {
    if (expr == null || expr.trim().length() == 0)
      return movieRepository.findAll();

    return searchService.search(Movie.class, "title", expr, 0);
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
