package com.mashedtomatoes.media;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(SpringRunner.class)
@SpringBootTest
public class EntitiesTest {

    @Autowired
    private MovieService movieService;

    private static long movieNumber = 1;

    private Movie addMovie() {
        String number = String.valueOf(movieNumber++);
        Movie movie = new Movie();
        movie.setTitle("Movie " + number);
        movie.setSlug("Slug-" + number);
        movie.setDescription("Description " + number);
        movieService.addMovie(movie);
        return movie;
    }

    @Test
    public void movieServiceAddMovieAndGetBySlugTest() {
        for (int i = 0; i < 50; i++) {
            Movie in = addMovie();
            Movie out = movieService.getMovieBySlug(in.getSlug());

            assertNotNull(out);
            assertEquals(in.getID(), out.getID());
        }
    }
}
