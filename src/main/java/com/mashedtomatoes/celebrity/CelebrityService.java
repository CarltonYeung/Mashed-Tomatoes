package com.mashedtomatoes.celebrity;

import com.mashedtomatoes.util.FuzzyStringMatchComparator;
import com.mashedtomatoes.util.RegexBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class CelebrityService {
  private static final int MAX_CELEBRITY_SEARCH_COUNT = 10;
  private static final String URL_SPACE_DELIM = "+";

  @Autowired
  CelebrityRepository celebrityRepository;

  public Celebrity getCelebrityByID(long ID) {
    Optional<Celebrity> optional = celebrityRepository.findById(ID);
    if (optional.isPresent()) {
      return optional.get();
    }
    return null;
  }

  @Cacheable("Celebrities")
  public Iterable<Celebrity> getAllCelebrities(String expr){
    if(expr == null){
      return celebrityRepository.findAll();
    }

    List<String> parts = Arrays.asList(expr.split("/" + URL_SPACE_DELIM)); // escape regex meta character
    String regex = RegexBuilder.buildMySQLRegex(parts);
    List<Celebrity> celebrities = celebrityRepository.findSimilarMovies(regex);
    String originalExpr = expr.replace(URL_SPACE_DELIM, " ");
    FuzzyStringMatchComparator<Celebrity> celebrityComparator =
            new FuzzyStringMatchComparator<>(originalExpr, Celebrity::getName);
    Collections.sort(celebrities, celebrityComparator);

    if (celebrities.size() < MAX_CELEBRITY_SEARCH_COUNT) {
      return celebrities;
    }

    return celebrities.subList(0, MAX_CELEBRITY_SEARCH_COUNT);

  }
}
