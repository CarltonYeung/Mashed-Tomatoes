package com.mashedtomatoes.user;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class UserViewController {

  @GetMapping("/login")
  public String login() {
    return "user/login";
  }

  @GetMapping("/register")
  public String register() {
    return "user/register";
  }

  @GetMapping("/recover")
  public String recover() {
    return "user/recover";
  }
}
