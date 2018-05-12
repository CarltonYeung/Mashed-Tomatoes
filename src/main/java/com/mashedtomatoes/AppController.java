package com.mashedtomatoes;

import com.mashedtomatoes.media.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.ArrayList;
import java.util.Iterator;

import java.util.List;

@Controller
public class AppController {
  @Autowired MovieService movieService;

  @Autowired TVShowService tvShowService;

  @Autowired MediaService mediaService;

  @Autowired private Environment env;

  @Value("${mt.files.uri}")
  private String filesUri = "/files";

  @Value("${mt.smash.threshold}")
  private int smashThreshold = 50;

  private List<MovieViewModel> createMovieViewModelList(Iterable<Movie> movies){
    List<MovieViewModel> movieViewModels = new ArrayList<MovieViewModel>();
    for (Iterator moviesIterator = movies.iterator(); moviesIterator.hasNext(); ) {
      movieViewModels.add(new MovieViewModel(filesUri, smashThreshold, (Movie)moviesIterator.next()));
    }
    return movieViewModels;
  }

  private List<MediaViewModel> createMediaViewModelList(Iterable<Media> media){
    List<MediaViewModel> mediaViewModels = new ArrayList<MediaViewModel>();
    for (Iterator mediaIterator = media.iterator(); mediaIterator.hasNext(); ) {
      mediaViewModels.add(new MediaViewModel(filesUri, smashThreshold, (Media)mediaIterator.next()));
    }
    return mediaViewModels;
  }

  private List<TVShowViewModel> createTVShowViewModelList(Iterable<TVShow> tvShows){
    List<TVShowViewModel> tvShowsViewModels = new ArrayList<TVShowViewModel>();
    for (Iterator tvShowIterator = tvShows.iterator(); tvShowIterator.hasNext(); ) {
      tvShowsViewModels.add(new TVShowViewModel(filesUri, smashThreshold, (TVShow)tvShowIterator.next()));
    }
    return tvShowsViewModels;
  }

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
    List<MovieViewModel> topBoxOffice = createMovieViewModelList(movieService.getTopBoxOfficeMovies(limit));
    List<MovieViewModel> topRatedFilms = createMovieViewModelList(movieService.getTopRatedFilms(limit));
    List<MovieViewModel> comingSoonFilms =
        createMovieViewModelList(movieService.getComingSoonFilms(limit, daysInterval));
    List<MovieViewModel> nowPlayingFilms =
        createMovieViewModelList(movieService.getNowPlayingFilms(limit, daysInterval));
    List<TVShowViewModel> nowAiringTVShows = createTVShowViewModelList(tvShowService.getNowAiringTVShows(limit));
    List<TVShowViewModel> topRatedTVShows = createTVShowViewModelList(tvShowService.getTopRatedTVShows(limit));
    List<MovieViewModel> bestPictureWinner = createMovieViewModelList(movieService.getBestPictureWinner(limit));
    List<TVShowViewModel> tvAiringToday = createTVShowViewModelList(tvShowService.getTVAiringToday(limit));
//    List<MediaViewModel> nowPlaying = createMediaViewModelList(mediaService.getNowPlaying(limit,nowPlayingDaysAhead));

//    System.out.println("Now Playing");
//    for(MediaViewModel movie : nowPlaying){
//      System.out.println(movie.getTitle());
//    }




    model.addAttribute("topBoxOffice", topBoxOffice);
    model.addAttribute("topRatedFilms", topRatedFilms);
    model.addAttribute("comingSoonFilms", comingSoonFilms);
    model.addAttribute("nowPlayingFilms", nowPlayingFilms);
    model.addAttribute("nowAiringTVShows", nowAiringTVShows);
    model.addAttribute("topRatedTVShows", topRatedTVShows);
    model.addAttribute("bestPictureWinner", bestPictureWinner);
    model.addAttribute("tvAiringToday", tvAiringToday);
//    model.addAttribute("nowPlaying", nowPlaying);
    return "index";
  }
}
