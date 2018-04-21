package com.mashedtomatoes.user;

import com.mashedtomatoes.exception.DuplicateDisplayNameException;
import com.mashedtomatoes.exception.DuplicateEmailException;
import com.mashedtomatoes.http.UserMediaList;
import com.mashedtomatoes.media.Media;
import com.mashedtomatoes.media.MediaService;
import com.mashedtomatoes.security.HashService;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.Set;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Service
public class UserService {
  @Autowired private UserRepository userRepository;
  @Autowired private AudienceRepository audienceRepository;
  @Autowired private MediaService mediaService;
  @Autowired private HashService hashService;
  @Autowired private Environment env;

  public static HttpSession session() {
    ServletRequestAttributes attr =
        (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
    return attr.getRequest().getSession(false);
  }

  public Audience addAudience(String displayName, String email, String password) throws Exception {

    if (audienceRepository.existsByDisplayName(displayName)) {
      throw new DuplicateDisplayNameException(env.getProperty("user.duplicateDisplayName"));
    }
    if (userRepository.existsByCredentials_Email(email)) {
      throw new DuplicateEmailException(env.getProperty("user.duplicateEmail"));
    }
    Audience user = new Audience(displayName, email, hashService.hash(password));
    return userRepository.save(user);
  }

  boolean verifyEmail(String email, String verificationKey) {

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

  User getUserByCredentials(String email, String plaintextPassword) {

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

  User getUserByEmail(String email) throws NoSuchElementException {
    Optional<User> optional = userRepository.findFirstByCredentials_Email(email);
    optional.orElseThrow(NoSuchElementException::new);
    return optional.get();
  }

  public Iterable<Audience> findAllAudience() {
    return (Iterable<Audience>) this.findAllByType(UserType.AUDIENCE);
  }

  private Iterable<? extends User> findAllByType(UserType userType) {
    return userRepository.findAllByType(userType);
  }

  public User getUserById(long id) throws NoSuchElementException {
    Optional<User> optional = userRepository.findFirstById(id);
    optional.orElseThrow(NoSuchElementException::new);
    return optional.get();
  }

  public void save(User user) {
    userRepository.save(user);
  }

  public void delete(User user) {
    user.setFollowing(null);
    save(user);
    for (User u : user.getFollowers()) {
      u.getFollowing().remove(user);
      save(u);
    }
    userRepository.delete(user);
  }

  void addWantToSee(User user, long mediaId) throws Exception {
    if (inList(user, mediaId, UserMediaList.NI)) {
      throw new Exception(env.getProperty("user.existsInNotInterested"));
    }

    Media media = mediaService.getMediaById(mediaId);
    if (user.getWantToSee().add(media)) {
      save(user);
    }
  }

  void removeWantToSee(User user, long mediaId) {
    for (Media media : user.getWantToSee()) {
      if (media.getId() == mediaId) {
        user.getWantToSee().remove(media);
        save(user);
        break;
      }
    }
  }

  void addNotInterested(User user, long mediaId) throws Exception {
    if (inList(user, mediaId, UserMediaList.WTS)) {
      throw new Exception(env.getProperty("user.existsInWantToSee"));
    }

    Media media = mediaService.getMediaById(mediaId);
    if (user.getNotInterested().add(media)) {
      save(user);
    }
  }

  void removeNotInterested(User user, long mediaId) {
    for (Media media : user.getNotInterested()) {
      if (media.getId() == mediaId) {
        user.getNotInterested().remove(media);
        save(user);
        break;
      }
    }
  }

  private boolean inList(User user, long mediaId, UserMediaList type) {
    Set<Media> list = null;

    switch (type) {
      case WTS:
        list = user.getWantToSee();
        break;
      case NI:
        list = user.getNotInterested();
        break;
    }

    for (Media media : list) {
      if (media.getId() == mediaId) {
        return true;
      }
    }

    return false;
  }
}
