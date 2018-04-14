package com.mashedtomatoes.celebrity;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface CelebrityRepository extends CrudRepository<Celebrity, Long> {

    @Override
    Optional<Celebrity> findById(Long id);

    Optional<Celebrity> findFirstByName(String name);

}