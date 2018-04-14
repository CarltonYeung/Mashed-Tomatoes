package com.mashedtomatoes.shell;

import com.mashape.unirest.http.JsonNode;
import com.mashedtomatoes.celebrity.Celebrity;
import com.mashedtomatoes.celebrity.Character;
import com.mashedtomatoes.media.Movie;
import com.mashedtomatoes.shell.deserializer.MediaCastMember;
import com.mashedtomatoes.shell.deserializer.MediaCredits;
import com.mashedtomatoes.shell.deserializer.MediaCrewMember;
import com.mashedtomatoes.shell.deserializer.SerializationManager;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@ShellComponent
public class PopulateComponent {

  @Autowired
  DatabaseManager databaseManager;

  @Autowired
  HttpManager httpManager;

  @Autowired
  SerializationManager serializationManager;

  @ShellMethod("Populate database with data from The Movie Database API")
  public void populate(
      @ShellOption(defaultValue = "", help = "Path to download images, if not given, images won't be downloaded") String imgBasePath,
      @ShellOption(defaultValue = "1950", help = "Year to start collecting movies from") Integer startYear,
      @ShellOption(defaultValue = "2018", help = "Year to stop collecting from") Integer stopYear) {
    if (imgBasePath.equals("")) {
      addMovies(startYear, stopYear, null);
    } else {
      addMovies(startYear, stopYear, imgBasePath);
    }
  }

  //
  private boolean inSet(Celebrity celebrity, Set<Celebrity> celebrities) {
    for (Celebrity c: celebrities) {
      if (c.getName().toLowerCase().equals(celebrity.getName().toLowerCase())) {
        return true;
      }
    }
    return false;
  }

  private Celebrity addCelebrity(Integer celebrityId, String imgBasePath) {
    Celebrity celebrity = httpManager
        .getObject(httpManager.getPersonUrl(celebrityId), Celebrity.class);
    if (celebrity == null || celebrity.getName() == null) {
      return null;
    }

    Optional<Celebrity> dbCelebrity = databaseManager.getCelebrityRepository()
        .findFirstByName(celebrity.getName());

    if (dbCelebrity.isPresent()) {
      System.out.println("Celebrity already exists: " + dbCelebrity.get().getName());
      return dbCelebrity.get();
    }

    if (imgBasePath != null) {
      httpManager
          .downloadBinary(httpManager.getProfileImgUrl(celebrity.getProfileImage()), imgBasePath,
              null);
      System.out.println("Downloaded profile image for: " + celebrity.getName());
    }

    celebrity = databaseManager.getCelebrityRepository().save(celebrity);
    System.out.println("Saved celebrity: " + celebrity.getName());
    return celebrity;
  }

  private Set<Celebrity> addCast(Set<MediaCastMember> mediaCastMembers, String imgBasePath) {
    Set<Celebrity> celebrities = new HashSet<>();
    for (MediaCastMember castMember : mediaCastMembers) {
      Celebrity celebrity = addCelebrity(castMember.getTalentId(), imgBasePath);
      Character character = castMember.getCharacter();
      if (celebrity != null && !inSet(celebrity, celebrities)) {
        character.setPlayedBy(celebrity);
        databaseManager.getCharacterRepository().save(character);
        System.out.println("Saved character: " + character.getName());
        celebrities.add(celebrity);
      }
    }

    return celebrities;
  }

  private Celebrity addDirector(Set<MediaCrewMember> mediaCrewMembers, String imgBasePath) {
    Optional<MediaCrewMember> director = mediaCrewMembers
        .stream()
        .filter(mediaCrewMember -> mediaCrewMember.getJob().toLowerCase().equals("director"))
        .findFirst();

    if (director.isPresent()) {
      Celebrity celebrity = addCelebrity(director.get().getTalentId(), imgBasePath);
      if (celebrity != null) {
        System.out.println("Saved director: " + celebrity.getName());
      }
      return celebrity;
    }

    return null;
  }

  private Celebrity addProducer(Set<MediaCrewMember> mediaCrewMembers, String imgBasePath) {
    Optional<MediaCrewMember> producer = mediaCrewMembers
        .stream()
        .filter(mediaCrewMember -> mediaCrewMember.getJob().toLowerCase().equals("producer"))
        .findFirst();

    if (producer.isPresent()) {
      Celebrity celebrity = addCelebrity(producer.get().getTalentId(), imgBasePath);
      if (celebrity != null) {
        System.out.println("Saved producer: " + celebrity.getName());
      }
    }

    System.out.println("New producer found!");

    return null;
  }

  private Set<Celebrity> addWriters(Set<MediaCrewMember> mediaCrewMembers, String imgBasePath) {
    Set<MediaCrewMember> writers = mediaCrewMembers.stream()
        .filter(mediaCrewMember -> mediaCrewMember.getDepartment().toLowerCase().equals("writing"))
        .collect(Collectors.toSet());

    Set<Celebrity> celebrities = new HashSet<>();
    for (MediaCrewMember writer : writers) {
      Celebrity celebrity = addCelebrity(writer.getTalentId(), imgBasePath);
      if (celebrity != null && !inSet(celebrity, celebrities)) {
        celebrities.add(celebrity);
        System.out.println("Saved writer: " + celebrity.getName());
      }
    }

    return celebrities;
  }

  private Movie addMovie(Integer movieId, String imgBasePath) {
    Movie movie = httpManager.getMovie(movieId);

    if (movie == null) {
      System.err.println("Failed to download movie");
      return null;
    }

    if (imgBasePath != null) {
      String[] urls = {
          httpManager.getBackdropImgUrl(movie.getBackdropPath()),
          httpManager.getPosterImgUrl(movie.getPosterPath())
      };
      for (String url : urls) {
        httpManager.downloadBinary(url, imgBasePath, null);
      }
      System.out.println("Downloaded images for: " + movie.getTitle());
    }

    movie = databaseManager.getMovieRepository().save(movie);

    if (movie == null) {
      System.err.println("Failed to save movie");
      return null;
    }

    MediaCredits mediaCredits = httpManager.getMediaCredits(movieId);
    movie.setCelebrities(addCast(mediaCredits.getCast(), imgBasePath));
    movie.setDirectedBy(addDirector(mediaCredits.getCrew(), imgBasePath));
    Celebrity producer = addProducer(mediaCredits.getCrew(), imgBasePath);
    if (producer == null ) {
      System.out.println("Movie ID: " + movieId);
    }
    movie.setProducedBy(producer);
    movie.setWrittenBy(addWriters(mediaCredits.getCrew(), imgBasePath));

    System.out.println("----------------------------------------------------------------");
    movie.getCelebrities().forEach(celebrity -> System.out.println("Celeb: " + celebrity.getName()));
    // System.out.println("Producer: " + movie.getProducedBy().getName());
    movie.getWrittenBy().forEach(celebrity -> System.out.println("Writer: " + celebrity.getName()));
    System.out.println("----------------------------------------------------------------");

    System.out.println("Saved movie: " + movie.getTitle());
    movie = databaseManager.getMovieRepository().save(movie);

    return movie;
  }

  public void addMovies(Integer startYear, Integer stopYear, String imgBasePath) {
    for (int year = startYear; year <= stopYear; ++year)  {
      JsonNode node = httpManager.getTopMoviesByYear(year);
      if (node == null) {
        System.err.println("Failed to make request");
        break;
      }

      System.out.println(node.getObject().toString());
      JSONArray results = node.getObject().getJSONArray("results");
      for (int i = 0; i < results.length(); i++) {
        JSONObject jsonObject = results.getJSONObject(i);
        Integer movieId = jsonObject.getInt("id");
        addMovie(movieId, imgBasePath);
      }
    }
  }

}
