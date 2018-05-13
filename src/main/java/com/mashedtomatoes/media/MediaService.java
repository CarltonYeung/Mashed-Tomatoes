package com.mashedtomatoes.media;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

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

  public Iterable<Media> getNowPlaying(int limit, int daysAhead){
    Date timeAhead = new Date(Instant.now().plus(daysAhead, ChronoUnit.DAYS).toEpochMilli());
    Date currentTime = new Date();
    List<Media> media = mediaRepository.getNowPlaying(PageRequest.of(0, limit), currentTime, timeAhead);
    return media;
  }


  void addMedia(Media media) {
    mediaRepository.save(media);
  }

  void updateMedia(Media media) {
    addMedia(media);
  }

}
