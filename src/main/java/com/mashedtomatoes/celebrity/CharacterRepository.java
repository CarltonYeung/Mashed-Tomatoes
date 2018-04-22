package com.mashedtomatoes.celebrity;

import java.util.List;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CharacterRepository extends CrudRepository<Character, Long> {
  List<Character> findAllByCelebrity_Id(long id);
}
