package com.mashedtomatoes.controller.user;

import com.mashedtomatoes.http.LoginRequest;
import com.mashedtomatoes.http.RegisterRequest;
import com.mashedtomatoes.model.StatusMessage;
import com.mashedtomatoes.model.user.Audience;
import com.mashedtomatoes.model.user.User;
import com.mashedtomatoes.service.mail.MailService;
import com.mashedtomatoes.service.security.AuthService;
import com.mashedtomatoes.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.net.UnknownHostException;

@RestController
public class UserAPIController {

    @Autowired
    private UserService userService;

    @Autowired
    private AuthService authService;

    @Autowired
    private MailService mailService;

    @PostMapping("/register")
    public StatusMessage register(@Valid @RequestBody RegisterRequest req) throws UnknownHostException {
        Audience user = null;

        // Add user to database
        try {
            user = userService.addAudience(req.getDisplayName(), req.getEmail(), req.getPassword());
        } catch (Exception e) {
            return new StatusMessage(false, e.getMessage());
        }

        // Send email verification
        System.out.println(user.getVerification().getVerificationKey());
        String email = user.getCredentials().getEmail();
        String key = user.getVerification().getVerificationKey();
        String message = "Please follow the link to verify your email.\n"
                + "http://localhost:8080/verify?email=" + email
                + "&key=" + key;

        try {
            this.mailService.send(user.getCredentials().getEmail(), message);
        } catch (MailException e) {
            System.err.println(e.getMessage());
            return new StatusMessage(false, "Failed to send verification email.");
        }

        return new StatusMessage(true, "Good job!");
    }

    @GetMapping("/verify")
    public StatusMessage verify(@RequestParam("email") String email,
                                @RequestParam("key") String key) {
        boolean success = userService.verifyEmail(email, key);
        String message;

        if (success) {
            message = "Verification successful.";
        } else {
            message = "Verification failed.";
        }

        return new StatusMessage(success, message);
    }


    @PostMapping("/login")
    public StatusMessage login(@RequestBody LoginRequest req, HttpServletRequest httpServletRequest) {
        //Error checking later

        User user = authService.getUserByCredentials(req.getEmail(), req.getPassword());

        if(user == null) {
            return new StatusMessage(false, "Failure!");
        }
        HttpSession httpSession = httpServletRequest.getSession(true);
        httpSession.setAttribute("User", user);
        return new StatusMessage(true,"whatever");


    }

    @PostMapping("/logout")
    public void logout(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        httpServletRequest.getSession(false).invalidate();
        httpServletResponse.setStatus(204);
    }

    @GetMapping("/hello")
    public StatusMessage hello(HttpServletRequest httpRequest) {
        HttpSession httpSession = httpRequest.getSession(false);
        if(httpSession == null){
            return new StatusMessage(false,"You not logged in you ja");
        }
        return new StatusMessage(true, "YOure logged in");
    }

}
