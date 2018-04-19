package com.mashedtomatoes.media;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class MovieViewController {
  @Value("${mt.files.uri}")
  private String filesUri = "/files";

	@Value("${mt.smash.threshold}")
	private int smashThreshold = 50;

  @Autowired private MovieService movieService;

  @GetMapping("/movie")
  public String getMovies(Model m) {
    m.addAttribute("movies", movieService.getAllMovies(null));
    return "movies";
  }

  @GetMapping("/movie/{id}")
  public String getMovie(@PathVariable long id, Model m) {
    Movie movie = movieService.getMovieById(id);
    if (movie == null) {
      return "error/404";
    }

    MovieViewModel viewModel = new MovieViewModel(filesUri, smashThreshold, movie);
    m.addAttribute("movie", viewModel);
    return "media/movie";
  }
}
