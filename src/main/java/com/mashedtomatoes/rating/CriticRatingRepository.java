package com.mashedtomatoes.rating;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CriticRatingRepository extends CrudRepository<CriticRating, Long> {

}
