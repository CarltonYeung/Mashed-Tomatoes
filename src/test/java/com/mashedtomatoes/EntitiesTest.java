package com.mashedtomatoes;

import com.mashedtomatoes.model.Movie;
import com.mashedtomatoes.service.MovieService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

@RunWith(SpringRunner.class)
@SpringBootTest
public class EntitiesTest {

    @Autowired
    private MovieService movieService;

    @Test
    public void movieServiceAddMovieAndGetBySlugTest() {
        Movie in = new Movie();
        in.setTitle("Finding Nemo");
        in.setSlug("finding-nemo");
        in.setDescription("Nemo, where you at?");
        movieService.addMovie(in);

        Movie out = movieService.getMovieBySlug(in.getSlug());
        assertNotNull(out);
        assertEquals(out.getTitle(), in.getTitle());
        assertEquals(out.getSlug(), in.getSlug());
        assertEquals(out.getDescription(), in.getDescription());
        assertEquals(out.getID(), in.getID());
    }
}
