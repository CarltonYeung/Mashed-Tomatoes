package com.mashedtomatoes.shell.deserializer;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.mashedtomatoes.media.Genre;
import com.mashedtomatoes.media.Movie;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.Optional;
import java.util.stream.StreamSupport;

public class MovieDeserializer extends StdDeserializer<Movie> {

  private enum MovieDBKey {
    Title("title"),
    Description("overview"),
    Runtime("runtime"),
    Genres("genres"),
    Genre("name"),
    BackdropPath("backdrop_path"),
    PosterPath("poster_path"),
    ReleaseDate("release_date"),
    BoxOffice("revenue"),
    ProductionCompanies("production_companies"),
    ProductionCompany("name");

    private String key;

    MovieDBKey(String key) {
      this.key = key;
    }

    public String toString() {
      return this.key;
    }

  }

  private final static DateFormat df = new SimpleDateFormat("yy-MM-dd", Locale.ENGLISH);


  public MovieDeserializer() {
    this(null);
  }

  public MovieDeserializer(Class<?> vc) {
    super(vc);
  }

  @Override
  public Movie deserialize(JsonParser parser, DeserializationContext deserializer) {
    Movie movie = new Movie();

    try {
      ObjectCodec codec = parser.getCodec();
      JsonNode rootNode = codec.readTree(parser);
      System.out.println("---------------------------------------------------------------------");
      System.out.println(rootNode.toString());
      System.out.println("---------------------------------------------------------------------");
      movie.setTitle(rootNode.get(MovieDBKey.Title.toString()).asText());
      movie.setSlug(Util.slugify(movie.getTitle()));
      movie.setDescription(rootNode.get(MovieDBKey.Description.toString()).asText());
      movie.setRunTime(rootNode.get(MovieDBKey.Runtime.toString()).asInt());
      Iterable<JsonNode> genreIterable = () -> rootNode.get(MovieDBKey.Genres.toString())
          .elements();
      StreamSupport.stream(genreIterable.spliterator(), false)
          .forEach(node -> {
            try {
              movie.getGenres().add(
                  Genre.valueOf(
                      node.get(
                          MovieDBKey.Genre.toString()).asText().toUpperCase()));
            } catch (IllegalArgumentException ex) {
              // skip unsupported genre
            }
          });
      movie.setBackdropPath(rootNode.get(MovieDBKey.BackdropPath.toString()).asText());
      movie.setPosterPath(rootNode.get(MovieDBKey.PosterPath.toString()).asText());
      movie.setReleaseDate(df.parse(rootNode.get(MovieDBKey.ReleaseDate.toString()).asText()));
      movie.setBoxOffice(rootNode.get(MovieDBKey.BoxOffice.toString()).asInt());
      Iterable<JsonNode> productionCompanyIterable = () -> rootNode
          .get(MovieDBKey.ProductionCompanies.toString()).elements();
      Optional<JsonNode> productionCompanyNode = StreamSupport
          .stream(productionCompanyIterable.spliterator(), false)
          .findFirst();
      if (productionCompanyNode.isPresent()) {
        movie.setProductionCompany(
            productionCompanyNode.get().get(MovieDBKey.ProductionCompany.toString()).asText());
      }
    } catch (ParseException ex) {
      // don't set release date if invalid date (some movies have different formats)
    } catch (IOException ex) {
      return null;
    }

    return movie;
  }
}