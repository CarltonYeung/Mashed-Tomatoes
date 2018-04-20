package com.mashedtomatoes.media;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class TVShowViewController {
  @Value("${mt.files.uri}")
  private String filesUri = "/files";

  @Value("${mt.smash.threshold}")
  private int smashThreshold = 50;

  @Autowired private TVShowService tvShowService;

  @GetMapping("/tv")
  public String getTVShows(Model m) {
    m.addAttribute("tvshows", tvShowService.getAllTVShows());
    return "media/tvshows";
  }

  @GetMapping("/tv/{id}")
  public String getTVShow(@PathVariable long id, Model m) {
    TVShow tvShow = tvShowService.getTVShowById(id);
    if (tvShow == null) {
      return "error/404";
    }

    TVShowViewModel viewModel = new TVShowViewModel(filesUri, smashThreshold, tvShow);
    m.addAttribute("tvshow", viewModel);
    return "media/tvshow";
  }
}
