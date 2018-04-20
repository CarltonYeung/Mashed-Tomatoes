package com.mashedtomatoes.user;

import com.mashedtomatoes.exception.DuplicateDisplayNameException;
import com.mashedtomatoes.exception.DuplicateEmailException;
import com.mashedtomatoes.http.UserMediaList;
import com.mashedtomatoes.media.Media;
import com.mashedtomatoes.media.MediaService;
import com.mashedtomatoes.security.HashService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpSession;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.Set;

@Service
public class UserService {
  @Autowired
  private UserRepository userRepository;
  @Autowired
  private AudienceRepository audienceRepository;
  @Autowired
  private MediaService mediaService;
  @Autowired
  private HashService hashService;
  @Autowired
  private Environment env;

  public Audience addAudience(String displayName,
                              String email,
                              String password) throws Exception {

    if (audienceRepository.existsByDisplayName(displayName)) {
      throw new DuplicateDisplayNameException(env.getProperty("user.duplicateDisplayName"));
    }
    if (userRepository.existsByCredentials_Email(email)) {
      throw new DuplicateEmailException(env.getProperty("user.duplicateEmail"));
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

  public User getUserById(long id) throws NoSuchElementException {
    Optional<User> optional = userRepository.findFirstById(id);
    optional.orElseThrow(NoSuchElementException::new);
    return optional.get();
  }

  public void save(User user) {
    userRepository.save(user);
  }

  public void delete(User user) {
    user.following = null;
    save(user);
    for (User u : user.followers) {
      u.following.remove(user);
      save(u);
    }
    userRepository.delete(user);
  }

  void addWantToSee(User user, long mediaId) throws Exception {
    if (inList(user, mediaId, UserMediaList.NI)) {
      throw new Exception(env.getProperty("user.existsInNotInterested"));
    }

    Media media = mediaService.getMediaById(mediaId);
    if (user.wantToSee.add(media)) {
      save(user);
    }
  }

  void removeWantToSee(User user, long mediaId) {
    for (Media media : user.wantToSee) {
      if (media.getId() == mediaId) {
        user.wantToSee.remove(media);
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
    if (user.notInterested.add(media)) {
      save(user);
    }
  }

  void removeNotInterested(User user, long mediaId) {
    for (Media media : user.notInterested) {
      if (media.getId() == mediaId) {
        user.notInterested.remove(media);
        save(user);
        break;
      }
    }
  }

  private boolean inList(User user, long mediaId, UserMediaList type) {
    Set<Media> list = null;

    switch (type) {
      case WTS:
        list = user.wantToSee; break;
      case NI:
        list = user.notInterested; break;
    }

    for (Media media : list) {
      if (media.getId() == mediaId) {
        return true;
      }
    }

    return false;
  }

  public static HttpSession session() {
    ServletRequestAttributes attr  = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
    return attr.getRequest().getSession(false);
  }
}
