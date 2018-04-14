package com.mashedtomatoes.celebrity;

import com.mashedtomatoes.media.Movie;
import com.mashedtomatoes.media.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class CelebrityViewController {
  @Autowired
  CelebrityService celebrityService;

  @Autowired
  MovieRepository movieRepository;

  @Autowired
  CharacterRepository characterRepository;

  @GetMapping("/celebrity/{ID}")
  public String getMovie(@PathVariable long ID, Model model) {
    Celebrity c = celebrityService.getCelebrityByID(ID);
    Iterable<Character> characters = characterRepository.findAllByCelebrity_Id(c.getId());
    Iterable<Movie> directed = movieRepository.findAllByDirector_Id(c.getId());

    model.addAttribute("celebrity", c);
    model.addAttribute("characters", characters);
    model.addAttribute("directed", directed);

    return "celebrity/celebrity";
  }
}
