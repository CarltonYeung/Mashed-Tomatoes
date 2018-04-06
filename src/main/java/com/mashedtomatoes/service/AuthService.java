package com.mashedtomatoes.service;

import com.mashedtomatoes.model.user.Audience;
import com.mashedtomatoes.model.user.User;
import com.mashedtomatoes.model.user.UserCredentials;
import com.mashedtomatoes.model.user.UserVerification;
import com.mashedtomatoes.repository.user.UserCredentialsRepository;
import com.mashedtomatoes.repository.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.Optional;

@Service
public class AuthService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserCredentialsRepository userCredentialsRepositoryRepository;

    @Autowired
    private HashService hashService;


    public User getUserByCredentials(String email, String password){
        Optional<User> optionalUser = userRepository.findFirstByCredentials_Email(email);
        if(!optionalUser.isPresent()){
            return null;
        }
        User u = optionalUser.get();
        if(!hashService.matches(password,u.getCredentials().getPassword())){
            return null;
        }
        return u;

    }
}
