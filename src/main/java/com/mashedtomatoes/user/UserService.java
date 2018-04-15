package com.mashedtomatoes.user;

import com.mashedtomatoes.security.HashService;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {
  private UserRepository userRepository;
  private AudienceRepository audienceRepository;
  private HashService hashService;

  public UserService(UserRepository userRepository,
                     AudienceRepository audienceRepository,
                     HashService hashService) {

    this.userRepository = userRepository;
    this.audienceRepository = audienceRepository;
    this.hashService = hashService;
  }

  public Audience addAudience(String displayName,
                              String email,
                              String password) throws Exception {

    if (audienceRepository.existsByDisplayName(displayName)) {
      throw new Exception("This display name already exists.");
    }

    if (userRepository.existsByCredentials_Email(email)) {
      throw new Exception("This email already exists.");
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
