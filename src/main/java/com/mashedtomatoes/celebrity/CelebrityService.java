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
  public Iterable<Celebrity> getAllCelebrities(String expr, int page) {
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
