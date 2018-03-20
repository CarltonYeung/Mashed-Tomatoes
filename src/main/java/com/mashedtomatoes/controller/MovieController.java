package com.mashedtomatoes.controller;

import org.springframework.ui.Model;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;

import com.mashedtomatoes.service.MovieService;

@Controller
public class MovieController {

    @Autowired
    MovieService movieService;

    @RequestMapping("/movie")
    public String getMovies(Model m) {
        m.addAttribute("movies", movieService.getAllMovies());
        return "movies";
    }

    @RequestMapping("/movie/{slug}")
    public String getMovie(@PathVariable("slug") String slug, Model m) {
        m.addAttribute("movie", movieService.getMovieBySlug(slug));
        return "movie";
    }

}