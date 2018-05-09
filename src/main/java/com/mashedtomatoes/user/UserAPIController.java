package com.mashedtomatoes.user;

import com.mashedtomatoes.exception.DuplicateKeyException;
import com.mashedtomatoes.http.*;
import com.mashedtomatoes.mail.MailService;
import com.mashedtomatoes.security.RecaptchaService;
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
  @Autowired private UserService userService;
  @Autowired private MailService mailService;
  @Autowired private CriticApplicationService criticApplicationService;
  @Autowired private UserReportService userReportService;
  @Autowired private RecaptchaService recaptchaService;
  @Autowired private Environment env;

  @PostMapping("/register")
  public String register(@Valid @RequestBody RegisterRequest request,
                         HttpServletResponse response,
                         HttpServletRequest httpRequest) {

    if (!recaptchaService.verifyRecaptcha(httpRequest.getRemoteAddr(), request.getRecaptchaResponse())) {
      response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
      return env.getProperty("google.recaptcha.fail");
    }

    User user;
    try {
      user = userService.addUser(request.isWantToBeAdmin(), request.getDisplayName(), request.getEmail(), request.getPassword());
    } catch (DuplicateKeyException e) {
      response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
      return e.getMessage();
    } catch (Exception e) {
      response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
      return "";
    }
    String to = user.getCredentials().getEmail();
    String key = user.getVerification().getVerificationKey();
    System.out.println(key);
    mailService.sendVerificationEmail(to, key);
    response.setStatus(HttpServletResponse.SC_CREATED);
    return "";
  }

  @GetMapping("/verify")
  public void verify(
      @RequestParam("email") String email,
      @RequestParam("key") String key,
      HttpServletResponse response) {

    if (userService.verifyEmail(email, key)) {
      response.setStatus(HttpServletResponse.SC_OK);
    } else {
      response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
    }
  }

  @PostMapping("/resendVerificationEmail")
  public String resendVerificationEmail(
      @Valid @RequestBody ResendVerificationEmailRequest request, HttpServletResponse response) {

    User user;
    try {
      user = userService.getUserByEmail(request.getEmail());
    } catch (NoSuchElementException e) {
      response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
      return env.getProperty("user.resendVerificationFailure");
    }

    user.getVerification().generateKey();
    userService.save(user);
    System.out.println(user.getVerification().getVerificationKey());
    mailService.sendVerificationEmail(
        request.getEmail(), user.getVerification().getVerificationKey());
    response.setStatus(HttpServletResponse.SC_OK);
    return env.getProperty("user.resendVerificationSuccess");
  }

  @PostMapping("/login")
  public String login(
      @Valid @RequestBody LoginRequest loginRequest,
      HttpServletRequest request,
      HttpServletResponse response) {

    User user =
        userService.getUserByCredentials(loginRequest.getEmail(), loginRequest.getPassword());
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
  public String logout(HttpServletRequest request, HttpServletResponse response) {

    HttpSession session = request.getSession(false);
    if (session == null) {
      response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
      return env.getProperty("user.notLoggedIn");
    }
    session.invalidate();
    response.setStatus(HttpServletResponse.SC_OK);
    return "";
  }

  @PostMapping("/user/follow")
  public String follow(
      @Valid @RequestBody FollowRequest followRequest,
      HttpServletRequest request,
      HttpServletResponse response) {

    HttpSession session = request.getSession(false);
    if (session == null) {
      response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
      return env.getProperty("user.notLoggedIn");
    }

    User me = (User) session.getAttribute("User");
    if (me.getId() == followRequest.getId()) {
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
    session.setAttribute("User", userService.getUserById(me.getId()));
    response.setStatus(HttpServletResponse.SC_OK);
    return "";
  }

  @PostMapping("/user/unfollow")
  public String unfollow(
      @Valid @RequestBody FollowRequest followRequest,
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
    session.setAttribute("User", userService.getUserById(me.getId()));
    response.setStatus(HttpServletResponse.SC_OK);
    return "";
  }

  private boolean isFollowing(User me, User you) {
    for (User following : me.getFollowing()) {
      if (following.getId() == you.getId()) {
        return true;
      }
    }
    return false;
  }

  @PostMapping("/userMediaLists")
  public String userMediaLists(
      @Valid @RequestBody UserMediaListsRequest umlRequest,
      HttpServletRequest request,
      HttpServletResponse response) {

    HttpSession session = request.getSession(false);
    if (session == null) {
      response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
      return env.getProperty("user.notLoggedIn");
    }

    User me = (User) session.getAttribute("User");

    switch (umlRequest.getList()) {
      case WTS:
        wantToSee(me, umlRequest);
        break;
      case NI:
        notInterested(me, umlRequest);
        break;
    }

    session.setAttribute("User", userService.getUserById(me.getId()));
    response.setStatus(HttpServletResponse.SC_OK);
    return "";
  }

  private void wantToSee(User user, UserMediaListsRequest request) {
    if (request.isAdd()) {
      userService.addWantToSee(user, request.getId());
    } else {
      userService.removeWantToSee(user, request.getId());
    }
  }

  private void notInterested(User user, UserMediaListsRequest request) {
    if (request.isAdd()) {
      userService.addNotInterested(user, request.getId());
    } else {
      userService.removeNotInterested(user, request.getId());
    }
  }

  @DeleteMapping("/user/{id}")
  public String deleteUser(@PathVariable long id, HttpServletResponse response) {

    HttpSession session = UserService.session();
    if (session == null) {
      response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
      return env.getProperty("user.notLoggedIn");
    }

    User loggedInUser = (User) session.getAttribute("User");
    if (id != loggedInUser.getId() && loggedInUser.getType() != UserType.ADMINISTRATOR) {
      response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
      return env.getProperty("user.cannotDelete");
    }

    try {
      userService.delete(id);
      if (id == loggedInUser.getId()) {
        session.invalidate();
      }
    } catch (NoSuchElementException e) {
      response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
      return env.getProperty("user.notFound");
    }
    response.setStatus(HttpServletResponse.SC_OK);
    return "";
  }

  @PostMapping("/user/changeEmail")
  public String changeEmail(
      @Valid @RequestBody ChangeEmailRequest request, HttpServletResponse response) {

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

  @PostMapping("/user/changePassword")
  public String changePassword(
      @Valid @RequestBody ChangePasswordRequest request, HttpServletResponse response) {

    HttpSession session = UserService.session();
    if (session == null) {
      response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
      return env.getProperty("user.notLoggedIn");
    }

    User user = (User) session.getAttribute("User");
    try {
      userService.changePassword(
          user, request.getOldPlaintextPassword(), request.getNewPlaintextPassword());
    } catch (IllegalArgumentException e) {
      response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
      return e.getMessage();
    }

    session.invalidate();
    response.setStatus(HttpServletResponse.SC_OK);
    return "";
  }

  @PostMapping("/user/resetPassword")
  public String resetPassword(@Valid @RequestBody ForgotPasswordRequest request,
                               HttpServletResponse response) {

    try {
      String plaintextPassword = userService.resetPassword(request.getEmail());
      System.out.println(plaintextPassword);
      mailService.sendResetPasswordEmail(request.getEmail(), plaintextPassword);
    } catch (NoSuchElementException e) {
      // Even if this email is not registered, don't let an attacker know that
    }

    response.setStatus(HttpServletResponse.SC_OK);
    return "";
  }

  @PostMapping("/user/changeDisplayName")
  public String changeDisplayName(@Valid @RequestBody ChangeDisplayNameRequest request,
                                  HttpServletResponse response) {

    HttpSession session = UserService.session();
    if (session == null) {
      response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
      return env.getProperty("user.notLoggedIn");
    }

    User user = (User) session.getAttribute("User");

    try {
      userService.changeDisplayName(user, request.getDisplayName());
    } catch (IllegalArgumentException e) {
      response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
      return e.getMessage();
    }

    session.setAttribute("User", userService.getUserById(user.getId()));
    response.setStatus(HttpServletResponse.SC_OK);
    return "";
  }

  @PostMapping("/user/changePrivacy")
  public String changePrivacy(@Valid @RequestBody ChangePrivacyRequest request,
                              HttpServletResponse response) {

    HttpSession session = UserService.session();
    if (session == null) {
      response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
      return env.getProperty("user.notLoggedIn");
    }

    User user = (User) session.getAttribute("User");
    userService.changePrivacy(user, request.isPublicProfile());
    session.setAttribute("User", userService.getUserById(user.getId()));
    response.setStatus(HttpServletResponse.SC_OK);
    return "";
  }

  @PostMapping("/user/criticApplication/apply")
  public String applyToBecomeACritic(@Valid @RequestBody CriticApplicationRequest request,
                                     HttpServletResponse response) {

    HttpSession session = UserService.session();
    if (session == null) {
      response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
      return env.getProperty("user.notLoggedIn");
    }

    User user = (User) session.getAttribute("User");
    if (!(user instanceof Audience)) {
      response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
      return env.getProperty("user.notAudience");
    }

    try {
      criticApplicationService.submitCriticApplication((Audience) user, request);
    } catch (Exception e) {
      response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
      return e.getMessage();
    }

    response.setStatus(HttpServletResponse.SC_OK);
    return "";
  }

  @DeleteMapping("/user/criticApplication/{applicantId}")
  public String acceptOrRejectCriticApplication(@Valid @RequestBody CriticApplicationStatusRequest request,
                                                @PathVariable long applicantId,
                                                HttpServletResponse response) {

    HttpSession session = UserService.session();
    if (session == null) {
      response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
      return env.getProperty("user.notLoggedIn");
    }

    User user = (User) session.getAttribute("User");
    if (!(user instanceof Administrator)) {
      response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
      return env.getProperty("user.notAdministrator");
    }

    try {
      if (request.isApproved()) {
        criticApplicationService.accept(applicantId);
      } else {
        criticApplicationService.reject(applicantId);
      }
    } catch (NoSuchElementException e) {
      response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
      return env.getProperty("criticApplication.doesNotExist");
    }

    response.setStatus(HttpServletResponse.SC_OK);
    return "";
  }

  @PostMapping("/user/report")
  public String reportUser(@Valid @RequestBody UserReportRequest request,
                           HttpServletResponse response) {

    HttpSession session = UserService.session();
    if (session == null) {
      response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
      return env.getProperty("user.notLoggedIn");
    }

    try {
      userReportService.addReport(request);
    } catch (NoSuchElementException e) {
      response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
      return env.getProperty("user.notFound");
    } catch (Exception e) {
      response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
      return e.getMessage();
    }

    response.setStatus(HttpServletResponse.SC_OK);
    return "";
  }

  @DeleteMapping("/user/report/{userId}")
  public String reportUser(@PathVariable long userId,
                           HttpServletResponse response) {

    HttpSession session = UserService.session();
    if (session == null) {
      response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
      return env.getProperty("user.notLoggedIn");
    }

    User user = (User) session.getAttribute("User");
    if (!(user instanceof Administrator)) {
      response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
      return env.getProperty("user.notAdministrator");
    }

    try {
      userReportService.removeReport(userId);
    } catch (NoSuchElementException e) {
      response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
      return env.getProperty("userReport.notFound");
    }

    response.setStatus(HttpServletResponse.SC_OK);
    return "";
  }
}
