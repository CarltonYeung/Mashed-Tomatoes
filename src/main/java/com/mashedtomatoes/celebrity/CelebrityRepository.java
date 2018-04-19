package com.mashedtomatoes.celebrity;

import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CelebrityRepository extends CrudRepository<Celebrity, Long> {
  Celebrity findFirstById(Long id);

  Celebrity findFirstByName(String name);

  @Query(
    value = "SELECT * FROM Celebrities WHERE Celebrities.name REGEXP :expr",
    nativeQuery = true
  )
  List<Celebrity> findSimilarMovies(@Param("expr") String expr);
}
