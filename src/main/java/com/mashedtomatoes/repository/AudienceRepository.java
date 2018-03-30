package com.mashedtomatoes.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;


import com.mashedtomatoes.model.Audience;;

@Repository
public interface AudienceRepository extends CrudRepository<Audience, Long> {
    @Override
    Iterable<Audience> findAll();
}