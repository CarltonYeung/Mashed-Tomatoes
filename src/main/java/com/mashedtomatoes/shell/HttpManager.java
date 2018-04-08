package com.mashedtomatoes.shell;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.mashedtomatoes.media.Movie;
import com.mashedtomatoes.shell.deserializer.CharacterTalentIdSet;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.FileCopyUtils;

import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;

@Component
public class HttpManager {
    static final String ApiBaseURL = "https://api.themoviedb.org/3";

    static final String ImageBaseURL = "https://image.tmdb.org/t/p";

    static final String BackdropSize = "/w1280";

    static final String PosterSize = "/w780";

    static final String ProfileSize = "/w185";

    @Value("${mt.moviedb.api.key}")
    private String apiKey;

    public String getMovieUrl(Integer movieId) {
        return ApiBaseURL + "/movie/" + movieId.toString();
    }

    public String getMovieCreditsUrl(Integer movieId) {
        return getMovieUrl(movieId) + "/credits";
    }

    public String getPersonUrl(Integer personId) {
        return ApiBaseURL + "/person/" + personId.toString();
    }

    public String getImageUrl(String uri, String size) {
        return ImageBaseURL + size + uri;
    }

    public String getBackdropImgUrl(String uri) {
        return getImageUrl(uri, BackdropSize);
    }

    public String getPosterImgUrl(String uri) {
        return getImageUrl(uri, PosterSize);
    }

    public String getProfileImgUrl(String uri) {
        return getImageUrl(uri, ProfileSize);
    }


    public Movie getMovie(Integer movieId) {
        return getObject(getMovieUrl(movieId), Movie.class);
    }

    public CharacterTalentIdSet getCharacterTalentIdSet(Integer movieId) {
        return getObject(getMovieCreditsUrl(movieId), CharacterTalentIdSet.class);
    }

    public JsonNode getJson(String url) {
        System.out.println(url);
        try {
            return Unirest.get(url).header("accept", "application/json").queryString("api_key", apiKey).asJson()
                    .getBody();
        } catch (UnirestException ex) {
            System.err.println(ex);
            return null;
        }
    }

    public <T> T getObject(String url, Class<? extends T> klass) {
        System.out.println(url);
        try {
            return Unirest.get(url).header("accept", "application/json").queryString("api_key", apiKey)
                    .asObject(klass).getBody();
        } catch (UnirestException ex) {
            System.err.println(ex);
            return null;
        }

    }

    public void downloadBinary(String url, String basepath, String filename) {
        try {
            HttpResponse<InputStream> response = Unirest.get(url).asBinary();
            if (filename == null) {
                String[] parts = url.split("/");
                filename = "/" + parts[parts.length - 1];
            }
            Path path = Paths.get(basepath, filename);
            File f = new File(path.toString());
            FileCopyUtils.copy(response.getBody(), new FileOutputStream(f));
        } catch (UnirestException ex) {
            System.err.println(ex);
        } catch (FileNotFoundException ex) {
            System.err.println(ex);
        } catch (IOException ex) {
            System.err.println(ex);
        }
    }

}