package com.mashedtomatoes.media;

import java.util.Optional;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface MovieRepository extends CrudRepository<Movie, Long> {
  @Override
  Iterable<Movie> findAll();

  @Override
  Optional<Movie> findById(Long id);

  Movie findFirstBySlug(String slug);

  @Query("SELECT m FROM Movie m WHERE m.title LIKE :expr")
  Iterable<Movie> findSimilarMovies(@Param("expr") String expr);

  Iterable<Movie> findAllByWriter_Id(long id);

  Iterable<Movie> findAllByDirector_Id(long id);

  Iterable<Movie> findAllByProducer_Id(long id);
}
