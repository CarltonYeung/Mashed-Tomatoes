package com.mashedtomatoes.rating;

import com.mashedtomatoes.media.Media;
import com.mashedtomatoes.user.User;
import com.mashedtomatoes.user.UserRepository;
import com.mashedtomatoes.user.UserType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Iterator;

@Service
public class RatingService {
  @Autowired private AudienceRatingRepository audRatingRepository;
  @Autowired private CriticRatingRepository criticRatingRepository;
  @Autowired private RatingRepository ratingRepository;
  @Autowired private UserRepository userRepository;

  public boolean submitRating(Media media, User user, int rating, String review) {
    boolean hasRated = ratingRepository.existsByAuthorAndMedia(user, media);
    if(hasRated){
      return false;
    }
    if(user.getType() == UserType.AUDIENCE || user.getType() == UserType.ADMINISTRATOR){
      AudienceRating audienceRating = new AudienceRating(rating, review, media, user);
      user.getRatings().add(audienceRating);
      media.getRatings().add(audienceRating);
      audRatingRepository.save(audienceRating);
    }
    else{
      CriticRating criticRating = new CriticRating(rating, review, media, user);
      user.getRatings().add(criticRating);
      media.getRatings().add(criticRating);
      criticRatingRepository.save(criticRating);
    }
    return true;
  }

  public boolean deleteRating(Media media, User user, long ratingId) {
    AudienceRating audienceRating = audRatingRepository.findFirstById(ratingId);
    if (audienceRating == null) {
      return false;
    }
    Iterator<Rating> userRatingsIterator = user.getRatings().iterator();
    while (userRatingsIterator.hasNext()) {
      Rating userRating = userRatingsIterator.next();
      if (userRating.getId() == ratingId) {
        user.getRatings().remove(userRating);
        break;
      }
    }
    Iterator<Rating> movieRatingsIterator = media.getRatings().iterator();
    while (movieRatingsIterator.hasNext()) {
      Rating movieRating = movieRatingsIterator.next();
      if (movieRating.getId() == ratingId) {
        media.getRatings().remove(movieRating);
        break;
      }
    }
    audRatingRepository.deleteAudienceRating(audienceRating.getId());
    ratingRepository.deleteRating(audienceRating.getId());
    userRepository.save(user);
    return true;
  }
  public boolean updateRating(Media media, User user, int rating, String review){
    return true;
  }

  

}
