package com.mashedtomatoes.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.view.RedirectView;

import com.mashedtomatoes.model.Movie;
import com.mashedtomatoes.service.MovieService;

@RestController
public class MovieAPIController {

    @Autowired
    MovieService movieService;

    @GetMapping("/api/movie")
    public Iterable<Movie> getMovies(
        @RequestParam(value = "expr", required = false) String expr) {
        return movieService.getAllMovies(expr);
    }

    @GetMapping("/api/movie/{slug}")
    public Movie getMovie(@PathVariable String slug) {
        return movieService.getMovieBySlug(slug);
    }

    @DeleteMapping("/api/movie/{slug}/delete")
    public RedirectView deleteMovie(@PathVariable String slug, Model m) {
        movieService.deleteMovieBySlug(slug);
        return new RedirectView("/api/movie");
    }

    @PatchMapping("/api/movie/{slug}/update")
    public Movie updateMovie(
            @PathVariable String slug, 
            @RequestBody Movie movieIn) {

        Movie movie = movieService.getMovieBySlug(slug);
        movie.setTitle(movieIn.getTitle());
        movieService.updateMovie(movie);
        return movie;
    }
}