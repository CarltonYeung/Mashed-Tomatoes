package com.mashedtomatoes.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;


import com.mashedtomatoes.model.Celebrity;;

@Repository
public interface CelebrityRepository extends CrudRepository<Celebrity, Long> {
}