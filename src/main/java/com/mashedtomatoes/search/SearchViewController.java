package com.mashedtomatoes.search;

import com.mashedtomatoes.celebrity.Celebrity;
import com.mashedtomatoes.celebrity.CelebrityService;
import com.mashedtomatoes.media.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

@Controller
public class SearchViewController {
  @Autowired
  MovieService movieService;
  @Autowired
  TVShowService tvShowService;
  @Autowired
  CelebrityService celebrityService;

  @GetMapping("/search")
  public String getSearchResults(Model m,
                                 @RequestParam(value = "expr", required = false) String expr,
                                 @RequestParam(value = "page", required = false) Integer page) {

    page = (page == null) ? 0 : page;
    Iterable<Movie> movies = movieService.getAllMovies(expr, page);
    Iterable<TVShow> tvShows = tvShowService.getAllTVShows(expr, page);
    Iterable<Celebrity> celebrities = celebrityService.getAllCelebrities(expr, page);

    List<MovieViewController.ViewModel> viewMovies = new ArrayList<>();
    for (Movie movie : movies) {
      viewMovies.add(new MovieViewController.ViewModel(movie));
    }

    m.addAttribute("movies", viewMovies);
    m.addAttribute("tvShows", tvShows);
    m.addAttribute("celebrities", celebrities);

    return "search/search";
  }
}
