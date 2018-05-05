package com.mashedtomatoes.media;

import com.mashedtomatoes.http.RateRequest;
import com.mashedtomatoes.http.ReviewReportRequest;
import com.mashedtomatoes.rating.RatingService;
import com.mashedtomatoes.user.User;
import org.apache.http.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
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

  @PostMapping("/api/movie/{id}/rate")
  public String submitRating(
      @PathVariable long id,
      @Valid @RequestBody RateRequest rateRequest,
      HttpServletRequest request,
      HttpServletResponse response) {

    HttpSession session = request.getSession(false);
    Movie movie = movieService.getMovieById(id);
    if (session == null) {
      response.setStatus(HttpStatus.SC_UNAUTHORIZED);
      return env.getProperty("user.notLoggedIn");
    } else if (movie == null) {
      response.setStatus(HttpStatus.SC_NOT_FOUND);
      return env.getProperty("media.notFound");
    }
    User user = (User) session.getAttribute("User");

     boolean isSubmitted =  ratingService.submitRating(
              movie, user, rateRequest.getRating(), rateRequest.getReview());
     if(!isSubmitted){
       response.setStatus(HttpStatus.SC_NOT_ACCEPTABLE);
       return env.getProperty("user.alreadySubmittedRating");
     }
    response.setStatus(HttpStatus.SC_OK);
    return "";
  }

  @PatchMapping("/api/movie/{id}/rate/update/{ratingID}")
  public String updateRating(
      @PathVariable long id,
      @PathVariable int ratingID,
      @Valid @RequestBody RateRequest rateRequest,
      HttpServletRequest request,
      HttpServletResponse response) {
    HttpSession httpSession = request.getSession(false);
    Movie movie = movieService.getMovieById(id);
    if(httpSession == null){
      response.setStatus(HttpStatus.SC_FORBIDDEN);
      return env.getProperty("user.notLoggedIn");
    }
    else if(movie == null){
      response.setStatus(HttpStatus.SC_NOT_FOUND);
      return env.getProperty("media.notFound");
    }
    User user = (User)httpSession.getAttribute("User");
    boolean hasUpdated = ratingService.updateRating(movie, user, rateRequest.getRating() , rateRequest.getReview());
    if(!hasUpdated){
      response.setStatus(HttpStatus.SC_UNAUTHORIZED);
      return env.getProperty("user.hasNotSubmittedRating");
    }
    response.setStatus(HttpStatus.SC_OK);
    return "";
  }

  @DeleteMapping("/api/movie/{id}/rate/delete/{ratingID}")
  public String deleteRating(
      @PathVariable long id,
      @PathVariable int ratingID,
      HttpServletRequest request,
      HttpServletResponse response) {

    HttpSession httpSession = request.getSession(false);
    Movie movie = movieService.getMovieById(id);
    boolean ratingExists = ratingService.ratingExistsById(ratingID);
    if (httpSession == null) {
      response.setStatus(HttpStatus.SC_FORBIDDEN);
      return env.getProperty("user.notLoggedIn");
    }
    else if (movie == null) {
      response.setStatus(HttpStatus.SC_NOT_FOUND);
      return env.getProperty("media.notFound");
    }
    else if(ratingExists == false){
      response.setStatus(HttpStatus.SC_NOT_FOUND);
      return env.getProperty("rating.notFound");
    }
    User user = (User) httpSession.getAttribute("User");
    boolean deleted = ratingService.deleteRating(movie, user, ratingID);
    if (!deleted) {
      response.setStatus(HttpStatus.SC_UNAUTHORIZED);
      return env.getProperty("rating.notAuthToDelete");
    }
    movieService.updateMovie(movie);
    response.setStatus(HttpStatus.SC_OK);
    return "";
  }

  @PostMapping("/api/movie/{id}/rate/report/{ratingID}")
  public String reportRating(
          @PathVariable long id,
          @PathVariable int ratingID,
          @Valid @RequestBody ReviewReportRequest reviewReportRequest,
          HttpServletRequest request,
          HttpServletResponse response){
    HttpSession httpSession = request.getSession(false);
    Movie movie = movieService.getMovieById(id);
    boolean ratingExists = ratingService.ratingExistsById(ratingID);
    if(httpSession == null){
      response.setStatus(HttpStatus.SC_FORBIDDEN);
      return env.getProperty("user.notLoggedIn");
    }
    else if(movie == null){
      response.setStatus(HttpStatus.SC_NOT_FOUND);
      return env.getProperty("media.notFound");
    }
    else if(ratingExists == false){
      response.setStatus(HttpStatus.SC_NOT_FOUND);
      return env.getProperty("rating.notFound");
    }
    boolean wasReported = ratingService.reportRating(ratingID,reviewReportRequest.getReason());
    //Check if the rating was reported
    if(!wasReported){
      response.setStatus(HttpStatus.SC_BAD_REQUEST);
      return env.getProperty("ratingReport.alreadyExists");
    }
    response.setStatus(HttpStatus.SC_OK);
    return "";

  }
}
