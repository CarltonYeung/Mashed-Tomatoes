package com.mashedtomatoes.media;

import com.mashedtomatoes.http.RateRequest;
import com.mashedtomatoes.rating.RatingService;
import com.mashedtomatoes.user.User;
import com.mashedtomatoes.user.UserType;
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

    @Autowired
    MovieService movieService;

    @Autowired
    RatingService ratingService;

    @GetMapping("/api/movie")
    public Iterable<Movie> getMovies(@RequestParam(value = "expr", required = false) String expr) {
        return movieService.getAllMovies(expr);
    }

    @GetMapping("/api/movie/{slug}")
    public Movie getMovie(@PathVariable String slug) {

        Movie movie = movieService.getMovieBySlug(slug);
        movie.setCelebrities(null);
        return movie;
    }

    @DeleteMapping("/api/movie/{slug}/delete")
    public RedirectView deleteMovie(@PathVariable String slug, Model model) {
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

    @PostMapping("/api/movie/{slug}/rate")
    public void submitRating(@PathVariable String slug,
                             @Valid @RequestBody RateRequest rateRequest,
                             HttpServletRequest httpRequest, HttpServletResponse httpResponse) {
        HttpSession httpSession = httpRequest.getSession(false);
        Movie movie = movieService.getMovieBySlug(slug);
        if (httpSession == null) {
            httpResponse.setStatus(HttpStatus.SC_UNAUTHORIZED);
            return;
        }
        else if (movie == null) {
            httpResponse.setStatus(HttpStatus.SC_NOT_FOUND);
            return;
        }
        User user = (User)httpSession.getAttribute("User");
        if (user.getType() != UserType.AUDIENCE) {
            httpResponse.setStatus(HttpStatus.SC_FORBIDDEN);
            return;
        }
        ratingService.submitAudienceRating(movie, user, rateRequest.getRating(), rateRequest.getReview());
//        movieService.updateMovie(movie);
        httpResponse.setStatus(HttpStatus.SC_OK);
    }

    @PatchMapping("/api/movie/{slug}/rate/update/{ratingID}")
    public void updateRating(@PathVariable String slug, @PathVariable int ratingID,
                             @Valid @RequestBody RateRequest rateRequest,
                             HttpServletRequest httpRequest, HttpServletResponse httpResponse){

    }

    @DeleteMapping("/api/movie/{slug}/rate/delete/{ratingID}")
    public void deleteRating(@PathVariable String slug,
                             @PathVariable int ratingID,
                             HttpServletRequest httpRequest, HttpServletResponse httpResponse) {
        HttpSession httpSession = httpRequest.getSession(false);
        Movie movie = movieService.getMovieBySlug(slug);
        if (httpSession == null) {
            httpResponse.setStatus(HttpStatus.SC_FORBIDDEN);
            return;
        }
        else if (movie == null) {
            httpResponse.setStatus(HttpStatus.SC_NOT_FOUND);
            return;
        }
        User user = (User)httpSession.getAttribute("User");
        if (user.getType() != UserType.AUDIENCE) {
            httpResponse.setStatus(HttpStatus.SC_FORBIDDEN);
            return;
        }
        boolean wasDeleted = ratingService.deleteAudienceRating(movie, user, ratingID);
        if (wasDeleted) {
            movieService.updateMovie(movie);
            httpResponse.setStatus(HttpStatus.SC_OK);
        }
        else {
            httpResponse.setStatus(HttpStatus.SC_BAD_REQUEST);
        }
    }
}
