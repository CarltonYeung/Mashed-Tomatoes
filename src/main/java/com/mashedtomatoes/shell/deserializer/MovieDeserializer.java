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
import java.util.stream.StreamSupport;

public class MovieDeserializer extends StdDeserializer<Movie> {
    private final static DateFormat df = new SimpleDateFormat("yy-MM-dd", Locale.ENGLISH);

    public MovieDeserializer() {
        this(null);
    }

    public MovieDeserializer(Class<?> vc) {
        super(vc);
    }

    @Override
    public Movie deserialize(JsonParser parser, DeserializationContext deserializer) {
        Movie m = new Movie();
        try {
            ObjectCodec codec = parser.getCodec();
            JsonNode rootNode = codec.readTree(parser);
            m.setTitle(rootNode.get("title").asText());
            m.setSlug(Util.slugify(m.getTitle()));
            m.setDescription(rootNode.get("overview").asText());
            m.setRunTime(rootNode.get("runtime").asInt());
            Iterable<JsonNode> nodeIteratable = () -> rootNode.get("genres").elements();
            StreamSupport.stream(nodeIteratable.spliterator(), false)
                    .forEach(node -> {
                        m.getGenres().add(Genre.valueOf(node.get("name").asText().toUpperCase()));
                    });
            m.setBackdropPath(rootNode.get("backdrop_path").asText());
            m.setPosterPath(rootNode.get("poster_path").asText());
            m.setReleaseDate(df.parse(rootNode.get("release_date").asText()));
        } catch (ParseException ex) {
            System.err.println(ex);
            // don't set release date if invalid date
        } catch (IOException ex) {
            System.err.println(ex);
            return null;
        }

        return m;
    }
}