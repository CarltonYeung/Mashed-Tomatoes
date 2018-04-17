package com.mashedtomatoes.search;

import com.mashedtomatoes.celebrity.Celebrity;
import com.mashedtomatoes.media.Movie;
import com.mashedtomatoes.media.MovieViewController;
import com.mashedtomatoes.media.TVShow;
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
  SearchService searchService;

  @GetMapping("/search")
  public String getSearchResults(Model m,
                                 @RequestParam(value = "expr", required = false) String expr,
                                 @RequestParam(value = "page", required = false) Integer page) {

    page = (page == null) ? 0 : page;
    Iterable<Movie> movies = searchService.search(Movie.class, "title", expr, page);
    Iterable<TVShow> tvShows = searchService.search(TVShow.class, "title", expr, page);
    Iterable<Celebrity> celebrities = searchService.search(Celebrity.class, "name", expr, page);

    List<MovieViewController.ViewModel> viewMovies = new ArrayList<>();
    for (Movie movie : movies) {
      viewMovies.add(new MovieViewController.ViewModel(movie));
    }

    m.addAttribute("movies", viewMovies);
    m.addAttribute("tvShows", tvShows);
    m.addAttribute("celebrities", celebrities);

    int nMovies = searchService.count(Movie.class, "title", expr);
    int nTVShows = searchService.count(TVShow.class, "title", expr);
    int nCelebrities = searchService.count(Celebrity.class, "name", expr);
    int nTotal = nMovies + nTVShows + nCelebrities;
    int nPages = (int) Math.ceil(Double.max(nMovies, Double.max(nTVShows, nCelebrities)) / SearchService.MAX_RESULTS_PER_PAGE);
    m.addAttribute("nMovies", nMovies);
    m.addAttribute("nTVShows", nTVShows);
    m.addAttribute("nCelebrities", nCelebrities);
    m.addAttribute("nTotal", nTotal);
    m.addAttribute("nPages", nPages);

    return "search/search";
  }
}
