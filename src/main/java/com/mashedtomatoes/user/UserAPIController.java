package com.mashedtomatoes.user;

import com.mashedtomatoes.http.LoginRequest;
import com.mashedtomatoes.http.RegisterRequest;
import com.mashedtomatoes.mail.MailService;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

@RestController
public class UserAPIController {
  private UserService userService;
  private MailService mailService;

  public UserAPIController(UserService userService, MailService mailService) {
    this.userService = userService;
    this.mailService = mailService;
  }

  @PostMapping("/register")
  public void register(@Valid @RequestBody RegisterRequest request,
                       HttpServletResponse response) {

    Audience user;
    try {
      user = userService.addAudience(request.getDisplayName(), request.getEmail(), request.getPassword());
    } catch (Exception e) {
      response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
      response.setHeader("message", e.getMessage());
      return;
    }
    String to = user.getCredentials().getEmail();
    String key = user.getVerification().getVerificationKey();
    System.out.println(key);
    mailService.sendVerificationEmail(to, key);
    response.setStatus(HttpServletResponse.SC_CREATED);
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
  public void login(@Valid @RequestBody LoginRequest loginRequest,
                    HttpServletRequest request,
                    HttpServletResponse response) {

    User user = userService.getUserByCredentials(loginRequest.getEmail(), loginRequest.getPassword());
    if (user == null) {
      response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
      response.setHeader("message", "Bad credentials.");
      return;
    }
    if (!user.getVerification().isVerified()) {
      response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
      response.setHeader("message", "Email not verified.");
      return;
    }
    HttpSession session = request.getSession(true);
    session.setAttribute("User", user);
    response.setStatus(HttpServletResponse.SC_OK);
  }

  @PostMapping("/logout")
  public void logout(HttpServletRequest request,
                     HttpServletResponse response) {

    HttpSession session = request.getSession(false);
    if (session == null) {
      response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
      return;
    }
    session.invalidate();
    response.setStatus(HttpServletResponse.SC_OK);
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

}
