package com.mashedtomatoes.user;

import com.mashedtomatoes.http.FollowRequest;
import com.mashedtomatoes.http.LoginRequest;
import com.mashedtomatoes.http.RegisterRequest;
import com.mashedtomatoes.mail.MailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.NoSuchElementException;

@RestController
public class UserAPIController {
  @Autowired
  private UserService userService;
  @Autowired
  private MailService mailService;
  @Autowired
  private Environment env;

  @PostMapping("/register")
  public String register(@Valid @RequestBody RegisterRequest request,
                         HttpServletResponse response) {

    Audience user;
    try {
      user = userService.addAudience(request.getDisplayName(), request.getEmail(), request.getPassword());
    } catch (Exception e) {
      response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
      return e.getMessage();
    }
    String to = user.getCredentials().getEmail();
    String key = user.getVerification().getVerificationKey();
    System.out.println(key);
    mailService.sendVerificationEmail(to, key);
    response.setStatus(HttpServletResponse.SC_CREATED);
    return "";
  }

  @GetMapping("/verify")
  public void verify(@RequestParam("email") String email,
                       @RequestParam("key") String key,
                       HttpServletResponse response) {

    if (userService.verifyEmail(email, key)) {
      response.setStatus(HttpServletResponse.SC_OK);
    } else {
      response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
    }
  }

  @PostMapping("/login")
  public String login(@Valid @RequestBody LoginRequest loginRequest,
                      HttpServletRequest request,
                      HttpServletResponse response) {

    User user = userService.getUserByCredentials(loginRequest.getEmail(), loginRequest.getPassword());
    if (user == null) {
      response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
      return env.getProperty("user.badCredentials");
    }
    if (!user.getVerification().isVerified()) {
      response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
      return env.getProperty("user.notVerified");
    }
    HttpSession session = request.getSession(true);
    session.setAttribute("User", user);
    response.setStatus(HttpServletResponse.SC_OK);
    return "";
  }

  @PostMapping("/logout")
  public String logout(HttpServletRequest request,
                     HttpServletResponse response) {

    HttpSession session = request.getSession(false);
    if (session == null) {
      response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
      return env.getProperty("user.notLoggedIn");
    }
    session.invalidate();
    response.setStatus(HttpServletResponse.SC_OK);
    return "";
  }

  @GetMapping("/hello")
  public void hello(HttpServletRequest request,
                    HttpServletResponse response) {

    HttpSession session = request.getSession(false);
    if (session == null) {
      response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
    } else {
      response.setStatus(HttpServletResponse.SC_OK);
    }
  }

  @PostMapping("/user/follow")
  @Transactional
  public String follow(@Valid @RequestBody FollowRequest followRequest,
                     HttpServletRequest request,
                     HttpServletResponse response) {

    HttpSession session = request.getSession(false);
    if (session == null) {
      response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
      return env.getProperty("user.notLoggedIn");
    }

    User me = (User) session.getAttribute("User");
    if (me.id == followRequest.getId()) {
      response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
      return env.getProperty("user.followSelf");
    }

    User toFollow;
    try {
      toFollow = userService.getUserById(followRequest.getId());
    } catch (NoSuchElementException e) {
      response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
      return env.getProperty("user.notFound");
    }

    if (isFollowing(me, toFollow)) {
      response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
      return env.getProperty("user.alreadyFollowing");
    }

    me.following.add(toFollow);
    toFollow.followers.add(me);
//    userService.save(me);
//    userService.save(toFollow);

    response.setStatus(HttpServletResponse.SC_OK);
    return "";
  }

  @PostMapping("/user/unfollow")
  @Transactional
  public String unfollow(@Valid @RequestBody FollowRequest followRequest,
                         HttpServletRequest request,
                         HttpServletResponse response) {

    HttpSession session = request.getSession(false);
    if (session == null) {
      response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
      return env.getProperty("user.notLoggedIn");
    }

    User me = (User) session.getAttribute("User");

    User toUnfollow;
    try {
      toUnfollow = userService.getUserById(followRequest.getId());
    } catch (NoSuchElementException e) {
      response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
      return env.getProperty("user.notFound");
    }

    if (!isFollowing(me, toUnfollow)) {
      response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
      return env.getProperty("user.notFollowing");
    }

    unfollow(me, toUnfollow);
//    userService.save(me);
//    userService.save(toUnfollow);

    response.setStatus(HttpServletResponse.SC_OK);
    return "";
  }

  private boolean isFollowing(User me, User you) {
    for (User following : me.following) {
      if (following.id == you.id) {
        return true;
      }
    }
    return false;
  }

  private void unfollow(User me, User you) {
    for (User possiblyYou : me.following) {
      if (possiblyYou.id == you.id) {
        me.following.remove(possiblyYou);
        break;
      }
    }

    for (User possiblyMe : you.followers) {
      if (possiblyMe.id == me.id) {
        you.followers.remove(possiblyMe);
        break;
      }
    }
  }

}
