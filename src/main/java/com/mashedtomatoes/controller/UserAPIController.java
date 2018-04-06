package com.mashedtomatoes.controller;

import com.mashedtomatoes.model.RegisterRequestBody;
import com.mashedtomatoes.model.StatusMessage;
import com.mashedtomatoes.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserAPIController {

    @Autowired
    UserService userService;

    @PostMapping("/reqister")
    public StatusMessage registerUser(@RequestBody RegisterRequestBody resp){
        //userService.addAudience(resp);
    return null;

    }

}
