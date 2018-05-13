package com.mashedtomatoes.rating;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
public interface CriticRatingRepository extends CrudRepository<CriticRating, Long> {

    @Transactional
    void deleteById(long id);

    public Optional<CriticRating> findFirstById(long id);

}
