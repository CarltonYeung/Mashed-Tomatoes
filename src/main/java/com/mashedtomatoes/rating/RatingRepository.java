package com.mashedtomatoes.rating;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface RatingRepository extends CrudRepository<Rating, Long> {

  @Transactional
  void deleteFirstById(Long id);

  @Transactional
  @Modifying
  @Query("DELETE FROM Rating r WHERE r.id LIKE ?1")
  void deleteRating(@Param("expr") long expr);
}
