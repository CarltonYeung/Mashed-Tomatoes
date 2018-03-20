package com.mashedtomatoes.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mashedtomatoes.model.Movie;
import com.mashedtomatoes.repository.MovieRepository;

@Service
public class MovieService {

    @Autowired
    MovieRepository movieRepository;

    public Iterable<Movie> getAllMovies() {
        return movieRepository.findAll();
    }

    public Movie getMovieBySlug(String slug) {
        return movieRepository.findFirstBySlug(slug);
    }
}