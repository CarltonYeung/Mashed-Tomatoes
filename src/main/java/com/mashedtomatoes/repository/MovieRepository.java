package com.mashedtomatoes.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

import com.mashedtomatoes.model.Movie;

@Repository
public interface MovieRepository extends CrudRepository<Movie, Long> {
    @Override
    Iterable<Movie> findAll();

    @Override
    Optional<Movie> findById(Long id);

    Movie findFirstBySlug(String slug);
}