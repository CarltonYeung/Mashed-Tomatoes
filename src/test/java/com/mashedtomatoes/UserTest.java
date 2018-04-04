package com.mashedtomatoes;

import com.mashedtomatoes.service.user.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserTest {

    @Autowired
    private UserService userService;

    @Test
    public void saveAudienceTest() {
        String displayName = "Carlton";
        String email = "carlton.yeung@stonybrook.edu";
        char[] password = "plaintextPassword".toCharArray();

        userService.addAudience(displayName, email, password);
    }
}
