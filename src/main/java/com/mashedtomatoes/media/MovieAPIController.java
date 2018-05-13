package com.mashedtomatoes.media;

import com.mashedtomatoes.rating.RatingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

@RestController
public class MovieAPIController {
  @Autowired private MovieService movieService;
  @Autowired private RatingService ratingService;
  @Autowired private Environment env;


  @GetMapping("/api/movie")
  public Iterable<Movie> getMovies() {
    return movieService.getAllMovies();
  }

  @GetMapping("/api/movie/{id}")
  public Movie getMovie(@PathVariable long id) {
    return movieService.getMovieById(id);
  }

  @DeleteMapping("/api/movie/{id}/delete")
  public RedirectView deleteMovie(@PathVariable long id, Model model) {
    movieService.deleteMovieById(id);
    return new RedirectView("/api/movie");
  }

  @PatchMapping("/api/movie/{id}/update")
  public Movie updateMovie(@PathVariable long id, @RequestBody Movie movieIn) {

    Movie movie = movieService.getMovieById(id);
    movie.setTitle(movieIn.getTitle());
    movieService.updateMovie(movie);
    return movie;
  }

}
