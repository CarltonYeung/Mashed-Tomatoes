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
public interface MovieRepository extends CrudRepository<Movie, Long> {
  @Override
  Iterable<Movie> findAll();

  Movie findFirstById(long id);

  // select title from Movies, Media where Movies.id = Media.id AND title regexp '.*(a|tr).*';
  @Query(
    value = "SELECT * FROM Movies, Media WHERE Movies.id = Media.id AND title REGEXP :expr",
    nativeQuery = true
  )
  List<Movie> findSimilarMovies(@Param("expr") String expr);

  @Query(
    value =
        "SELECT if(rt.mediaID IS NOT Null , avg(rt.score), -1) as avgRating, media.title, media.id, me.boxOffice, me.releaseDate "
            + "FROM (Movies me left join Ratings rt on rt.mediaID = me.id), Media media WHERE media.id = me.id GROUP By me.id ORDER BY me.boxOffice DESC",
    nativeQuery = true
  )
  Page<Object[]> findTopBoxOfficeDesc(Pageable pageable);

  @Query(
    value =
        "Select if(rt.mediaID IS NOT Null , avg(rt.score), -1)as avgRating, md.title, md.id, me.boxOffice, me.releaseDate "
            + "FROM (Movies me left join Ratings rt on rt.mediaID=me.id), Media md WHERE md.id = me.id GROUP By me.id ORDER BY avg(rt.score) DESC",
    nativeQuery = true
  )
  Page<Object[]> findTopRatedFilms(Pageable pageable);

  @Query(
    value =
        "SELECT if(rt.mediaID IS NOT NULL, avg(rt.score), -1) as avgRating, media.title, movie.id, movie.boxOffice, movie.releaseDate "
            + "FROM (Movies movie left join Ratings rt on movie.id = rt.mediaID), Media media WHERE movie.id=media.id AND movie.releaseDate <= :timeAhead AND movie.releaseDate >= :currentDate "
            + "GROUP By movie.id ORDER By movie.releaseDate DESC",
    nativeQuery = true
  )
  Page<Object[]> findComingSoonFilms(
      Pageable pageable,
      @Param("currentDate") Date currentDate,
      @Param("timeAhead") Date timeAhead);

  @Query(
    value =
        "SELECT if(rt.mediaID IS NOT NULL, avg(rt.score), -1) as avgRating, media.title, movie.id, movie.boxOffice, movie.releaseDate "
            + "FROM (Movies movie left join Ratings rt on movie.id = rt.mediaID), Media media WHERE movie.id=media.id AND movie.releaseDate >= :timeBefore AND movie.releaseDate <= :currentDate "
            + "GROUP By movie.id ORDER By movie.releaseDate DESC",
    nativeQuery = true
  )
  Page<Object[]> findNowPlayingFilms(
      Pageable pageable,
      @Param("currentDate") Date currentDate,
      @Param("timeBefore") Date timeBefore);

  @Query(
    value =
        "SELECT if(rt.mediaID IS NOT NULL, avg(rt.score), -1), media.title, movie.id, movie.boxOffice, bpw.year "
            + "FROM (Movies movie left join Ratings rt on movie.id = rt.mediaID), Media media, BestPictureWinners bpw "
            + "WHERE movie.id = media.id AND media.id = bpw.movieId Group By movie.id ORDER By bpw.year DESC",
    nativeQuery = true
  )
  Page<Object[]> findBestPictureWinner(Pageable pageable);

  Iterable<Movie> findAllByWriter_Id(long id);

  Iterable<Movie> findAllByDirector_Id(long id);

  Iterable<Movie> findAllByProducer_Id(long id);
}
