package com.mashedtomatoes.celebrity;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CharacterRepository extends CrudRepository<Character, Long> {
  Iterable<Character> findAllByCelebrity_Id(long id);
}
