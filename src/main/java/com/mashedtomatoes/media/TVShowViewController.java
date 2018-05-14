package com.mashedtomatoes.media;

import com.mashedtomatoes.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

@Controller
public class TVShowViewController {
  @Value("${mt.files.uri}")
  private String filesUri = "/files";

  @Value("${mt.smash.threshold}")
  private int smashThreshold = 50;

  @Autowired private TVShowService tvShowService;

  @Autowired private UserService userService;

  @Autowired private Environment env;

  private static final Map<String, String> tvShowSortFilter = new HashMap<String, String>();
  static{
    tvShowSortFilter.put("Most Popular", "most-popular");
    tvShowSortFilter.put("Critic Rating", "critic-rating");
    tvShowSortFilter.put("Now Airing", "now-airing");
    tvShowSortFilter.put("Airing Today", "airing-today");
    tvShowSortFilter.put("All", "all");
  }

  private boolean validGenre(String genre){
    if(genre.equals("all")){
      return true;
    }
    for(Genre g: Genre.values()){
      if(g.name().equals(genre)){
        return true;
      }
    }
    return false;

  }

  private boolean validateReqParam(String genre, String sort){
    System.out.println(sort);
    System.out.println(tvShowSortFilter.get("Airing Today"));
    if(!validGenre(genre)){
      return false;
    }
    else if(!sort.equals(tvShowSortFilter.get("Most Popular")) && !sort.equals(tvShowSortFilter.get("Airing Today"))
            && !sort.equals(tvShowSortFilter.get("Critic Rating"))&& !sort.equals(tvShowSortFilter.get("Now Airing")) && !sort.equals(tvShowSortFilter.get("All"))){
      return false;
    }
    return true;
  }

  @GetMapping("/tv")
  public String getTVShows(
          @RequestParam(required = false, defaultValue = "all", value = "genre") String genre,
          @RequestParam(required = false, defaultValue = "all", value = "sort") String sort,
          @RequestParam(required = false, value = "page") Integer pageInt,
          Model m) {
    genre = genre.toUpperCase();
    if(!validateReqParam(genre, sort)){
      return "media/tvfilter";
    }
    int page;
    if(pageInt == null || pageInt.intValue() < 0){
      page = 0;
    }
    else{
      page = pageInt.intValue();
    }
    int limit = Integer.parseInt(env.getProperty("mt.filteredPage.limit"));
    int timeInterval = Integer.parseInt(env.getProperty("mt.newTVShows.timeInterval"));
    Iterable<TVShow> tvShows = tvShowService.getFilteredTVShows(genre,sort,page,limit,timeInterval);

    ArrayList<TVShowViewModel> tvShowViewModelList = new ArrayList<TVShowViewModel>();
    for (Iterator tvShowsIterator = tvShows.iterator(); tvShowsIterator.hasNext(); ) {
      tvShowViewModelList.add(new TVShowViewModel(filesUri, smashThreshold, (TVShow)tvShowsIterator.next()));
    }
    ArrayList<String> genres = new ArrayList<String>();
    for(Genre g: Genre.values()){
      genres.add(g.name());
    }
    m.addAttribute("page", page);
    m.addAttribute("genre", genre);
    m.addAttribute("sort", sort);
    m.addAttribute("tvShows", tvShowViewModelList);
    m.addAttribute("tvShowSortFilters", tvShowSortFilter);
    m.addAttribute("genres", genres);
    return "media/tvfilter";
  }

  @GetMapping("/tv/{id}")
  public String getTVShow(@PathVariable long id, Model m) {
    TVShow tvShow = tvShowService.getTVShowById(id);
    if (tvShow == null) {
      return "error/404";
    }

    TVShowViewModel viewModel = new TVShowViewModel(filesUri, smashThreshold, tvShow);
    m.addAttribute("tvshow", viewModel);
    userService.setMediaListAttributes(m, tvShow.getId());
    return "media/tvshow";
  }
  @GetMapping("/tvfilter")
  public String getMovieFilter() { return "media/tvfilter"; }
}
