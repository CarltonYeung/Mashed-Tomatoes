package com.mashedtomatoes;

import com.mashedtomatoes.model.user.Audience;
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
        int DISPLAY_NAME = 0;
        int EMAIL = 1;
        int PASSWORD = 2;
        int nUsers = 10;

        String[][] users = new String[nUsers][3];

        for (int i = 0; i < nUsers; i++) {
            users[i][DISPLAY_NAME] = "user " + (i+1);
            users[i][EMAIL] = "email@" + (i+1) + ".edu";
            users[i][PASSWORD] = "plaintext password " + (i+1);
        }

        for (String[] user : users) {
            Audience saved = userService.addAudience(
                    user[DISPLAY_NAME],
                    user[EMAIL],
                    user[PASSWORD].toCharArray());
            System.out.printf("\t%s\n", saved);
        }
    }
}
