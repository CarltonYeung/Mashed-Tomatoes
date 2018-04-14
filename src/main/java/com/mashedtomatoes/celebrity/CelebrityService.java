package com.mashedtomatoes.celebrity;

import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CelebrityService {
  @Autowired
  CelebrityRepository celebrityRepository;

  Celebrity getCelebrityByID(long ID) {
    Optional<Celebrity> optional = celebrityRepository.findById(ID);
    if (optional.isPresent()) {
      return optional.get();
    }
    return null;
  }
}
