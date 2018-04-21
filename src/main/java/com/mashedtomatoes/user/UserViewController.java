package com.mashedtomatoes.user;

import java.util.NoSuchElementException;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class UserViewController {
  @Autowired UserService userService;

  @Value("${mt.rank.upgrade.rate}")
  private int rankUpgradeRate = 10;

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
  public String user(@PathVariable long id, HttpServletResponse response, Model model) {
    User user;
    try {
      user = userService.getUserById(id);
    } catch (NoSuchElementException e) {
      response.setStatus(HttpServletResponse.SC_NOT_FOUND);
      return "error/404";
    }
    user.setProfileViews(user.getProfileViews() + 1);
    userService.save(user);

    if (user.getType() != UserType.AUDIENCE && user.getType() != UserType.CRITIC) {
      response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
      return "error/500";
    }

    if (user.getType() == UserType.AUDIENCE) {
      model.addAttribute("user", new AudienceViewModel((Audience) user, rankUpgradeRate));
    } else if (user.getType() == UserType.CRITIC) {
      model.addAttribute("user", new CriticViewModel((Critic) user));
    }
    return "user/user";
  }
}
