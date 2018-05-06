package com.mashedtomatoes.user;

import java.util.NoSuchElementException;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
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

  @GetMapping("/resend")
  public String resend() {
    return "user/resend";
  }

  @GetMapping("/user/me")
  public String user(HttpServletResponse response) {
    HttpSession session = UserService.session();
    if (session == null) {
      response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
      return "error/401";
    }

    User user = (User) session.getAttribute("User");
    return String.format("redirect:/user/%d", user.getId());
  }

  @GetMapping("/user/{id}")
  public String user(@PathVariable long id, HttpServletResponse response, Model model) {
    User dbUser;
    try {
      dbUser = userService.getUserById(id);
    } catch (NoSuchElementException e) {
      response.setStatus(HttpServletResponse.SC_NOT_FOUND);
      return "error/404";
    }

    if (dbUser.getType() != UserType.AUDIENCE && dbUser.getType() != UserType.CRITIC) {
      response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
      return "error/500";
    }

    User sessionUser = null;
    HttpSession session = UserService.session();
    if (session != null) {
      sessionUser = (User) session.getAttribute("User");
    }

    boolean viewingOwnProfile = session != null && sessionUser.getId() == dbUser.getId();
    if (!viewingOwnProfile) {
      dbUser.setProfileViews(dbUser.getProfileViews() + 1);
      dbUser = userService.save(dbUser);
    }

    model.addAttribute("viewingOwnProfile", viewingOwnProfile);
    boolean isFollowing = false;
    if (session != null && !viewingOwnProfile) {
      for (User user: sessionUser.getFollowing()) {
        if (user.getId() == dbUser.getId()) {
          isFollowing = true;
          break;
        }
      }
    }

    model.addAttribute("isFollowing", isFollowing);
    if (dbUser.getType() == UserType.AUDIENCE) {
      model.addAttribute("user", new AudienceViewModel((Audience) dbUser, rankUpgradeRate));
    } else if (dbUser.getType() == UserType.CRITIC) {
      model.addAttribute("user", new CriticViewModel((Critic) dbUser));
    }
    return "user/user";
  }
}
