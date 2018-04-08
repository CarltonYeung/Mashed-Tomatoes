package com.mashedtomatoes.controller.media;

import com.mashedtomatoes.service.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class MovieViewController {

    @Autowired
    MovieService movieService;

    @GetMapping("/movie")
    public String getMovies(Model m) {
        m.addAttribute("movies", movieService.getAllMovies(null));
        return "movies";
    }

    @GetMapping("/movie/{slug}")
    public String getMovie(@PathVariable String slug, Model m) {
        // m.addAttribute("movie", movieService.getMovieBySlug(slug));
        return "movie";
    }
}