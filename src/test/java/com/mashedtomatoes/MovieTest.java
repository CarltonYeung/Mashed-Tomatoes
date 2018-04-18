package com.mashedtomatoes;

import com.mashedtomatoes.celebrity.Celebrity;
import com.mashedtomatoes.celebrity.CelebrityRepository;
import com.mashedtomatoes.media.Genre;
import com.mashedtomatoes.media.Movie;
import com.mashedtomatoes.media.MovieRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashSet;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MovieTest {

    @Autowired
    private MovieRepository movieRepository;

    @Autowired
    private CelebrityRepository celebrityRepository;

    @Test
    public void addMovieTest() {
        Movie movie = new Movie();
        movie.setTitle("foo");
        //movie.setSlug("foo");

        HashSet<Genre> genres = new HashSet<>();
        genres.add(Genre.ACTION);
        genres.add(Genre.ADVENTURE);
        movie.setGenres(genres);

        Celebrity bradPitt = new Celebrity();
        bradPitt.setName("brad pitt");

//        HashSet<Media> media = new HashSet<>();
//        media.add(movie);
//        bradPitt.setMedia(media);

        HashSet<Celebrity> celebs = new HashSet<>();
        celebs.add(bradPitt);

//        movie.setCelebrities(celebs);
        movieRepository.save(movie);

        System.err.println(bradPitt);
//        System.err.println(bradPitt.getMedia().size());
        bradPitt = celebrityRepository.findById(bradPitt.getId()).get();
        System.err.println(bradPitt);
//        System.err.println(bradPitt.getMedia().size());

    }




}
