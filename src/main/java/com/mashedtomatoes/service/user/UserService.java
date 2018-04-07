package com.mashedtomatoes.service.user;

import com.mashedtomatoes.model.user.*;
import com.mashedtomatoes.repository.user.UserRepository;
import com.mashedtomatoes.service.security.HashService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private HashService hashService;

    public Audience addAudience(String displayName, String email, String password) {
        // Create parent and children (see OneToOne relationships in parent class)
        Audience user = new Audience(displayName);
        UserCredentials credentials = new UserCredentials(email, hashService.hash(password));
        UserVerification verification = new UserVerification();

        // Set the parent/child relationships
        user.setCredentials(credentials);
        user.setVerification(verification);
        credentials.setUser(user);
        verification.setUser(user);

        // Save the parent
        return userRepository.save(user);
    }


    public Iterable<Audience> findAllAudience() {
        return (Iterable<Audience>) this.findAllByType(UserType.AUDIENCE);
    }

    private Iterable<? extends User> findAllByType(UserType userType) {
        return userRepository.findAllByType(userType);
    }
}
