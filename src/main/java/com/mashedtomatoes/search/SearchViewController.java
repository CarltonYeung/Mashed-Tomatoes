package com.mashedtomatoes.search;

import com.mashedtomatoes.celebrity.Celebrity;
import com.mashedtomatoes.celebrity.CelebrityService;
import com.mashedtomatoes.media.Movie;
import com.mashedtomatoes.media.MovieService;
import com.mashedtomatoes.media.MovieViewModel;
import com.mashedtomatoes.media.TVShow;
import com.mashedtomatoes.media.TVShowService;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class SearchViewController {
  @Autowired MovieService movieService;
  @Autowired TVShowService tvShowService;
  @Autowired CelebrityService celebrityService;
  @Value("${mt.files.uri}")
  private String filesUri = "/files";
  @Value("${mt.smash.threshold}")
  private int smashThreshold = 50;

  @GetMapping("/search")
  public String getSearchResults(
      Model m,
      @RequestParam(value = "expr", required = false) String expr,
      @RequestParam(value = "page", required = false) Integer page) {

    page = (page == null) ? 0 : page;
    Iterable<Movie> movies = movieService.getAllMovies(expr);
    Iterable<TVShow> tvShows = tvShowService.getAllTVShows(expr);
    Iterable<Celebrity> celebrities = celebrityService.getAllCelebrities(expr);

    List<MovieViewModel> viewMovies = new ArrayList<>();
    for (Movie movie : movies) {
      viewMovies.add(new MovieViewModel(filesUri, smashThreshold, movie));
    }

    m.addAttribute("movies", viewMovies);
    m.addAttribute("tvShows", tvShows);
    m.addAttribute("celebrities", celebrities);

    int nMovies = count(movies);
    int nTVShows = count(tvShows);
    int nCelebrities = count(celebrities);
    int nTotal = nMovies + nTVShows + nCelebrities;
    //    int nPages = (int) Math.ceil(Double.max(nMovies, Double.max(nTVShows, nCelebrities)) /
    // SearchService.MAX_RESULTS_PER_PAGE);
    m.addAttribute("nMovies", nMovies);
    m.addAttribute("nTVShows", nTVShows);
    m.addAttribute("nCelebrities", nCelebrities);
    m.addAttribute("nTotal", nTotal);
    m.addAttribute("nPages", 0);

    return "search/search";
  }

  private int count(Iterable i) {
    int count = 0;
    Iterator it = i.iterator();
    while (it.hasNext()) {
      count++;
      it.next();
    }
    return count;
  }
}
