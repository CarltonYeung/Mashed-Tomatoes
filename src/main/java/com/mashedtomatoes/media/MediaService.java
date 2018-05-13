package com.mashedtomatoes.media;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class MediaService {
  @Autowired
  private MediaRepository mediaRepository;

  public Media getMediaById(long id) throws NoSuchElementException {
    Optional<Media> optional = mediaRepository.findFirstById(id);
    optional.orElseThrow(NoSuchElementException::new);
    return optional.get();
  }

  void addMedia(Media media) {
    mediaRepository.save(media);
  }

  void updateMedia(Media media) {
    addMedia(media);
  }

}
