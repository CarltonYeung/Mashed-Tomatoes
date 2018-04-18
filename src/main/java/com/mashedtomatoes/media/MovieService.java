package com.mashedtomatoes.media;

import com.mashedtomatoes.search.SearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;

@Service
public class MovieService {

  @Autowired MovieRepository movieRepository;

  @Autowired SearchService searchService;

  @Cacheable("movies")
  public Iterable<Movie> getAllMovies(String expr) {
    if (expr == null || expr.trim().length() == 0) return movieRepository.findAll();

    return searchService.search(Movie.class, "title", expr, 0);
  }

  private ArrayList<MovieRatingQuery> makeMovieRatingList(Iterator<Object[]> rows){

    ArrayList<MovieRatingQuery> movieRatingQueries = new ArrayList<MovieRatingQuery>();
    while(rows.hasNext()){
      Object [] columns = rows.next();
      MovieRatingQuery tempMovieRatingQuery = new MovieRatingQuery(((BigDecimal)columns[0]).doubleValue(),(String)columns[1],((BigInteger)columns[2]).intValue(),(double)columns[3],(Date)columns[4]);
      movieRatingQueries.add(tempMovieRatingQuery);
    }
    return movieRatingQueries;
  }

  public Iterable<MovieRatingQuery> getTopBoxOfficeMovies(int limit){
    Page<Object[]> pageMovies = movieRepository.findTopBoxOfficeDesc(PageRequest.of(0, limit));
    ArrayList<MovieRatingQuery> movieRatingQueries = makeMovieRatingList(pageMovies.iterator());
    return movieRatingQueries;
  }

  public Iterable<MovieRatingQuery> getTopRatedFilms(int limit){
    Page<Object[]> pageMovies = movieRepository.findTopRatedFilms(PageRequest.of(0, limit));
    ArrayList<MovieRatingQuery> movieRatingQueries = makeMovieRatingList(pageMovies.iterator());
    return movieRatingQueries;
  }

  public Iterable<MovieRatingQuery> getComingSoonFilms(int limit, int daysAhead){
    Date timeAhead = new Date(Instant.now().plus(daysAhead, ChronoUnit.DAYS).toEpochMilli());
    Date currentTime = new Date();
    Page<Object[]> pageMovies = movieRepository.findComingSoonFilms(PageRequest.of(0, limit), currentTime, timeAhead);
    ArrayList<MovieRatingQuery> movieRatingQueries = makeMovieRatingList(pageMovies.iterator());
    return movieRatingQueries;
  }
  public Iterable<MovieRatingQuery> getNowPlayingFilms(int limit, int daysBefore){
    Date timeBefore = new Date(Instant.now().minus(daysBefore, ChronoUnit.DAYS).toEpochMilli());
    Date currentTime = new Date();
    Page<Object[]> pageMovies = movieRepository.findNowPlayingFilms(PageRequest.of(0,limit), currentTime, timeBefore);
    ArrayList<MovieRatingQuery> movieRatingQueries = makeMovieRatingList(pageMovies.iterator());
    return movieRatingQueries;
  }
  public Iterable<MovieRatingQuery> getBestPictureWinner(int limit){
    Page<Object[]> pageMovies = movieRepository.findBestPictureWinner(PageRequest.of(0, limit));
    ArrayList<MovieRatingQuery> movieRatingQueries = makeMovieRatingList(pageMovies.iterator());
    return movieRatingQueries;
  }

  void addMovie(Movie movie) {
    movieRepository.save(movie);
  }

  Movie getMovieById(long id) {
    return movieRepository.findFirstById(id);
  }

  void updateMovie(Movie movie) {
    addMovie(movie);
  }

  void deleteMovie(Movie movie) {
    movieRepository.delete(movie);
  }

  Boolean deleteMovieById(long id) {
    Movie movie = getMovieById(id);
    if (movie == null) {
      return false;
    }

    deleteMovie(movie);
    return true;
  }
}
