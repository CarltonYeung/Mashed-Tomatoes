package com.mashedtomatoes.repository;

import com.mashedtomatoes.model.celebrity.Celebrity;
import com.mashedtomatoes.model.media.Movie;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface CelebrityRepository extends CrudRepository<Celebrity, Long> {
}