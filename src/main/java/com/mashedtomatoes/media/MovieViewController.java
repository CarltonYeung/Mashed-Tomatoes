package com.mashedtomatoes.media;

import com.mashedtomatoes.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.*;


@Controller
public class MovieViewController {
  @Value("${mt.files.uri}")
  private String filesUri = "/files";

  @Value("${mt.smash.threshold}")
  private int smashThreshold = 50;

  @Autowired private MovieService movieService;

  @Autowired private UserService userService;

  @Autowired private Environment env;

  public static final Map<String, String> movieCategoryFilter = new HashMap<String, String>();
  static{
    movieCategoryFilter.put("New Releases", "new-releases");
    movieCategoryFilter.put("Coming Soon", "coming-soon");
    movieCategoryFilter.put("Opening This Week", "opening-this-week");
    movieCategoryFilter.put("All", "all");
  }

  public static final Map<String, String> movieSortFilter = new HashMap<String, String>();
  static{
    movieSortFilter.put("Release Date", "release-date");
    movieSortFilter.put("Most Popular", "most-popular");
    movieSortFilter.put("Critic Rating", "critic-rating");
    movieSortFilter.put("Top Box Office", "top-box-office");
  }

  private boolean validGenre(String genre){
    if(genre.equals("all")){
      return true;
    }
    for(Genre g: Genre.values()){
      if(g.name().equals(genre)){
        return true;
      }
    }
    return false;

  }

  private boolean validateReqParam(String category, String genre, String sort){
    if(!category.equals(movieCategoryFilter.get("New Releases")) && !category.equals(movieCategoryFilter.get("Coming Soon"))
            & !category.equals(movieCategoryFilter.get("Opening This Week")) && !category.equals(movieCategoryFilter.get("All"))){
      return false;
    }
    else if(!validGenre(genre)){
      return false;
    }
    else if(!sort.equals(movieSortFilter.get("Release Date")) && !sort.equals(movieSortFilter.get("Most Popular"))
            && !sort.equals(movieSortFilter.get("Critic Rating")) && !sort.equals(movieSortFilter.get("Top Box Office"))){
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
      return "media/moviefilter";
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
        return "media/moviefilter";
    }
    ArrayList<MovieViewModel> movieViewModelList = new ArrayList<MovieViewModel>();
    for (Iterator moviesIterator = movies.iterator(); moviesIterator.hasNext(); ) {
      movieViewModelList.add(new MovieViewModel(filesUri, smashThreshold, (Movie)moviesIterator.next()));
    }
    ArrayList<String> genres = new ArrayList<String>();
    for(Genre g: Genre.values()){
      genres.add(g.name());
    }

    m.addAttribute("genre", genre);
    m.addAttribute("sort", sort);
    m.addAttribute("category", category);
    m.addAttribute("pageNumber", page);
    m.addAttribute("movies", movieViewModelList);
    m.addAttribute("categories", movieCategoryFilter);
    m.addAttribute("sortFilters", movieSortFilter);
    m.addAttribute("genres", genres);
    return "media/moviefilter";
  }

  @GetMapping("/movie/academy-award")
  public String getAcademyMovies(
          @RequestParam(required = false, value = "year") Integer year,
          Model m){
    if(year == null){
      year = Calendar.getInstance().get(Calendar.YEAR)-1;
    }
    OscarWinnerSet oscarWinnerSet = movieService.getOscarWinnerByYear(year.intValue());
    m.addAttribute("academyAward",oscarWinnerSet);
    return "media/academyfilter";
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
