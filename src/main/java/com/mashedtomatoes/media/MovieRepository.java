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
  List<Movie> findAll();

  Movie findFirstById(long id);

  @Query(
    value = "SELECT * FROM Movies, Media WHERE Movies.id = Media.id AND title REGEXP :expr",
    nativeQuery = true
  )
  List<Movie> findSimilarMovies(@Param("expr") String expr);

  @Query(
    value =
        "SELECT media.*, me.* "
            + "FROM Movies me, Media media WHERE media.id = me.id GROUP By me.id ORDER BY me.boxOffice DESC",
    nativeQuery = true
  )
  List<Movie> findTopBoxOfficeDesc(Pageable pageable);

  @Query(
    value =
        "Select md.*, me.* "
            + "FROM (Movies me left join Ratings rt on rt.mediaID=me.id), Media md WHERE md.id = me.id GROUP By me.id ORDER BY avg(rt.score) DESC",
    nativeQuery = true
  )
  List<Movie> findTopRatedFilms(Pageable pageable);

  @Query(
    value =
        "SELECT media.*, movie.* "
            + "FROM Movies movie, Media media WHERE movie.id=media.id AND movie.releaseDate <= :timeAhead AND movie.releaseDate >= :currentDate "
            + "GROUP By movie.id ORDER By movie.releaseDate DESC",
    nativeQuery = true
  )
  List<Movie> findComingSoonFilms(
      Pageable pageable,
      @Param("currentDate") Date currentDate,
      @Param("timeAhead") Date timeAhead);

  @Query(
    value =
        "SELECT media.*, movie.* "
            + "FROM Movies movie, Media media WHERE movie.id=media.id AND movie.releaseDate >= :timeBefore AND movie.releaseDate <= :currentDate "
            + "GROUP By movie.id ORDER By movie.releaseDate DESC",
    nativeQuery = true
  )
  List<Movie> findNowPlayingFilms(
      Pageable pageable,
      @Param("currentDate") Date currentDate,
      @Param("timeBefore") Date timeBefore);

  @Query(
    value =
        "SELECT media.*, movie.* "
            + "FROM Movies movie, Media media, OscarWinnerSet ows "
            + "WHERE movie.id = media.id AND media.id = ows.bestPictureId Group By movie.id ORDER By ows.year DESC",
    nativeQuery = true
  )
  List<Movie> findBestPictureWinner(Pageable pageable);

  List<Movie> findAllByOrderByReleaseDateDesc(Pageable pageable);

  List<Movie> findAllByGenresContainingOrderByReleaseDateDesc(Pageable pageable, Genre genre);

  List<Movie> findAllByOrderByBoxOfficeDesc(Pageable pageable);

  List<Movie> findAllByGenresContainingOrderByBoxOfficeDesc(Pageable pageable, Genre genre);

  @Query(
          value = "Select Me.*, md.* FROM (Movies me left join Ratings rt on rt.mediaID=me.id), Media md WHERE md.id = me.id GROUP By me.id ORDER BY avg(rt.score) DESC",
          nativeQuery = true
  )
  List<Movie> findFilteredMovieByMostPopular(Pageable pageable);

  @Query(
          value = "Select Me.*, md.* FROM (Movies me left join Ratings rt on rt.mediaID=me.id), Media md, mediagenres mg "
                  + "WHERE md.id = me.id AND mg.mediaId = md.id AND mg.genre = :genre GROUP By me.id ORDER BY avg(rt.score) DESC",
          nativeQuery = true
  )
  List<Movie> findFilteredMovieByMostPopularAndGenre(Pageable pageable, @Param("genre") String genre);

  @Query(
          value = "Select Me.*, md.* FROM (Movies me left join Ratings rt on rt.mediaID=me.id), Media md, CriticRatings crts "
                  + "WHERE md.id = me.id AND crts.id = rt.id GROUP By me.id ORDER BY avg(rt.score) DESC",
          nativeQuery = true
  )
  List<Movie> findFilteredMovieByCriticRating(Pageable pageable);

  @Query(
          value = "Select Me.*, md.* FROM (Movies me left join Ratings rt on rt.mediaID=me.id), Media md, mediagenres mg, CriticRatings crts "
                  + "WHERE md.id = me.id AND mg.mediaId = md.id AND crts.id = rt.id  AND mg.genre = :genre GROUP By me.id ORDER BY avg(rt.score) DESC",
          nativeQuery = true
  )
  List<Movie> findFilteredMovieByCriticRatingAndGenre(Pageable pageable, @Param("genre") String genre);

  List<Movie> findAllByReleaseDateBetweenOrderByReleaseDateDesc(Pageable page, Date bef, Date aft);

  List<Movie> findAllByReleaseDateBetweenAndGenresContainingOrderByReleaseDateDesc(Pageable page, Date bef, Date aft, Genre genre);

  List<Movie> findAllByReleaseDateBetweenOrderByBoxOfficeDesc(Pageable page, Date bef, Date aft);

  List<Movie> findAllByReleaseDateBetweenAndGenresContainingOrderByBoxOfficeDesc(Pageable page, Date bef, Date aft, Genre genre);

  @Query(
          value = "Select Me.*, md.* FROM (Movies me left join Ratings rt on rt.mediaID=me.id), Media md "
                  + "WHERE md.id = me.id AND me.releaseDate >= :beforeDate AND me.releaseDate <= :afterDate "
                  + "GROUP By me.id ORDER BY avg(rt.score) DESC",
          nativeQuery = true
  )
  List<Movie> findFilteredByDateMovieByMostPopular(Pageable pageable, @Param("beforeDate") Date beforeDate, @Param("afterDate") Date afterDate);

  @Query(
          value = "Select Me.*, md.* FROM (Movies me left join Ratings rt on rt.mediaID=me.id), Media md, mediagenres mg "
                  + "WHERE md.id = me.id AND mg.mediaId = md.id AND mg.genre = :genre AND me.releaseDate >= :beforeDate AND me.releaseDate <= :afterDate "
                  + "GROUP By me.id ORDER BY avg(rt.score) DESC",
          nativeQuery = true
  )
  List<Movie> findFilteredByDateMovieByMostPopularAndGenre(Pageable pageable, @Param("genre") String genre, @Param("beforeDate") Date beforeDate, @Param("afterDate") Date afterDate);

  @Query(
          value = "Select Me.*, md.* FROM (Movies me left join Ratings rt on rt.mediaID=me.id), Media md, CriticRatings crts "
                  + "WHERE md.id = me.id AND crts.id = rt.id AND me.releaseDate >= :beforeDate AND me.releaseDate <= :afterDate "
                  + "GROUP By me.id ORDER BY avg(rt.score) DESC",
          nativeQuery = true
  )
  List<Movie> findFilteredByDateMovieByCriticRating(Pageable pageable, @Param("beforeDate") Date beforeDate, @Param("afterDate") Date afterDate);

  @Query(
          value = "Select Me.*, md.* FROM (Movies me left join Ratings rt on rt.mediaID=me.id), Media md, mediagenres mg, CriticRatings crts " +
                  "WHERE md.id = me.id AND mg.mediaId = md.id AND crts.id = rt.id  AND me.releaseDate >= :beforeDate AND me.releaseDate <= :afterDate AND mg.genre = :genre " +
                  "GROUP By me.id ORDER BY avg(rt.score) DESC",
          nativeQuery = true
  )
  List<Movie> findFilteredDateMovieByCriticRatingAndGenre(Pageable pageable, @Param("genre") String genre, @Param("beforeDate") Date beforeDate, @Param("afterDate") Date afterDate);


  List<Movie> findAllByWriter_Id(long id);

  List<Movie> findAllByDirector_Id(long id);

  List<Movie> findAllByProducer_Id(long id);
}
