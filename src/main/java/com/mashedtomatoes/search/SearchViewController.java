package com.mashedtomatoes.search;

import com.mashedtomatoes.celebrity.Celebrity;
import com.mashedtomatoes.celebrity.CelebrityService;
import com.mashedtomatoes.media.Movie;
import com.mashedtomatoes.media.MovieService;
import com.mashedtomatoes.media.TVShow;
import com.mashedtomatoes.media.TVShowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class SearchViewController {

    @Autowired
    MovieService movieService;

    @Autowired
    TVShowService tvShowService;

    @Autowired
    CelebrityService celebrityService;

    @GetMapping("/search")
    public String getSearchResults(Model m, @RequestParam(value = "expr", required = false) String expr){
        Iterable<Movie> movies = movieService.getAllMovies(expr);
        Iterable<TVShow> tvShows = tvShowService.getAllTVShows(expr);
        Iterable<Celebrity>celebrities = celebrityService.getAllCelebrities(expr);

        m.addAttribute("movies", movies);
        m.addAttribute("tvShows", tvShows);
        m.addAttribute("celebrities", celebrities);

        return "search/search";
    }


}
