package com.mashedtomatoes.media;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

@RestController
public class MovieAPIController {

    @Autowired
    MovieService movieService;

    @GetMapping("/api/movie")
    public Iterable<Movie> getMovies(@RequestParam(value = "expr", required = false) String expr) {
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
    public Movie updateMovie(@PathVariable String slug, @RequestBody Movie movieIn) {
        Movie movie = movieService.getMovieBySlug(slug);
        movie.setTitle(movieIn.getTitle());
        movieService.updateMovie(movie);
        return movie;
    }
}