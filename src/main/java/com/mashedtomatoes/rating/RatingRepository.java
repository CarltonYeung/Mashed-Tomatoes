package com.mashedtomatoes.rating;

import com.mashedtomatoes.media.Media;
import com.mashedtomatoes.media.Movie;
import com.mashedtomatoes.user.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

  boolean existsByAuthorAndMedia(User user, Media media);

  boolean existsByAuthorAndIdAndMedia(User user, Long id, Media media);

  boolean existsById(Long id);

  Rating findFirstByAuthorAndMedia(User user, Media media);

  Rating findFirstById(Long id);
}
