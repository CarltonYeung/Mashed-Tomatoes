package com.mashedtomatoes.shell;

import com.mashedtomatoes.repository.CelebrityRepository;
import com.mashedtomatoes.repository.CharacterRepository;
import com.mashedtomatoes.repository.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DatabaseManager {
    @Autowired
    private MovieRepository movieRepository;

    @Autowired
    private CelebrityRepository celebrityRepository;

    @Autowired
    private CharacterRepository characterRepository;

    public DatabaseManager() {
    }

    public MovieRepository getMovieRepository() {
        return movieRepository;
    }

    public CelebrityRepository getCelebrityRepository() {
        return celebrityRepository;
    }

    public CharacterRepository getCharacterRepository() {
        return characterRepository;
    }
}