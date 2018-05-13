package com.mashedtomatoes.celebrity;

import com.mashedtomatoes.media.Movie;
import com.mashedtomatoes.media.MovieRepository;
import com.mashedtomatoes.media.TVShow;
import com.mashedtomatoes.media.TVShowRepository;
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
}
