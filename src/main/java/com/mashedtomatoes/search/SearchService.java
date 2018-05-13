package com.mashedtomatoes.search;

import com.mashedtomatoes.celebrity.CelebrityRepository;
import com.mashedtomatoes.media.MovieRepository;
import com.mashedtomatoes.media.TVShowRepository;
import com.mashedtomatoes.util.FuzzyStringMatchComparator;
import com.mashedtomatoes.util.Pair;
import com.mashedtomatoes.util.Util;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
public class SearchService {
  private static final String URL_SPACE_DELIM = "+";

  @Autowired MovieRepository movieRepository;

  @Autowired TVShowRepository tvShowRepository;

  @Autowired CelebrityRepository celebrityRepository;

  private String buildRegex(String expr) {
    if (expr == null) {
      return null;
    }
    List<String> parts =
        Arrays.asList(expr.split("/" + URL_SPACE_DELIM)); // escape regex meta character
    StringBuilder builder = new StringBuilder();
    builder.append(".*(");
    builder.append(String.join("-", parts));
    builder.append(").*");
    return builder.toString();
  }

  @Cacheable("searchobjects")
  public List<Object> getAllObjectsMatching(String expr) {
    ArrayList<Object> objects = new ArrayList<>();
    if (expr == null) {
      objects.addAll(movieRepository.findAll());
      objects.addAll(tvShowRepository.findAll());
      objects.addAll(celebrityRepository.findAll());
      return objects;
    }

    String regex = buildRegex(expr);
    objects.addAll(movieRepository.findSimilarMovies(regex));
    objects.addAll(tvShowRepository.findSimilarTVShows(regex));
    objects.addAll(celebrityRepository.findSimilarCelebrities(regex));
    String originalExpr = expr.replace(URL_SPACE_DELIM, " ");
    FuzzyStringMatchComparator<Object> comparator =
        new FuzzyStringMatchComparator<>(originalExpr, Object::toString);
    objects.sort(comparator);
    return objects;
  }

  public Pair<List<Object>, Boolean> getListPage(
      List<Object> list, Integer pageNumber, Integer pageSize) {
    Integer start = pageNumber * pageSize;
    if (start >= list.size()) {
      return null;
    }

    Integer end = Util.clamp(start + pageSize, 0, list.size());
    Boolean hasNext = end != list.size();
    return new Pair<>(list.subList(start, end), hasNext);
  }
}
