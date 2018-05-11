package com.mashedtomatoes;

import com.mashedtomatoes.media.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import java.util.Iterator;

import java.util.List;

@Controller
public class AppController {
  @Autowired MovieService movieService;

  @Autowired TVShowService tvShowService;

  @Autowired MediaService mediaService;

  @Autowired private Environment env;

  /*
  Returns dynamic data to the homepage.
  SPECIAL NOTE!
  The Date field returned for BEST PICTURE WINNER is the year they won not the release date. The date field is the release date for the other categories.
   */
  @GetMapping("/")
  public String getIndex(Model model) {
    int limit = Integer.parseInt(env.getProperty("mt.homepage.categories.limit"));
    int daysInterval =
        Integer.parseInt(env.getProperty("mt.homepage.categories.daysAheadAndBeforeCurrentDate"));
    int nowPlayingDaysAhead = Integer.parseInt(env.getProperty("mt.homepage.nowPlaying.daysAhead"));
    Iterable<MovieRatingQuery> topBoxOffice = movieService.getTopBoxOfficeMovies(limit);
    Iterable<MovieRatingQuery> topRatedFilms = movieService.getTopRatedFilms(limit);
    Iterable<MovieRatingQuery> comingSoonFilms =
        movieService.getComingSoonFilms(limit, daysInterval);
    Iterable<MovieRatingQuery> nowPlayingFilms =
        movieService.getNowPlayingFilms(limit, daysInterval);
    Iterable<TVShowRatingQuery> nowAiringTVShows = tvShowService.getNowAiringTVShows(limit);
    Iterable<TVShowRatingQuery> topRatedTVShows = tvShowService.getTopRatedTVShows(limit);
    Iterable<MovieRatingQuery> bestPictureWinner = movieService.getBestPictureWinner(limit);
    Iterable<TVShowRatingQuery> tvAiringToday = tvShowService.getTVAiringToday(limit);
    Iterable<MediaRatingQuery> nowPlaying = mediaService.getNowPlaying(limit,nowPlayingDaysAhead);

    model.addAttribute("topBoxOffice", topBoxOffice);
    model.addAttribute("topRatedFilms", topRatedFilms);
    model.addAttribute("comingSoonFilms", comingSoonFilms);
    model.addAttribute("nowPlayingFilms", nowPlayingFilms);
    model.addAttribute("nowAiringTVShows", nowAiringTVShows);
    model.addAttribute("topRatedTVShows", topRatedTVShows);
    model.addAttribute("bestPictureWinner", bestPictureWinner);
    model.addAttribute("tvAiringToday", tvAiringToday);
    model.addAttribute("nowPlaying", nowPlaying);
    return "index";
  }
}
