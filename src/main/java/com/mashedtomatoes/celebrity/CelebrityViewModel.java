package com.mashedtomatoes.celebrity;

import com.mashedtomatoes.media.Movie;
import com.mashedtomatoes.media.TVShow;
import com.mashedtomatoes.util.Util;
import java.util.List;
import java.util.stream.Collectors;

public class CelebrityViewModel extends Celebrity {
  private final List<Movie> directed;
  private final List<Movie> produced;
  private final List<Movie> written;
  private final List<TVShow> created;
  private final List<Character> movieCharacters;
  private final List<Character> tvShowCharacters;

  public CelebrityViewModel(String filesUri, Celebrity base) {
    this(filesUri, base, null, null, null, null, null);
  }

  public CelebrityViewModel(
      String filesUri,
      Celebrity base,
      List<Character> characters,
      List<Movie> directed,
      List<Movie> produced,
      List<Movie> written,
      List<TVShow> created) {
    super.setId(base.getId());
    super.setName(base.getName());
    super.setBirthday(base.getBirthday());
    super.setDeathday(base.getDeathday());
    super.setBirthplace(base.getBirthplace());
    super.setBiography(base.getBiography());
    super.setProfilePath(Util.resolveFilesUrl(filesUri, base.getProfilePath()));
    super.setPhotos(
        base.getPhotos()
            .stream()
            .map(photo -> Util.resolveFilesUrl(filesUri, photo))
            .collect(Collectors.toSet()));
    this.directed = directed;
    this.produced = produced;
    this.written = written;
    this.created = created;
    if (characters != null) {
      this.movieCharacters =
          characters
              .stream()
              .filter(character -> character.getMedia() instanceof Movie)
              .collect(Collectors.toList());
      this.tvShowCharacters =
          characters
              .stream()
              .filter(character -> character.getMedia() instanceof TVShow)
              .collect(Collectors.toList());
    } else {
      this.movieCharacters = null;
      this.tvShowCharacters = null;
    }
  }

  public List<Movie> getDirected() {
    return directed;
  }

  public List<Movie> getProduced() {
    return produced;
  }

  public List<Movie> getWritten() {
    return written;
  }

  public List<TVShow> getCreated() {
    return created;
  }

  public List<Character> getMovieCharacters() {
    return movieCharacters;
  }

  public List<Character> getTvShowCharacters() {
    return tvShowCharacters;
  }
}
