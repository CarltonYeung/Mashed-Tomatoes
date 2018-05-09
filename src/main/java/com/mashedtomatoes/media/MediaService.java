package com.mashedtomatoes.media;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.*;

@Service
public class MediaService {
  @Autowired
  private MediaRepository mediaRepository;

  public Media getMediaById(long id) throws NoSuchElementException {
    Optional<Media> optional = mediaRepository.findFirstById(id);
    optional.orElseThrow(NoSuchElementException::new);
    return optional.get();
  }

  public ArrayList<MediaRatingQuery> makeMediaRatingQueryList(Iterator<Object[]> rows){
    ArrayList<MediaRatingQuery> mediaRatingQueries = new ArrayList<MediaRatingQuery>();
    while (rows.hasNext()) {
      Object[] columns = rows.next();
      MediaRatingQuery tempMediaRatingQuery =
              new MediaRatingQuery(
                      ((BigDecimal) columns[0]).doubleValue(),
                      (String)columns[1],
                      ((BigInteger) columns[2]).intValue(),
                      (String) columns[3],
                      (String) columns[4]);
      mediaRatingQueries.add(tempMediaRatingQuery);
    }
    return mediaRatingQueries;
  }

  public Iterable<MediaRatingQuery> getNowPlaying(int limit, int daysAhead){
    Date timeAhead = new Date(Instant.now().plus(daysAhead, ChronoUnit.DAYS).toEpochMilli());
    Date currentTime = new Date();
    Page<Object[]> pageMedia = mediaRepository.getNowPlaying(PageRequest.of(0, limit), currentTime, timeAhead);
    ArrayList<MediaRatingQuery> mediaRatingQueries = makeMediaRatingQueryList(pageMedia.iterator());
    return mediaRatingQueries;
  }


  void addMedia(Media media) {
    mediaRepository.save(media);
  }

  void updateMedia(Media media) {
    addMedia(media);
  }

}
