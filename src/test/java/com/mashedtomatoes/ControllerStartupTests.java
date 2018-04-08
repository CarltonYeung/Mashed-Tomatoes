package com.mashedtomatoes;

import com.mashedtomatoes.media.MovieAPIController;
import com.mashedtomatoes.media.MovieViewController;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertNotNull;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ControllerStartupTests {

    @Autowired
    private AppController appController;

    @Autowired
    private MovieAPIController movieAPIController;

    @Autowired
    private MovieViewController movieViewController;

    @Test
    public void contextLoads() {
        assertNotNull(appController);
        assertNotNull(movieAPIController);
        assertNotNull(movieViewController);
    }
}
