package com.mashedtomatoes.service.user;

import com.mashedtomatoes.model.user.Audience;
import com.mashedtomatoes.model.user.UserCredentials;
import com.mashedtomatoes.model.user.UserVerification;
import com.mashedtomatoes.repository.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public Audience addAudience(String displayName, String email, char[] password) {
        // Create parent and children (see OneToOne relationships in parent class)
        Audience user = new Audience(displayName);
        UserCredentials credentials = new UserCredentials(email, password);
        UserVerification verification = new UserVerification();

        // Set the parent/child relationships
        user.setCredentials(credentials);
        user.setVerification(verification);
        credentials.setUser(user);
        verification.setUser(user);

        // Save the parent
        return userRepository.save(user);
    }
}
