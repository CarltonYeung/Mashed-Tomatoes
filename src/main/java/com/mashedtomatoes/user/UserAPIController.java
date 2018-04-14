package com.mashedtomatoes.user;

import com.mashedtomatoes.http.LoginRequest;
import com.mashedtomatoes.http.RegisterRequest;
import com.mashedtomatoes.http.StatusMessage;
import com.mashedtomatoes.mail.MailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

@RestController
public class UserAPIController {

    @Autowired
    private UserService userService;

    @Autowired
    private MailService mailService;

    @PostMapping("/register")
    public StatusMessage register(@Valid @RequestBody RegisterRequest request) {
        Audience user;
        try {
            user = userService.addAudience(request.getDisplayName(), request.getEmail(), request.getPassword());
        } catch (Exception e) {
            return new StatusMessage(false, e.getMessage());
        }
        System.out.println(user.getVerification().getVerificationKey());
        String email = user.getCredentials().getEmail();
        String key = user.getVerification().getVerificationKey();
        String message = "Please follow the link to verify your email.\n"
                + "http://localhost:8080/verify?email=" + email
                + "&key=" + key;
        try {
            this.mailService.send(user.getCredentials().getEmail(), "Verify your Mashed Tomatoes email", message);
        } catch (MailException e) {
            System.err.println(e.getMessage());
            return new StatusMessage(false, "Failed to send verification email.");
        }
        return new StatusMessage(true, "Success! Please check your email for verification details.");
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
    public StatusMessage login(@Valid @RequestBody LoginRequest req,
                               HttpServletRequest httpServletRequest) {
        User user = userService.getUserByCredentials(req.getEmail(), req.getPassword());

        if (user == null) {
            return new StatusMessage(false, "Login failed.");
        }

        if (!user.getVerification().isVerified()) {
            return new StatusMessage(false, "Please verify your email.");
        }

        HttpSession httpSession = httpServletRequest.getSession(true);
        httpSession.setAttribute("User", user);

        return new StatusMessage(true, "Login successful.");
    }

    @PostMapping("/logout")
    public void logout(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        HttpSession session = httpServletRequest.getSession(false);
        if (session == null) {
            httpServletResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }
        session.invalidate();
        httpServletResponse.setStatus(HttpServletResponse.SC_ACCEPTED);
    }

    @GetMapping("/hello")
    public StatusMessage hello(HttpServletRequest httpRequest) {
        HttpSession httpSession = httpRequest.getSession(false);

        if (httpSession == null) {
            return new StatusMessage(false, "You are not logged in.");
        }

        return new StatusMessage(true, "You are logged in.");
    }

}
