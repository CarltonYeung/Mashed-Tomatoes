package com.mashedtomatoes.media;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
public class MovieService {

  @Autowired MovieRepository movieRepository;

  public Iterable<Movie> getAllMovies() {
    return movieRepository.findAll();
  }

  private ArrayList<MovieRatingQuery> makeMovieRatingList(Iterator<Object[]> rows) {

    ArrayList<MovieRatingQuery> movieRatingQueries = new ArrayList<MovieRatingQuery>();
    while (rows.hasNext()) {
      Object[] columns = rows.next();
      MovieRatingQuery tempMovieRatingQuery =
          new MovieRatingQuery(
              ((BigDecimal) columns[0]).doubleValue(),
              (String) columns[1],
              ((BigInteger) columns[2]).intValue(),
              (double) columns[3],
              (Date) columns[4],
              (String) columns[5]);
      movieRatingQueries.add(tempMovieRatingQuery);
    }
    return movieRatingQueries;
  }

  public Iterable<MovieRatingQuery> getTopBoxOfficeMovies(int limit) {
    Page<Object[]> pageMovies = movieRepository.findTopBoxOfficeDesc(PageRequest.of(0, limit));
    ArrayList<MovieRatingQuery> movieRatingQueries = makeMovieRatingList(pageMovies.iterator());
    return movieRatingQueries;
  }

  public Iterable<MovieRatingQuery> getTopRatedFilms(int limit) {
    Page<Object[]> pageMovies = movieRepository.findTopRatedFilms(PageRequest.of(0, limit));
    ArrayList<MovieRatingQuery> movieRatingQueries = makeMovieRatingList(pageMovies.iterator());
    return movieRatingQueries;
  }

  public Iterable<MovieRatingQuery> getComingSoonFilms(int limit, int daysAhead) {
    Date timeAhead = new Date(Instant.now().plus(daysAhead, ChronoUnit.DAYS).toEpochMilli());
    Date currentTime = new Date();
    Page<Object[]> pageMovies =
        movieRepository.findComingSoonFilms(PageRequest.of(0, limit), currentTime, timeAhead);
    ArrayList<MovieRatingQuery> movieRatingQueries = makeMovieRatingList(pageMovies.iterator());
    return movieRatingQueries;
  }

  public Iterable<MovieRatingQuery> getNowPlayingFilms(int limit, int daysBefore) {
    Date timeBefore = new Date(Instant.now().minus(daysBefore, ChronoUnit.DAYS).toEpochMilli());
    Date currentTime = new Date();
    Page<Object[]> pageMovies =
        movieRepository.findNowPlayingFilms(PageRequest.of(0, limit), currentTime, timeBefore);
    ArrayList<MovieRatingQuery> movieRatingQueries = makeMovieRatingList(pageMovies.iterator());
    return movieRatingQueries;
  }

  public Iterable<MovieRatingQuery> getBestPictureWinner(int limit) {
    Page<Object[]> pageMovies = movieRepository.findBestPictureWinner(PageRequest.of(0, limit));
    ArrayList<MovieRatingQuery> movieRatingQueries = makeMovieRatingList(pageMovies.iterator());
    return movieRatingQueries;
  }

  private Iterable<Movie> getFilteredMoviesByTimeInterval(String genre, String sort, int page, int limit, Date beforeTime, Date afterTime){
    Iterable<Movie> movies = new ArrayList<Movie>();
    switch (sort){
      case "release-date":
        if(genre.equals("all"))
          movies = movieRepository.findAllByReleaseDateBetweenOrderByReleaseDateDesc(PageRequest.of(page, limit),beforeTime,afterTime);
        else
          movies = movieRepository.findAllByReleaseDateBetweenAndGenresContainingOrderByReleaseDateDesc(PageRequest.of(page, limit),beforeTime,afterTime, Genre.valueOf(genre));
        break;
      case "most-popular":
        if(genre.equals("all"))
          movies = movieRepository.findFilteredByDateMovieByMostPopular(PageRequest.of(page, limit),beforeTime,afterTime);
        else
          movies = movieRepository.findFilteredByDateMovieByMostPopularAndGenre(PageRequest.of(page, limit), Genre.valueOf(genre).getName(),beforeTime,afterTime);
        break;
      case "critic-rating":
        if(genre.equals("all"))
          movies = movieRepository.findFilteredByDateMovieByCriticRating(PageRequest.of(page, limit),beforeTime,afterTime);
        else
          movies = movieRepository.findFilteredDateMovieByCriticRatingAndGenre(PageRequest.of(page, limit), Genre.valueOf(genre).getName(),beforeTime,afterTime);
        break;
      case "top-box-office":
        if(genre.equals("all"))
          movies = movieRepository.findAllByReleaseDateBetweenOrderByBoxOfficeDesc(PageRequest.of(page, limit),beforeTime,afterTime);
        else
          movies = movieRepository.findAllByReleaseDateBetweenAndGenresContainingOrderByBoxOfficeDesc(PageRequest.of(page, limit),beforeTime,afterTime, Genre.valueOf(genre));
        break;
    }
    return movies;
  }

  public Iterable<Movie> getFilteredMoviesByNewReleases(String genre, String sort, int page, int limit, int daysBefore){
    Date timeBefore = new Date(Instant.now().minus(daysBefore, ChronoUnit.DAYS).toEpochMilli());
    Date currentTime = new Date();
    return getFilteredMoviesByTimeInterval(genre, sort, page, limit, timeBefore, currentTime);
  }

  public Iterable<Movie> getFilteredMoviesByComingSoon(String genre, String sort, int page, int limit, int daysAhead){
    Date timeAhead = new Date(Instant.now().plus(daysAhead, ChronoUnit.DAYS).toEpochMilli());
    Date currentTime = new Date();
    return getFilteredMoviesByTimeInterval(genre, sort, page, limit, currentTime, timeAhead);
  }

  public Iterable<Movie> getFilteredMoviesByOpeningThisWeek(String genre, String sort, int page, int limit){
    Iterable<Movie> movies = new ArrayList<Movie>();
    Calendar calendar = Calendar.getInstance();
    calendar.setTime(new Date());
    int month = calendar.get(Calendar.MONTH);
    int year = calendar.get(Calendar.YEAR);
    int weekOfMonth = calendar.get(Calendar.WEEK_OF_MONTH);
    int firstDayOfWeek = calendar.getFirstDayOfWeek();
    calendar.clear();
    calendar.set(Calendar.MONTH, month);
    calendar.set(Calendar.YEAR, year);
    calendar.set(Calendar.WEEK_OF_MONTH, weekOfMonth);
    calendar.set(Calendar.DAY_OF_WEEK, firstDayOfWeek);
    Date beforeDate = calendar.getTime();
    calendar.add(Calendar.DATE, 6);
    Date afterDate = calendar.getTime();
    return getFilteredMoviesByTimeInterval(genre, sort, page, limit, beforeDate, afterDate);
  }

  public Iterable<Movie> getFilteredMovies(String genre, String sort, int page, int limit){
    Iterable<Movie> movies = new ArrayList<Movie>();
    switch (sort){
      case "release-date":
        if(genre.equals("all"))
          movies = movieRepository.findAllByOrderByReleaseDateDesc(PageRequest.of(page, limit));
        else
          movies = movieRepository.findAllByGenresContainingOrderByReleaseDateDesc(PageRequest.of(page, limit), Genre.valueOf(genre));
        break;
      case "most-popular":
        if(genre.equals("all"))
          movies = movieRepository.findFilteredMovieByMostPopular(PageRequest.of(page,limit));
        else
          movies = movieRepository.findFilteredMovieByMostPopularAndGenre(PageRequest.of(page, limit), Genre.valueOf(genre).getName());
        break;
      case "critic-rating":
        if(genre.equals("all"))
          movies = movieRepository.findFilteredMovieByCriticRating(PageRequest.of(page, limit));
        else
          movies = movieRepository.findFilteredMovieByCriticRatingAndGenre(PageRequest.of(page, limit), Genre.valueOf(genre).getName());
        break;
      case "top-box-office":
        if(genre.equals("all"))
          movies = movieRepository.findAllByOrderByBoxOfficeDesc(PageRequest.of(page, limit));
        else
          movies = movieRepository.findAllByGenresContainingOrderByBoxOfficeDesc(PageRequest.of(page, limit), Genre.valueOf(genre));
        break;
    }
    return movies;
  }

  Movie getMovieById(long id) {
    return movieRepository.findFirstById(id);
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

  Boolean deleteMovieById(long id) {
    Movie movie = getMovieById(id);
    if (movie == null) {
      return false;
    }

    deleteMovie(movie);
    return true;
  }
}
