<<<<<<< HEAD
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
=======
package com.mashedtomatoes.rating;

import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public interface CriticRatingRepository extends CrudRepository<CriticRating, Long> {

    @Transactional
    void deleteById(long id);

    public Optional<CriticRating> findFirstById(long id);

    public List<CriticRating> findAllByOrderByMedia_TitleAsc(Pageable pageable);

    public List<CriticRating> findAllByOrderByUpdatedDesc(Pageable pageable);

}
>>>>>>> ba6e3eebfaacf72977facf025edb2f576b51a94d
