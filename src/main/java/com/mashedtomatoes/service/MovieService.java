package com.mashedtomatoes.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mashedtomatoes.model.Movie;
import com.mashedtomatoes.repository.MovieRepository;

@Service
public class MovieService {

    @Autowired
    MovieRepository movieRepository;

    public Iterable<Movie> getAllMovies(String expr) {
        if (expr == null) {
            return movieRepository.findAll();
        }

        return movieRepository.findSimilarMovies(expr);
    }

    public Movie getMovieBySlug(String slug) {
        return movieRepository.findFirstBySlug(slug);
    }

    public void addMovie(Movie movie) {
        movieRepository.save(movie);
    }

    public void updateMovie(Movie movie) {
        addMovie(movie);
    }

    public void deleteMovie(Movie movie) {
        movieRepository.delete(movie);
    }

    public Boolean deleteMovieBySlug(String slug) {
        Movie movie = getMovieBySlug(slug);
        if (movie == null) {
            return false;
        }

        deleteMovie(movie);
        return true;
    }
}