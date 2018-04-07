package com.mashedtomatoes.repository.celebrity;

import com.mashedtomatoes.model.celebrity.Character;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CharacterRepository extends CrudRepository<Character, Long> {
}