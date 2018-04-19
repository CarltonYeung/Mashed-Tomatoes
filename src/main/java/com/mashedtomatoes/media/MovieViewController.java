package com.mashedtomatoes.media;

import com.mashedtomatoes.celebrity.Celebrity;
import com.mashedtomatoes.rating.AudienceRating;
import com.mashedtomatoes.rating.CriticRating;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.thymeleaf.util.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class MovieViewController {
	private static final String FILES_URI = "/files";

  @Autowired
  private MovieService movieService;

  @GetMapping("/movie")
  public String getMovies(Model m) {
    m.addAttribute("movies", movieService.getAllMovies(null));
    return "movies";
  }

  @GetMapping("/movie/{id}")
  public String getMovie(@PathVariable long id, Model m) {
    ViewModel viewModel = new ViewModel(movieService.getMovieById(id));
    m.addAttribute("movie", viewModel);
    return "media/movie";
  }

  public static class ViewModel extends Movie {

    private Double averageCriticRating = 0.0;

    private Integer totalCriticRating = 0;

    private Integer smashCount = 0;

    private Integer passCount = 0;

    private Double averageAudienceRating = 0.0;

    private Integer totalAudienceRating = 0;

    public ViewModel(Movie movie) {
      super.setId(movie.getId());
      super.setTitle(movie.getTitle());
      super.setGenres(movie.getGenres());
      super.setDescription(movie.getDescription());
      super.setReleaseDate(movie.getReleaseDate());
      super.setRunTime(movie.getRunTime());
      super.setPosterPath(FILES_URI + movie.getPosterPath());
      super.setCharacters(movie.getCharacters());
      super.getCharacters().forEach(character -> {
      	Celebrity c = character.getCelebrity();
      	character.getCelebrity().setProfilePath(FILES_URI + c.getProfilePath());
      });
	    super.setDirector(movie.getDirector());
	    super.setProducer(movie.getProducer());
	    super.setWriter(movie.getWriter());
	    super.setProductionCompany(movie.getProductionCompany());
      super.setBoxOffice(movie.getBoxOffice());
      super.setBudget(movie.getBudget());
    }

    public Double getAverageCriticRating() {
    	return averageCriticRating;
    }

	  public Integer getTotalCriticRating() {
    	return totalCriticRating;
	  }

	  public Integer getSmashCount() {
		  return smashCount;
	  }

	  public Integer getPassCount() {
		  return passCount;
	  }

	  public Double getAverageAudienceRating() {
		  return averageAudienceRating;
	  }

	  public Integer getTotalAudienceRating() {
		  return totalAudienceRating;
	  }

	  public void setTotalAudienceRating(Integer totalAudienceRating) {
      this.totalAudienceRating = totalAudienceRating;
    }

    public String getCommaSeperatedGenres() {
      return getGenres()
          .stream()
          .map(g -> StringUtils.capitalize(g.toString().toLowerCase()))
          .collect(Collectors.joining(","));
    }

    public List<String> getVideos() {
    	return new ArrayList<>();
    }

    public List<CriticRating> getCriticRatings() {
      return null;
    }

    public List<AudienceRating> getAudienceRatings() {
      return null;
    }

  }
}
