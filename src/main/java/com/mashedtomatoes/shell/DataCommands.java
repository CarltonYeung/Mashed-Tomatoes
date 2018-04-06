package com.mashedtomatoes.shell;

import com.mashedtomatoes.model.media.Genre;
import com.mashedtomatoes.model.media.Movie;
import com.mashedtomatoes.repository.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

import java.util.HashSet;

@ShellComponent
public class DataCommands {

    @Autowired
    MovieRepository movieRepository;

    @ShellMethod("Populate database")
    public void pop() {
        Movie movie = new Movie();
        movie.setTitle("foo");
        movie.setSlug("foo");

        HashSet<Genre> genres = new HashSet<>();
        genres.add(Genre.ACTION);
        genres.add(Genre.ADVENTURE);
        movie.setGenres(genres);
        movieRepository.save(movie);
        // return movie.getTitle();
    }
}
