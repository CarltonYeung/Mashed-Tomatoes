package com.mashedtomatoes.search;

import com.mashedtomatoes.celebrity.Celebrity;
import com.mashedtomatoes.celebrity.CelebrityViewModel;
import com.mashedtomatoes.media.Movie;
import com.mashedtomatoes.media.MovieViewModel;
import com.mashedtomatoes.media.TVShow;
import com.mashedtomatoes.media.TVShowViewModel;
import com.mashedtomatoes.util.Pair;
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
  private static final int MOVIES_PER_PAGE = 10;
  private static final int TVSHOWS_PER_PAGE = 10;
  private static final int CELEBS_PER_PAGE = 7;

  @Autowired SearchService searchService;

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

    List<Object> objects = searchService.getAllObjectsMatching(expr);
    Pair<List<Object>, Boolean> pagedObjectsAndHasNext =
        searchService.getListPage(objects, page, MOVIES_PER_PAGE);
    if (pagedObjectsAndHasNext == null) {
      return "error/404";
    }

    Boolean hasNextPage = pagedObjectsAndHasNext.getRight();
    List<Object> pagedObjects = pagedObjectsAndHasNext.getLeft();
    List<MovieViewModel> movies = new ArrayList<>();
    List<TVShowViewModel> tvShows = new ArrayList<>();
    List<CelebrityViewModel> celebrities = new ArrayList<>();
    for (Object o : pagedObjects) {
      if (o instanceof Movie) {
        movies.add(new MovieViewModel(filesUri, smashThreshold, (Movie) o));
      } else if (o instanceof TVShow) {
        tvShows.add(new TVShowViewModel(filesUri, smashThreshold, (TVShow) o));

      } else if (o instanceof Celebrity) {
        celebrities.add(new CelebrityViewModel(filesUri, (Celebrity) o));
      }
    }

    m.addAttribute("movies", movies);
    m.addAttribute("tvShows", tvShows);
    m.addAttribute("celebrities", celebrities);
	  m.addAttribute("expr", expr);
	  m.addAttribute("page", page);
    m.addAttribute("hasPrevPage", page != 0);
    m.addAttribute("hasNextPage", hasNextPage);

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
