package com.mashedtomatoes.repository;

import com.mashedtomatoes.model.Rating;
import org.springframework.data.repository.CrudRepository;

public interface RatingRepository extends CrudRepository<Rating, Long> {
}
