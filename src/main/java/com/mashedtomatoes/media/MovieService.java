package com.mashedtomatoes.media;

import com.mashedtomatoes.util.FuzzyStringMatchComparator;
import com.mashedtomatoes.util.RegexBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Service
public class MovieService {
  private static final int MAX_MOVIE_SEARCH_COUNT = 10;
  private static final String URL_SPACE_DELIM = "+";

  @Autowired
  MovieRepository movieRepository;

  @Cacheable("movies")
  public Iterable<Movie> getAllMovies(String expr) {
//    try {
//      Thread.sleep(3000L);
//    } catch (InterruptedException e) {
//      throw new IllegalStateException(e);
//    } // For testing to cache

    if (expr == null) {
      return movieRepository.findAll();
    }

    List<String> parts = Arrays.asList(expr.split("/" + URL_SPACE_DELIM)); // escape regex meta character
    String regex = RegexBuilder.buildMySQLRegex(parts);
    List<Movie> movies = movieRepository.findSimilarMovies(regex);
    String originalExpr = expr.replace(URL_SPACE_DELIM, " ");
    FuzzyStringMatchComparator<Movie> movieComparator =
        new FuzzyStringMatchComparator<>(originalExpr, Movie::getTitle);
    Collections.sort(movies, movieComparator);

    if (movies.size() < MAX_MOVIE_SEARCH_COUNT) {
      return movies;
    }

    return movies.subList(0, MAX_MOVIE_SEARCH_COUNT);
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
