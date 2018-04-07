package com.mashedtomatoes.repository;

import com.mashedtomatoes.model.celebrity.Celebrity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface CelebrityRepository extends CrudRepository<Celebrity, Long> {
}