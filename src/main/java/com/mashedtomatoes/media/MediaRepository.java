package com.mashedtomatoes.media;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MediaRepository extends CrudRepository<Media, Long> {
  public Optional<Media> findFirstById(long id);
}
