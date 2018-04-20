package com.mashedtomatoes.celebrity;

import com.mashedtomatoes.media.Movie;
import com.mashedtomatoes.media.TVShow;
import com.mashedtomatoes.util.Util;
import java.util.List;
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

    List<Character> characters = celebrityService.getAllPlayedCharacters(id);
    characters.forEach(
        character ->
            character
                .getMedia()
                .setPosterPath(
                    Util.resolveFilesUrl(filesUri, character.getMedia().getPosterPath())));

    characters.forEach(character -> System.out.println(character.getMedia().getPosterPath()));

    List<Movie> directed = celebrityService.getDirectedMovies(id);
    List<Movie> produced = celebrityService.getProducedMovies(id);
    List<Movie> written = celebrityService.getWrittenMovies(id);
    List<TVShow> created = celebrityService.getCreatedTVShows(id);

    CelebrityViewModel viewModel =
        new CelebrityViewModel(
            filesUri, celebrity, characters, directed, produced, written, created);
    model.addAttribute("celebrity", viewModel);

    return "celebrity/celebrity";
  }
}
