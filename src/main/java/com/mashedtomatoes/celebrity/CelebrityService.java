package com.mashedtomatoes.celebrity;

import com.mashedtomatoes.search.SearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CelebrityService {

  @Autowired
  CelebrityRepository celebrityRepository;
  @Autowired
  SearchService searchService;

  public Celebrity getCelebrityByID(long ID) {
    Optional<Celebrity> optional = celebrityRepository.findById(ID);
    if (optional.isPresent()) {
      return optional.get();
    }
    return null;
  }

  @Cacheable("Celebrities")
  public Iterable<Celebrity> getAllCelebrities(String expr){
    if (expr == null || expr.trim().length() == 0)
      return celebrityRepository.findAll();

    return searchService.search(Celebrity.class, "name", expr, 0);
  }
}
