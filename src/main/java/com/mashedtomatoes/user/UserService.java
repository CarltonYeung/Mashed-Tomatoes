package com.mashedtomatoes.user;

import com.mashedtomatoes.security.HashService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
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
  @Autowired
  private Environment env;

  public Audience addAudience(String displayName,
                              String email,
                              String password) throws Exception {

    if (audienceRepository.existsByDisplayName(displayName)) {
      throw new Exception(env.getProperty("user.duplicateDisplayName"));
    }

    if (userRepository.existsByCredentials_Email(email)) {
      throw new Exception(env.getProperty("user.duplicateEmail"));
    }
    Audience user = new Audience(displayName, email, hashService.hash(password));
    return userRepository.save(user);
  }

  boolean verifyEmail(String email,
                      String verificationKey) {

    Optional<User> optionalUser = userRepository.findFirstByCredentials_Email(email);
    if (!optionalUser.isPresent()) {
      return false;
    }
    User user = optionalUser.get();
    UserVerification verification = user.getVerification();
    if (verification.verify(verificationKey)) {
      userRepository.save(user);
    }
    return verification.isVerified();
  }

  User getUserByCredentials(String email,
                            String plaintextPassword) {

    Optional<User> optionalUser = userRepository.findFirstByCredentials_Email(email);
    if (!optionalUser.isPresent()) {
      return null;
    }
    User user = optionalUser.get();
    if (!hashService.matches(plaintextPassword, user.getCredentials().getPassword())) {
      return null;
    }
    return user;
  }

  public Iterable<Audience> findAllAudience() {
    return (Iterable<Audience>) this.findAllByType(UserType.AUDIENCE);
  }

  private Iterable<? extends User> findAllByType(UserType userType) {
    return userRepository.findAllByType(userType);
  }
}
