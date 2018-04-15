package com.mashedtomatoes.rating;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface AudienceRatingRepository extends CrudRepository<AudienceRating, Long> {
  AudienceRating findFirstById(Long id);

  @Transactional
  void deleteFirstById(Long id);

  @Transactional
  @Modifying
  @Query("DELETE FROM AudienceRating ar WHERE ar.id LIKE ?1")
  void deleteAudienceRating(@Param("expr") long expr);
}
