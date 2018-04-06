package com.mashedtomatoes.controller;

import com.mashedtomatoes.http.LoginRequestBody;
import com.mashedtomatoes.http.RegisterRequestBody;
import com.mashedtomatoes.model.StatusMessage;
import com.mashedtomatoes.model.user.User;
import com.mashedtomatoes.service.AuthService;
import com.mashedtomatoes.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@RestController
public class UserAPIController {

    @Autowired
    UserService userService;

    @Autowired
    AuthService authService;

    @PostMapping("/register")
    public StatusMessage register(@RequestBody RegisterRequestBody req){
        //Add Checking later!!!
        userService.addAudience(req.getDisplayName(),req.getEmail(), req.getPassword());
        return new StatusMessage(true, "Good job!");

    }

    @PostMapping("/login")
    public StatusMessage login(@RequestBody LoginRequestBody req, HttpServletRequest httpServletRequest){
        //Error checking later

        User user = authService.getUserByCredentials(req.getEmail(),req.getPassword());

        if(user == null){
            return new StatusMessage(false, "Failure!");
        }
        HttpSession httpSession = httpServletRequest.getSession(true);
        httpSession.setAttribute("User", user);
        return new StatusMessage(true,"whatever");


    }

    @PostMapping("/logout")
    public void logout(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse){
        httpServletRequest.getSession(false).invalidate();
        httpServletResponse.setStatus(204);
    }

    @GetMapping("/hello")
    public StatusMessage hello(HttpServletRequest httpRequest){
        HttpSession httpSession = httpRequest.getSession(false);
        if(httpSession == null){
            return new StatusMessage(false,"You not logged in you ja");
        }
        return new StatusMessage(true, "YOure logged in");
    }

}
