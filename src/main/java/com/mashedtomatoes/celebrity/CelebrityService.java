package com.mashedtomatoes.celebrity;

import com.mashedtomatoes.media.Movie;
import com.mashedtomatoes.media.MovieRepository;
import com.mashedtomatoes.media.TVShow;
import com.mashedtomatoes.media.TVShowRepository;
import com.mashedtomatoes.util.FuzzyStringMatchComparator;
import com.mashedtomatoes.util.RegexBuilder;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
public class CelebrityService {
  private static final int MAX_CELEBRITY_SEARCH_COUNT = 10;
  private static final String URL_SPACE_DELIM = "+";

  @Autowired private CelebrityRepository celebrityRepository;

  @Autowired private CharacterRepository characterRepository;

  @Autowired private MovieRepository movieRepository;

  @Autowired private TVShowRepository tvShowRepository;

  public Celebrity getCelebrityById(long id) {
    return celebrityRepository.findFirstById(id);
  }

  @Cacheable("CelebrityDirectedMovies")
  public List<Movie> getDirectedMovies(long id) {
    return movieRepository.findAllByDirector_Id(id);
  }

  @Cacheable("CelebrityProducedMovies")
  public List<Movie> getProducedMovies(long id) {
    return movieRepository.findAllByProducer_Id(id);
  }

  @Cacheable("CelebrityWrittenMovies")
  public List<Movie> getWrittenMovies(long id) {
    return movieRepository.findAllByWriter_Id(id);
  }

  @Cacheable("CelebrityCreatedTVShows")
  public List<TVShow> getCreatedTVShows(long id) {
    return tvShowRepository.findAllByCreator_Id(id);
  }

  @Cacheable("CelebrityCharacters")
  public List<Character> getAllPlayedCharacters(long id) {
    return characterRepository.findAllByCelebrity_Id(id);
  }

  @Cacheable("Celebrities")
  public Iterable<Celebrity> getAllCelebrities(String expr, int page) {
    if (expr == null) {
      return celebrityRepository.findAll();
    }

    List<String> parts =
        Arrays.asList(expr.split("/" + URL_SPACE_DELIM)); // escape regex meta character
    String regex = RegexBuilder.buildMySQLRegex(parts);
    List<Celebrity> celebrities = celebrityRepository.findSimilarMovies(regex);
    String originalExpr = expr.replace(URL_SPACE_DELIM, " ");
    FuzzyStringMatchComparator<Celebrity> celebrityComparator =
        new FuzzyStringMatchComparator<>(originalExpr, Celebrity::getName);
    Collections.sort(celebrities, celebrityComparator);

    // Pagination
    int start = 0, end = celebrities.size();
    if (page > 0) {
      start = (page - 1) * MAX_CELEBRITY_SEARCH_COUNT;

      if (start >= celebrities.size()) {
        return celebrities.subList(0, 0);
      }

      end = start + MAX_CELEBRITY_SEARCH_COUNT;
      end = Integer.min(end, celebrities.size());
    }
    return celebrities.subList(start, end);
  }

  public Iterable<Celebrity> getAllCelebrities(String expr) {
    return getAllCelebrities(expr, 0);
  }
}
