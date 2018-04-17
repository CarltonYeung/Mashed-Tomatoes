package com.mashedtomatoes.media;

import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface MovieRepository extends CrudRepository<Movie, Long> {
  @Override
  Iterable<Movie> findAll();

  Movie findFirstById(long id);

  // select title from Movies, Media where Movies.id = Media.id AND title regexp '.*(a|tr).*';
  @Query(
    value = "SELECT * FROM Movies, Media WHERE Movies.id = Media.id AND title REGEXP :expr",
    nativeQuery = true
  )
  List<Movie> findSimilarMovies(@Param("expr") String expr);

  Iterable<Movie> findAllByWriter_Id(long id);

  Iterable<Movie> findAllByDirector_Id(long id);

  Iterable<Movie> findAllByProducer_Id(long id);
}
