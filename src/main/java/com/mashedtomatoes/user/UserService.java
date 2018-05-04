package com.mashedtomatoes.user;

import com.mashedtomatoes.exception.DuplicateKeyException;
import com.mashedtomatoes.http.UserMediaList;
import com.mashedtomatoes.media.Media;
import com.mashedtomatoes.media.MediaRepository;
import com.mashedtomatoes.media.MediaService;
import com.mashedtomatoes.rating.Rating;
import com.mashedtomatoes.rating.RatingService;
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
import java.util.UUID;

@Service
public class UserService {
  @Autowired private UserRepository userRepository;
  @Autowired private MediaRepository mediaRepository;
  @Autowired private MediaService mediaService;
  @Autowired private RatingService ratingService;
  @Autowired private HashService hashService;
  @Autowired private Environment env;

  public static HttpSession session() {
    ServletRequestAttributes attr =
        (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
    return attr.getRequest().getSession(false);
  }

  public User addUser(boolean wantToBeAdmin, String displayName, String email, String password) throws Exception {
    if (userRepository.existsByDisplayName(displayName)) {
      throw new DuplicateKeyException(env.getProperty("user.duplicateDisplayName"));
    }

    if (userRepository.existsByCredentials_Email(email)) {
      throw new DuplicateKeyException(env.getProperty("user.duplicateEmail"));
    }

    User user = wantToBeAdmin
            ? new Administrator(displayName, email, hashService.hash(password))
            : new Audience(displayName, email, hashService.hash(password));

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

  public User save(User user) {
    return userRepository.save(user);
  }

  public void delete(long id) throws NoSuchElementException {
    Optional<User> optional = userRepository.findFirstById(id);
    optional.orElseThrow(NoSuchElementException::new);
    User user = optional.get();

    user.setFollowing(null);
    save(user);

    for (User u : user.getFollowers()) {
      u.getFollowing().remove(user);
      save(u);
    }

    for (Rating r : user.getRatings()) {
      Media m = mediaService.getMediaById(r.getMedia().getId());
      ratingService.deleteAudienceRating(m, user, r.getId());
    }

    user.setRatings(null);
    save(user);

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

  public void follow(User me, User you) {
    me.getFollowing().add(you);
    you.getFollowers().add(me);
    save(me);
    save(you);
  }

  public void unfollow(User me, User you) {
    for (User possiblyYou : me.getFollowing()) {
      if (possiblyYou.getId() == you.getId()) {
        me.getFollowing().remove(possiblyYou);
        break;
      }
    }

    for (User possiblyMe : you.getFollowers()) {
      if (possiblyMe.getId() == me.getId()) {
        you.getFollowers().remove(possiblyMe);
        break;
      }
    }

    save(me);
    save(you);
  }

  public void changeEmail(User user, String newEmail, String plaintextPassword)
      throws IllegalArgumentException {
    if (userRepository.existsByCredentials_Email(newEmail)) {
      throw new IllegalArgumentException(env.getProperty("user.duplicateEmail"));
    }

    if (!hashService.matches(plaintextPassword, user.getCredentials().getPassword())) {
      throw new IllegalArgumentException(env.getProperty("user.badCredentials"));
    }

    user.getCredentials().setEmail(newEmail);
    save(user);
  }

  public void changePassword(User user, String oldPlaintextPassword, String newPlaintextPassword)
      throws IllegalArgumentException {
    if (!hashService.matches(oldPlaintextPassword, user.getCredentials().getPassword())) {
      throw new IllegalArgumentException(env.getProperty("user.badCredentials"));
    }

    user.getCredentials().setPassword(hashService.hash(newPlaintextPassword));
    save(user);
  }

  public String resetPassword(String email) throws NoSuchElementException {
    Optional<User> optional = userRepository.findFirstByCredentials_Email(email);
    optional.orElseThrow(NoSuchElementException::new);
    User user = optional.get();

    String plaintextPassword = UUID.randomUUID().toString().replace("-", "");
    user.getCredentials().setPassword(hashService.hash(plaintextPassword));
    save(user);
    return plaintextPassword;
  }

  public void changeDisplayName(User user, String displayName) throws IllegalArgumentException {
    if (userRepository.existsByDisplayName(displayName)) {
      throw new IllegalArgumentException(env.getProperty("user.duplicateDisplayName"));
    }

    user.setDisplayName(displayName);
    save(user);
  }

  public void changePrivacy(User user, boolean isPublic) {
    user.setPublicProfile(isPublic);
    save(user);
  }
}
