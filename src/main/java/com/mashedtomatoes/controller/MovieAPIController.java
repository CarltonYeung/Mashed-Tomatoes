package com.mashedtomatoes.controller;

import com.mashedtomatoes.model.Rating;
import com.mashedtomatoes.repository.RatingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.view.RedirectView;

import com.mashedtomatoes.model.Movie;
import com.mashedtomatoes.repository.CelebrityRepository;
import com.mashedtomatoes.model.Celebrity;
import com.mashedtomatoes.service.MovieService;

import java.time.LocalDateTime;
import java.util.Date;

@RestController
public class MovieAPIController {

    @Autowired
    MovieService movieService;

    @Autowired
    CelebrityRepository celebRepo;

    @Autowired
    RatingRepository ratingRepo;

    @GetMapping("/api/movie")
    public Iterable<Movie> getMovies(
        @RequestParam(value = "expr", required = false) String expr) {
        // return movieService.getAllMovies(expr);
        Movie m = new Movie();
        m.setSlug("foo");
        m.setTitle("Foo");
        movieService.updateMovie(m);

        Celebrity c = new Celebrity((long) 1, "Mila Kunis");
        // Rating r = new Rating(m, LocalDateTime.now());
        celebRepo.save(c);
        // ratingRepo.save(r);
        // m.getCelebrities().add(c);
        // m.getRatings().add(r);

        movieService.updateMovie(m);
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