package com.mashedtomatoes;

import com.mashedtomatoes.media.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AppController {

  @Autowired
  private MovieService movieService;

  @Autowired
  private TVShowService tvShowService;

  @Autowired
  private Environment env;

  @Value("${mt.title}")
  private String title = "MT!";

  /*
  Dynamic Data for Top Box Office Films, Top Rated Films, Coming Soon Films, Now Playing Films, Now Airing TV Shows, Top Rated TV Shows,
  and Best Picture Winner.
  SPECIAL NOTE!!!!!
  The Date object returned by the Best Picture Winner is the year they won! For all other movies, it is the release date!
   */
  @GetMapping("/")
  public String getIndex(Model m) {
    int limit = Integer.parseInt(env.getProperty("mt.homepage.categories.limit"));
    int daysAheadAndBefore = Integer.parseInt(env.getProperty("mt.homepage.categories.daysAheadAndBeforeCurrentDate"));
    Iterable<MovieRatingQuery> topBoxOffices = movieService.getTopBoxOfficeMovies(limit);
    Iterable<MovieRatingQuery> topRatedFilms = movieService.getTopRatedFilms(limit);
    Iterable<MovieRatingQuery> comingSoonFilms = movieService.getComingSoonFilms(limit, daysAheadAndBefore);
    Iterable<MovieRatingQuery> nowPlayingFilms = movieService.getNowPlayingFilms(limit, daysAheadAndBefore);
    Iterable<TVShowRatingQuery> nowAiringTVShows = tvShowService.getNowAiringTVShows(limit);
    Iterable<TVShowRatingQuery> topRatedTVShows = tvShowService.getTopRatedTVShows(limit);
    Iterable<MovieRatingQuery> bestPictureWinner = movieService.getBestPictureWinner(limit);

    m.addAttribute("topBoxOffice", topBoxOffices);
    m.addAttribute("topRatedFilms", topRatedFilms);
    m.addAttribute("comingSoonFilms", comingSoonFilms);
    m.addAttribute("nowPlayingFilms", nowPlayingFilms);
    m.addAttribute("nowAiringTVShows", nowAiringTVShows);
    m.addAttribute("topRatedTVShows", topRatedTVShows);
    m.addAttribute("bestPictureWinner", bestPictureWinner);
    return "index";

  }
}
