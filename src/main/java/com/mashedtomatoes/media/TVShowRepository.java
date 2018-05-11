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
  List<TVShow> findAll();

  TVShow findFirstById(long id);

  List<TVShow> findAllByCreator_Id(long id);

  // select title from TVShows, Media where TVShows.id = Media.id AND title regexp '.*(a|tr).*';
  @Query(
    value = "SELECT * FROM TVShows, Media WHERE TVShows.id = Media.id AND title REGEXP :expr",
    nativeQuery = true
  )
  List<TVShow> findSimilarTVShows(@Param("expr") String expr);

  @Query(
    value =
        "SELECT md.title, tv.id, if(rt.mediaID IS NOT NULL ,avg(rt.score),-1), md.posterPath "
            + "FROM (TVShows tv left join Ratings rt on tv.id = rt.mediaID), Media md WHERE md.id = tv.id GROUP By tv.id ORDER BY avg(rt.score) DESC",
    nativeQuery = true
  )
  Page<Object[]> findTopRatedTVShows(Pageable pageable);

  @Query(
    value =
        "SELECT md.title, tv.id, if(rt.mediaID IS NOT NULL, avg(rt.score), -1), md.posterPath "
                + "FROM (TVShows tv left join Ratings rt on tv.id = rt.mediaID), Media md WHERE md.id = tv.id AND tv.startDate <= :currentDate AND tv.endDate >= :currentDate "
                + "GROUP By tv.id ORDER BY avg(rt.score) DESC",
    nativeQuery = true
  )
  Page<Object[]> findNowAiringTVShows(Pageable pageable, @Param("currentDate") Date currentDate);

  @Query(
    value =
        "SELECT md.title, tv.id, if(rt.mediaID IS NOT NULL, avg(rt.score), -1), md.posterPath "
                + "FROM (TVShows tv left join Ratings rt on tv.id = rt.mediaID), Media md, tvshowairdates tvShowAir "
                + "WHERE md.id = tv.id AND tvShowAir.mediaId = md.id AND airDate = :currentDate "
                + "GROUP By tv.id ORDER BY avg(rt.score) DESC",
    nativeQuery = true
  )
  Page<Object[]> findTVAiringToday(Pageable pageable, @Param("currentDate") Date currentDate);

  List<TVShow> findAllByOrderByStartDateDesc(Pageable pageable);

  List<TVShow> findAllByGenresContainingOrderByStartDateDesc(Pageable pageable, Genre genre);

  List<TVShow> findAllByStartDateIsBetweenOrderByStartDateDesc(Pageable pageable, Date beforeDate, Date afterDate);

  List<TVShow> findAllByStartDateIsBetweenAndGenresContainingOrderByStartDateDesc(Pageable pageable, Date beforeDate, Date afterDate, Genre genre);

  @Query(
          value = "Select tv.*, md.* FROM (TVShows tv left join Ratings rt on rt.mediaID=tv.id), Media md "
                  + "WHERE md.id = tv.id GROUP By tv.id ORDER BY avg(rt.score) DESC",
          nativeQuery = true
  )
  List<TVShow> findTVShowByMostPopularOrderByMostPopularDesc(Pageable pageable);

  @Query(
          value = "Select tv.*, md.* FROM (TVShows tv left join Ratings rt on rt.mediaID=tv.id), Media md, mediagenres mg "
                  + "WHERE md.id = tv.id AND mg.mediaId = md.id AND mg.genre = :genre GROUP By tv.id ORDER BY avg(rt.score) DESC",
          nativeQuery = true
  )
  List<TVShow> findTVShowByMostPopularAndGenreOrderByMostPopularDesc(Pageable pageable, @Param("genre") String genre);

  @Query(
          value = "Select tv.*, md.* FROM (TVShows tv left join Ratings rt on rt.mediaID=tv.id), Media md, CriticRatings crts "
                  + "WHERE md.id = tv.id AND crts.id = rt.id GROUP By tv.id ORDER BY avg(rt.score) DESC",
          nativeQuery = true
  )
  List<TVShow> findTVShowByCriticRatingOrderByCriticRatingDesc(Pageable pageable);

  @Query(
          value = "Select tv.*, md.* FROM (TVShows tv left join Ratings rt on rt.mediaID=tv.id), Media md, mediagenres mg, CriticRatings crts "
                  + "WHERE md.id = tv.id AND crts.id = rt.id AND mg.genre = :genre GROUP By tv.id ORDER BY avg(rt.score) DESC",
          nativeQuery = true
  )
  List<TVShow> findTVShowByCriticRatingAndGenreOrderByCriticRatingDesc(Pageable pageable, @Param("genre") String genre);
}
