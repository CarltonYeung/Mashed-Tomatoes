package com.mashedtomatoes.celebrity;

import com.mashedtomatoes.media.Movie;
import com.mashedtomatoes.media.TVShow;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class CelebrityViewController {
  @Autowired CelebrityService celebrityService;
  @Value("${mt.files.uri}")
  private String filesUri = "/files";

  @GetMapping("/celebrity/{id}")
  public String getCelebrity(@PathVariable long id, Model model) {
    Celebrity celebrity = celebrityService.getCelebrityById(id);
    if (celebrity == null) {
      return "error/404";
    }

    CelebrityViewModel viewModel = new CelebrityViewModel(filesUri, celebrity);
    Iterable<Character> characters = celebrityService.getAllPlayedCharacters(id);
    Iterable<Movie> directed = celebrityService.getDirectedMovies(id);
    Iterable<Movie> produced = celebrityService.getProducedMovies(id);
    Iterable<Movie> written = celebrityService.getWrittenMovies(id);
    Iterable<TVShow> created = celebrityService.getCreatedTVShows(id);

    model.addAttribute("celebrity", viewModel);
    model.addAttribute("characters", characters);
    model.addAttribute("directed", directed);
    model.addAttribute("produced", produced);
    model.addAttribute("written", written);
    model.addAttribute("created", created);

    return "celebrity/celebrity";
  }
}
