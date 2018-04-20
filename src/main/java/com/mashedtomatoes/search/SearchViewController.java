package com.mashedtomatoes.search;

import com.mashedtomatoes.celebrity.CelebrityService;
import com.mashedtomatoes.celebrity.CelebrityViewModel;
import com.mashedtomatoes.media.MovieService;
import com.mashedtomatoes.media.MovieViewModel;
import com.mashedtomatoes.media.TVShowService;
import com.mashedtomatoes.media.TVShowViewModel;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;
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

  	if (page == null) {
  		page = 0;
	  }

    if (page < 0) {
      return "error/404";
    }

    List<MovieViewModel> movies =
        StreamSupport.stream(movieService.getAllMovies(expr, page).spliterator(), false)
            .map(movie -> new MovieViewModel(filesUri, smashThreshold, movie))
            .collect(Collectors.toList());

    List<TVShowViewModel> tvShows =
        StreamSupport.stream(tvShowService.getAllTVShows(expr, page).spliterator(), false)
            .map(tvShow -> new TVShowViewModel(filesUri, smashThreshold, tvShow))
            .collect(Collectors.toList());

    List<CelebrityViewModel> celebrities =
        StreamSupport.stream(celebrityService.getAllCelebrities(expr, page).spliterator(), false)
            .map(
                celebrity ->
                    new CelebrityViewModel(filesUri, celebrity, null, null, null, null, null))
            .collect(Collectors.toList());

    m.addAttribute("movies", movies);
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
