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

    @Autowired
    MovieService movieService;

    @GetMapping("/movie")
    public String getMovies(Model m) {
        m.addAttribute("movies", movieService.getAllMovies(null));
        return "movies";
    }

    @GetMapping("/movie/{slug}")
    public String getMovie(@PathVariable String slug, Model m) {
        ViewModel viewModel = new ViewModel(movieService.getMovieBySlug(slug));
        m.addAttribute("movie", viewModel);
        return "media/movie";
    }

    static class ViewModel extends Movie {
        private Double averageCriticRating;
        private Integer totalCriticRating;
        private Integer smashCount;
        private Integer passCount;
        private Double averageAudienceRating;
        private Integer totalAudienceRating;
        private Celebrity director;
        private List<Celebrity> writers;
        private String studio;

        public ViewModel(Movie movie) {
            super.setID(movie.getID());
            super.setTitle(movie.getTitle());
            super.setSlug(movie.getSlug());
            super.setGenres(movie.getGenres());
            super.setDescription(movie.getDescription());
            super.setReleaseDate(movie.getReleaseDate());
            super.setRunTime(movie.getRunTime());
            super.setBackdropPath("/img/fight-club-backdrop.jpg"); // concat from base image path here
            super.setPosterPath("/img/fight-club-poster.jpg"); // concat from base image path here
            super.setBoxOffice(movie.getBoxOffice());
        }

        public Double getAverageCriticRating() {
            return 0.0;
        }

        public void setAverageCriticRating(Double averageCriticRating) {
            this.averageCriticRating = averageCriticRating;
        }

        public Integer getTotalCriticRating() {
            return 0;
        }

        public void setTotalCriticRating(Integer totalCriticRating) {
            this.totalCriticRating = totalCriticRating;
        }

        public Integer getSmashCount() {
            return 0;
        }

        public void setSmashCount(Integer smashCount) {
            this.smashCount = smashCount;
        }

        public Integer getPassCount() {
            return 0;
        }

        public void setPassCount(Integer passCount) {
            this.passCount = passCount;
        }

        public Double getAverageAudienceRating() {
            return 0.0;
        }

        public void setAverageAudienceRating(Double averageAudienceRating) {
            this.averageAudienceRating = averageAudienceRating;
        }

        public Integer getTotalAudienceRating() {
            return 0;
        }

        public void setTotalAudienceRating(Integer totalAudienceRating) {
            this.totalAudienceRating = totalAudienceRating;
        }

        public Celebrity getDirector() {
            Celebrity mock = new Celebrity();
            mock.setName("John Doe");
            return mock;
        }

        public void setDirector(Celebrity director) {
            this.director = director;
        }

        public List<Celebrity> getWriters() {
            List<Celebrity> mocks = new ArrayList<>();
            Celebrity mock = new Celebrity();
            mock.setName("John Doe");
            mocks.add(mock);
            return mocks;
        }

        public void setWriters(List<Celebrity> writers) {
            this.writers = writers;
        }

        public String getStudio() {
            return "Warner Bros.";
        }

        public void setStudio(String studio) {
            this.studio = studio;
        }

        public String getCommaSeperatedGenres() {
            return getGenres().stream()
                    .map(g -> StringUtils.capitalize(g.toString().toLowerCase()))
                    .collect(Collectors.joining(","));
        }

        public String getCommaSeperatedWriters() {
            return "John Doe, Jane Doe";
        }

        public String getRating() {
            return "PG-13"; // stub method for now; movie entity should store this
        }

        public List<String> getPhotos() {
            return Arrays.asList(
                    "/img/fight-club-backdrop.jpg",
                    "/img/fight-club-backdrop.jpg",
                    "/img/fight-club-backdrop.jpg"
            );
        }

        public List<Video> getVideos() {
            return Arrays.asList(
                    new Video("/img/fight-club-backdrop.jpg", "/vid/jellyfish.mp4"),
                    new Video("/img/fight-club-backdrop.jpg", "/vid/jellyfish.mp4"),
                    new Video("/img/fight-club-backdrop.jpg", "/vid/jellyfish.mp4")
            );
        }

        public List<Character> getCharacters() {
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