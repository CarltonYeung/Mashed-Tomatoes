package com.mashedtomatoes.user;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class UserViewController {

    @GetMapping("/login.html")
    public String login() {
        return "user/login";
    }
}
