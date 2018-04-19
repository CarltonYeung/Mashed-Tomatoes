package com.mashedtomatoes.media;

import java.util.Date;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface TVShowRepository extends CrudRepository<TVShow, Long> {

  @Override
  Iterable<TVShow> findAll();

  TVShow findFirstById(long id);

  Iterable<TVShow> findAllByCreator_Id(long id);

  // select title from TVShows, Media where TVShows.id = Media.id AND title regexp '.*(a|tr).*';
  @Query(
    value = "SELECT * FROM TVShows, Media WHERE TVShows.id = Media.id AND title REGEXP :expr",
    nativeQuery = true
  )
  List<TVShow> findSimilarTVShows(@Param("expr") String expr);

  @Query(
    value =
        "SELECT md.title, tv.id, if(rt.mediaID IS NOT NULL ,avg(rt.score),-1) "
            + "FROM (TVShows tv left join Ratings rt on tv.id = rt.mediaID), Media md WHERE md.id = tv.id GROUP By tv.id ORDER BY avg(rt.score) DESC",
    nativeQuery = true
  )
  Page<Object[]> findTopRatedTVShows(Pageable pageable);

  @Query(
    value =
        "SELECT md.title, tv.id, if(rt.mediaID IS NOT NULL, avg(rt.score), -1) FROM (TVShows tv left join Ratings rt on tv.id = rt.mediaID), Media md WHERE md.id = tv.id AND tv.startDate <= :currentDate AND tv.endDate >= :currentDate "
            + "GROUP By tv.id ORDER BY avg(rt.score) DESC",
    nativeQuery = true
  )
  Page<Object[]> findNowAiringTVShows(Pageable pageable, @Param("currentDate") Date currentDate);
}
