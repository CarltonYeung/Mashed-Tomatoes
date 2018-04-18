package com.mashedtomatoes;

import com.mashedtomatoes.media.MovieRatingQuery;
import com.mashedtomatoes.media.MovieService;
import com.mashedtomatoes.media.TVShowRatingQuery;
import com.mashedtomatoes.media.TVShowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AppController {

  @Value("${mt.title}")
  private String title = "MT!";

  @Autowired
  MovieService movieService;

  @Autowired
  TVShowService tvShowService;

  @Autowired
  private Environment env;

  /*
  Returns dynamic data to the homepage.
  SPECIAL NOTE!
  The Date field returned for BEST PICTURE WINNER is the year they won not the release date. The date field is the release date for the other categories.
   */
  @GetMapping("/")
  public String getIndex(Model model) {
      int limit = Integer.parseInt(env.getProperty("mt.homepage.categories.limit"));
      int daysInterval = Integer.parseInt(env.getProperty("mt.homepage.categories.daysAheadAndBeforeCurrentDate"));
      Iterable<MovieRatingQuery> topBoxOffice = movieService.getTopBoxOfficeMovies(limit);
      Iterable<MovieRatingQuery> topRatedFilms = movieService.getTopRatedFilms(limit);
      Iterable<MovieRatingQuery> comingSoonFilms = movieService.getComingSoonFilms(limit, daysInterval);
      Iterable<MovieRatingQuery> nowPlayingFilms = movieService.getNowPlayingFilms(limit, daysInterval);
      Iterable<TVShowRatingQuery> nowAiringTVShows = tvShowService.getNowAiringTVShows(limit);
      Iterable<TVShowRatingQuery> topRatedTVShows = tvShowService.getTopRatedTVShows(limit);
      Iterable<MovieRatingQuery> bestPictureWinner =movieService.getBestPictureWinner(limit);

//      MovieRatingQuery movie = topBoxOffice.iterator().next();
//      System.out.println(movie.getId()+ " "+ movie.getTitle() + " "+ movie.getAvgRating()+" "+ movie.getReleaseDate()+" "+ movie.getBoxOffice());
//
//      movie= topRatedFilms.iterator().next();
//      System.out.println(movie.getId()+ " "+ movie.getTitle() + " "+ movie.getAvgRating()+" "+ movie.getReleaseDate()+" "+ movie.getBoxOffice());
//
//      movie= comingSoonFilms.iterator().next();
//      System.out.println(movie.getId()+ " "+ movie.getTitle() + " "+ movie.getAvgRating()+" "+ movie.getReleaseDate()+" "+ movie.getBoxOffice());
//
//      movie= nowPlayingFilms.iterator().next();
//      System.out.println(movie.getId()+ " "+ movie.getTitle() + " "+ movie.getAvgRating()+" "+ movie.getReleaseDate()+" "+ movie.getBoxOffice());
//
//      TVShowRatingQuery tv = nowAiringTVShows.iterator().next();
//      System.out.println(tv.getId()+ " "+ tv.getTitle() + " "+ tv.getAvgRating());
//
//      tv = topRatedTVShows.iterator().next();
//      System.out.println(tv.getId()+ " "+ tv.getTitle() + " "+ tv.getAvgRating());
//
//      movie= bestPictureWinner.iterator().next();
//      System.out.println(movie.getId()+ " "+ movie.getTitle() + " "+ movie.getAvgRating()+" "+ movie.getReleaseDate()+" "+ movie.getBoxOffice());

      model.addAttribute("topBoxOffice", topBoxOffice);
      model.addAttribute("topRatedFilms", topRatedFilms);
      model.addAttribute("comingSoonFilms", comingSoonFilms);
      model.addAttribute("nowPlayingFilms", nowPlayingFilms);
      model.addAttribute("nowAiringTVShows", nowAiringTVShows);
      model.addAttribute("topRatedTVShows", topRatedTVShows);
      model.addAttribute("bestPictureWinner", bestPictureWinner);
      return "index";
  }
}
