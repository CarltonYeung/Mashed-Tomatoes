package com.mashedtomatoes.service.user;

import com.mashedtomatoes.model.user.*;
import com.mashedtomatoes.repository.user.AudienceRepository;
import com.mashedtomatoes.repository.user.UserRepository;
import com.mashedtomatoes.service.security.HashService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AudienceRepository audienceRepository;

    @Autowired
    private HashService hashService;

    /**
     * Responsible for creating an Audience and persisting it.
     * @param displayName
     * @param email
     * @param password
     * @return
     * @throws Exception
     */
    public Audience addAudience(String displayName, String email, String password) throws Exception {
        if (audienceRepository.existsByDisplayName(displayName)) {
            throw new Exception("This display name already exists.");
        }

        if (userRepository.existsByCredentials_Email(email)) {
            throw new Exception("This email already exists.");
        }

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

    public boolean verifyEmail(String email, String verificationKey) {
        Optional<User> optionalUser = userRepository.findFirstByCredentials_Email(email);

        if (!optionalUser.isPresent()) {
            return false;
        }

        User user = optionalUser.get();
        UserVerification verification = user.getVerification();

        if (verification.verify(verificationKey)) {
            userRepository.save(user);
            return true;
        }

        return false;
    }

    public Iterable<Audience> findAllAudience() {
        return (Iterable<Audience>) this.findAllByType(UserType.AUDIENCE);
    }

    private Iterable<? extends User> findAllByType(UserType userType) {
        return userRepository.findAllByType(userType);
    }
}
