package com.mashedtomatoes.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.servlet.http.HttpServletResponse;
import java.util.NoSuchElementException;

@Controller
public class UserViewController {
  @Autowired
  UserService userService;

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

  @GetMapping("/user/{id}")
  public String user(@PathVariable long id,
                     HttpServletResponse response) {
    User user;
    try {
      user = userService.getUserById(id);
    } catch (NoSuchElementException e) {
      response.setStatus(HttpServletResponse.SC_NOT_FOUND);
      return "error/404";
    }
    user.setProfileViews(user.getProfileViews() + 1);
    userService.save(user);
    return "user/user";
  }
}
