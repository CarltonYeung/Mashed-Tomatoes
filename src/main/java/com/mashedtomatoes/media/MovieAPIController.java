package com.mashedtomatoes.media;

import com.mashedtomatoes.http.RateRequest;
import com.mashedtomatoes.rating.RatingService;
import com.mashedtomatoes.user.User;
import org.apache.http.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

@RestController
public class MovieAPIController {
  @Autowired private MovieService movieService;
  @Autowired private RatingService ratingService;

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

     boolean isSubmitted =  ratingService.submitRating(
              movie, user, rateRequest.getRating(), rateRequest.getReview());
     if(isSubmitted){
       response.setStatus(HttpStatus.SC_OK);
     }
     else{
       response.setStatus(HttpStatus.SC_NOT_ACCEPTABLE);
     }
  }

  @PatchMapping("/api/movie/{id}/rate/update/{ratingID}")
  public void updateRating(
      @PathVariable long id,
      @PathVariable int ratingID,
      @Valid @RequestBody RateRequest rateRequest,
      HttpServletRequest request,
      HttpServletResponse response) {
    HttpSession httpSession = request.getSession(false);
    Movie movie = movieService.getMovieById(id);
    if(httpSession == null){
      response.setStatus(HttpStatus.SC_FORBIDDEN);
      return;
    }
    else if(movie == null){
      response.setStatus(HttpStatus.SC_NOT_FOUND);
      return;
    }
    User user = (User)httpSession.getAttribute("User");
    boolean hasUpdated = ratingService.updateRating(movie, user, rateRequest.getRating() , rateRequest.getReview());
    if(hasUpdated){
      response.setStatus(HttpStatus.SC_OK);
    }
    else{
      response.setStatus(HttpStatus.SC_UNAUTHORIZED);
    }

  }

  @DeleteMapping("/api/movie/{id}/rate/delete/{ratingID}")
  public void deleteRating(
      @PathVariable long id,
      @PathVariable int ratingID,
      HttpServletRequest request,
      HttpServletResponse response) {

    HttpSession httpSession = request.getSession(false);
    Movie movie = movieService.getMovieById(id);
    boolean ratingExists = ratingService.existsById(ratingID);
    if (httpSession == null) {
      response.setStatus(HttpStatus.SC_FORBIDDEN);
      return;
    } else if (movie == null) {
      response.setStatus(HttpStatus.SC_NOT_FOUND);
      return;
    }
    else if(ratingExists == false){
      response.setStatus(HttpStatus.SC_NOT_FOUND);
      return;
    }
    User user = (User) httpSession.getAttribute("User");
    boolean deleted = ratingService.deleteRating(movie, user, ratingID);
    if (deleted) {
      movieService.updateMovie(movie);
      response.setStatus(HttpStatus.SC_OK);
    } else {
      response.setStatus(HttpStatus.SC_UNAUTHORIZED);
    }
  }
}
