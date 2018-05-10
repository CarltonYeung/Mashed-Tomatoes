package com.mashedtomatoes.media;

import com.mashedtomatoes.user.User;
import com.mashedtomatoes.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.awt.print.Pageable;
import java.util.Iterator;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

@Controller
public class MovieViewController {
  @Value("${mt.files.uri}")
  private String filesUri = "/files";

  @Value("${mt.smash.threshold}")
  private int smashThreshold = 50;

  @Autowired private MovieService movieService;

  @Autowired private UserService userService;

  @Autowired private Environment env;

  private boolean validGenre(String genre){
    if(genre.equals("all")){
      return true;
    }
    System.err.println("GOT HERE");
    for(Genre g: Genre.values()){
      if(g.name().equals(genre)){
        return true;
      }
    }
    return false;

  }

  private boolean validateReqParam(String category, String genre, String sort){
    if(!category.equals("new-releases") && !category.equals("coming-soon")
            & !category.equals("opening-this-week") && !category.equals("all")){
      return false;
    }
    else if(!validGenre(genre)){
      return false;
    }
    else if(!sort.equals("release-date") && !sort.equals("most-popular")
            && !sort.equals("critic-rating") && !sort.equals("top-box-office")){
      return false;
    }
    return true;
  }

  @GetMapping("/movie")
  public String getMovies(
          @RequestParam(required = false, defaultValue = "all", value = "category") String category,
          @RequestParam(required = false, defaultValue = "all", value = "genre") String genre,
          @RequestParam(required = false, defaultValue = "release-date", value = "sort") String sort,
          @RequestParam(required = false, value = "page") Integer pageInt,
          Model m) {
    if(!validateReqParam(category, genre, sort)){
      return "error/404";
    }
    int page;
    if(pageInt == null || pageInt.intValue() < 0){
      page = 0;
    }
    else{
      page = pageInt.intValue();
    }
    int limit = Integer.parseInt(env.getProperty("mt.filteredPage.limit"));
    int daysInterval = Integer.parseInt(env.getProperty("mt.homepage.categories.daysAheadAndBeforeCurrentDate"));

    Iterable<Movie> movies;
    switch (category){
      case "new-releases":
        movies = movieService.getFilteredMoviesByNewReleases(genre, sort, page, limit, daysInterval);
        break;
      case "coming-soon":
        movies = movieService.getFilteredMoviesByComingSoon(genre, sort, page, limit, daysInterval);
        break;
      case "opening-this-week":
        movies = movieService.getFilteredMoviesByOpeningThisWeek(genre, sort, page, limit);
        break;
      case "all":
        movies = movieService.getFilteredMovies(genre, sort, page, limit);
        break;
      default:
        return "error/404";
    }
    ArrayList<MovieViewModel> movieViewModelList = new ArrayList<MovieViewModel>();
    for (Iterator moviesIterator = movies.iterator(); moviesIterator.hasNext(); ) {
      movieViewModelList.add(new MovieViewModel(filesUri, smashThreshold, (Movie)moviesIterator.next()));
    }
    m.addAttribute("movies", movieViewModelList);
    return "search/search";
  }

  @GetMapping("/movie/{id}")
  public String getMovie(@PathVariable long id, Model m) {
    Movie movie = movieService.getMovieById(id);
    if (movie == null) {
      return "error/404";
    }

    MovieViewModel viewModel = new MovieViewModel(filesUri, smashThreshold, movie);
    m.addAttribute("movie", viewModel);
    userService.setMediaListAttributes(m, movie.getId());
    return "media/movie";
  }



}
