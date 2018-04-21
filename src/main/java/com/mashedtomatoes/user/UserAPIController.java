package com.mashedtomatoes.user;

import com.mashedtomatoes.http.*;
import com.mashedtomatoes.mail.MailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
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

  @PostMapping("/resendVerificationEmail")
  public String resendVerificationEmail(@Valid @RequestBody ResendVerificationEmailRequest request,
                                      HttpServletResponse response) {

    User user;
    try {
      user = userService.getUserByEmail(request.getEmail());
    } catch (NoSuchElementException e) {
      response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
      return env.getProperty("user.resendVerificationFailure");
    }

    user.verification.generateKey();
    userService.save(user);
    System.out.println(user.verification.getVerificationKey());
    mailService.sendVerificationEmail(request.getEmail(), user.verification.getVerificationKey());
    response.setStatus(HttpServletResponse.SC_OK);
    return env.getProperty("user.resendVerificationSuccess");
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

    userService.follow(me, toFollow);
    session.setAttribute("User", userService.getUserById(me.id));
    response.setStatus(HttpServletResponse.SC_OK);
    return "";
  }

  @PostMapping("/user/unfollow")
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

    userService.unfollow(me, toUnfollow);
    session.setAttribute("User", userService.getUserById(me.id));
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

  @PostMapping("/userMediaLists")
  public String userMediaLists(@Valid @RequestBody UserMediaListsRequest umlRequest,
                               HttpServletRequest request,
                               HttpServletResponse response) {

    HttpSession session = request.getSession(false);
    if (session == null) {
      response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
      return env.getProperty("user.notLoggedIn");
    }

    User me = (User) session.getAttribute("User");

    try {
      switch (umlRequest.getList()) {
        case WTS:
          wantToSee(me, umlRequest); break;
        case NI:
          notInterested(me, umlRequest); break;
      }
    } catch (Exception e) {
      response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
      return e.getMessage();
    }

    session.setAttribute("User", userService.getUserById(me.id));
    response.setStatus(HttpServletResponse.SC_OK);
    return "";
  }

  private void wantToSee(User user, UserMediaListsRequest request) throws Exception {
    if (request.isAdd()) {
      userService.addWantToSee(user, request.getId());
    } else {
      userService.removeWantToSee(user, request.getId());
    }
  }

  private void notInterested(User user, UserMediaListsRequest request) throws Exception {
    if (request.isAdd()) {
      userService.addNotInterested(user, request.getId());
    } else {
      userService.removeNotInterested(user, request.getId());
    }
  }

  /**
   * User deletes their own account.
   * @param id
   * @param response
   * @return
   */
  @DeleteMapping("/user/{id}")
  public String deleteUser(@PathVariable long id,
                           HttpServletResponse response) {

    HttpSession session = UserService.session();
    if (session == null) {
      response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
      return env.getProperty("user.notLoggedIn");
    }

    User user = (User) session.getAttribute("User");
    if (id != user.id) {
      response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
      return env.getProperty("user.cannotDelete");
    }

    userService.delete(user);
    session.invalidate();
    response.setStatus(HttpServletResponse.SC_OK);
    return "";
  }

  @PostMapping("/user/changeEmail")
  public String changeEmail(@Valid @RequestBody ChangeEmailRequest request,
                            HttpServletResponse response) {

    HttpSession session = UserService.session();
    if (session == null) {
      response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
      return env.getProperty("user.notLoggedIn");
    }

    User user = (User) session.getAttribute("User");
    try {
      userService.changeEmail(user, request.getNewEmail(), request.getPassword());
    } catch (Exception e) {
      response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
      return e.getMessage();
    }

    user.getVerification().generateKey();
    user.getVerification().setVerified(false);
    userService.save(user);
    String key = user.getVerification().getVerificationKey();
    System.out.println(key);
    mailService.sendVerificationEmail(user.getCredentials().getEmail(), key);

    session.invalidate();
    response.setStatus(HttpServletResponse.SC_OK);
    return "";
  }

}
