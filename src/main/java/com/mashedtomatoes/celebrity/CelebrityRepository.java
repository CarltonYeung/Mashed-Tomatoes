package com.mashedtomatoes.celebrity;

import java.util.Optional;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CelebrityRepository extends CrudRepository<Celebrity, Long> {
  @Override
  Optional<Celebrity> findById(Long id);

  Optional<Celebrity> findFirstByName(String name);
}
