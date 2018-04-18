package com.mashedtomatoes.media;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TVShowRepository extends CrudRepository<TVShow, Long> {

    @Override
    Iterable<TVShow> findAll();

    // select title from TVShows, Media where TVShows.id = Media.id AND title regexp '.*(a|tr).*';
    @Query(value = "SELECT * FROM TVShows, Media WHERE TVShows.id = Media.id AND title REGEXP :expr", nativeQuery = true)
    List<TVShow> findSimilarTVShows(@Param("expr") String expr);
}
