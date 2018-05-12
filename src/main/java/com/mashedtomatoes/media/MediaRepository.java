package com.mashedtomatoes.media;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface MediaRepository extends CrudRepository<Media, Long> {
  public Optional<Media> findFirstById(long id);

  @Query (
          value = "SELECT media.* "
                  + "FROM (Media media left join Ratings rt on media.id = rt.mediaID left join Movies movie on media.id = movie.id left join TVShows tv on media.id = tv.id left join tvshowairdates tvShowAir on tvShowAir.mediaId = tv.id) "
                  + "WHERE ((movie.releaseDate >= :beforeDate AND movie.releaseDate <= :afterDate) or (tvShowAir.airDate >= :beforeDate AND tvShowAir.airDate <= :afterDate))",
          nativeQuery = true
  )
  List<Media> getNowPlaying(Pageable pageable, @Param("beforeDate") Date beforeDate, @Param("afterDate") Date afterDate);
}
