package com.mashedtomatoes.media;

import com.mashedtomatoes.http.RateRequest;
import com.mashedtomatoes.rating.RatingService;
import com.mashedtomatoes.user.User;
import com.mashedtomatoes.user.UserType;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import org.apache.http.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.view.RedirectView;

@RestController
public class MovieAPIController {
  @Autowired private MovieService movieService;
  @Autowired private RatingService ratingService;

  @GetMapping("/api/movie")
  public Iterable<Movie> getMovies(@RequestParam(value = "expr", required = false) String expr) {
    return movieService.getAllMovies(expr);
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

  @PostMapping("/api/movie/{id}/rate")
  public void submitRating(
      @PathVariable long id,
      @Valid @RequestBody RateRequest rateRequest,
      HttpServletRequest request,
      HttpServletResponse response) {

    HttpSession session = request.getSession(false);
    Movie movie = movieService.getMovieById(id);
    if (session == null) {
      response.setStatus(HttpStatus.SC_UNAUTHORIZED);
      return;
    } else if (movie == null) {
      response.setStatus(HttpStatus.SC_NOT_FOUND);
      return;
    }
    User user = (User) session.getAttribute("User");
    if (user.getType() != UserType.AUDIENCE) {
      response.setStatus(HttpStatus.SC_FORBIDDEN);
      return;
    }
    ratingService.submitAudienceRating(
        movie, user, rateRequest.getRating(), rateRequest.getReview());
    response.setStatus(HttpStatus.SC_OK);
  }

  @PatchMapping("/api/movie/{id}/rate/update/{ratingID}")
  public void updateRating(
      @PathVariable long id,
      @PathVariable int ratingID,
      @Valid @RequestBody RateRequest rateRequest,
      HttpServletRequest request,
      HttpServletResponse response) {}

  @DeleteMapping("/api/movie/{id}/rate/delete/{ratingID}")
  public void deleteRating(
      @PathVariable long id,
      @PathVariable int ratingID,
      HttpServletRequest request,
      HttpServletResponse response) {

    HttpSession httpSession = request.getSession(false);
    Movie movie = movieService.getMovieById(id);
    if (httpSession == null) {
      response.setStatus(HttpStatus.SC_FORBIDDEN);
      return;
    } else if (movie == null) {
      response.setStatus(HttpStatus.SC_NOT_FOUND);
      return;
    }
    User user = (User) httpSession.getAttribute("User");
    if (user.getType() != UserType.AUDIENCE) {
      response.setStatus(HttpStatus.SC_FORBIDDEN);
      return;
    }
    boolean deleted = ratingService.deleteAudienceRating(movie, user, ratingID);
    if (deleted) {
      movieService.updateMovie(movie);
      response.setStatus(HttpStatus.SC_OK);
    } else {
      response.setStatus(HttpStatus.SC_BAD_REQUEST);
    }
  }
}
