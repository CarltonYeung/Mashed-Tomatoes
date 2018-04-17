package com.mashedtomatoes.search;

import com.mashedtomatoes.celebrity.Celebrity;
import com.mashedtomatoes.celebrity.CelebrityService;
import com.mashedtomatoes.media.*;
import org.hibernate.search.jpa.FullTextEntityManager;
import org.hibernate.search.jpa.Search;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;

@Controller
public class SearchViewController {

  @Autowired MovieService movieService;

  @Autowired TVShowService tvShowService;

  @Autowired CelebrityService celebrityService;

  @Autowired EntityManager em;

  @GetMapping("/search")
  public String getSearchResults(Model m, @RequestParam(value = "expr", required = false) String expr) {
    Iterable<Movie> movies = movieService.getAllMovies(expr);
    Iterable<TVShow> tvShows = tvShowService.getAllTVShows(expr);
    Iterable<Celebrity> celebrities = celebrityService.getAllCelebrities(expr);

    List<MovieViewController.ViewModel> viewMovies = new ArrayList<>();
    for (Movie movie : movies) {
      viewMovies.add(new MovieViewController.ViewModel(movie));
    }

    m.addAttribute("movies", viewMovies);
    m.addAttribute("tvShows", tvShows);
    m.addAttribute("celebrities", celebrities);

    return "search/search";
  }

  @GetMapping("/initializeHibernateSearchLuceneIndex")
  public String initializeLuceneIndex() throws InterruptedException {
    FullTextEntityManager fullTextEntityManager = Search.getFullTextEntityManager(em);
    fullTextEntityManager.createIndexer().startAndWait();
    return "index";
  }
}
