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

  public boolean getRatingById(long id){
    return ratingRepository.existsById(id);
  }

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
    if(user.getType() != UserType.ADMINISTRATOR){
      boolean userOwns = ratingRepository.existsByAuthorAndIdAndMedia(user, ratingId, media);
      if(userOwns == false){
        return false;
      }
    }
    Iterator<Rating> userRatingsIterator = user.getRatings().iterator();
    while (userRatingsIterator.hasNext()) {
      Rating userRating = userRatingsIterator.next();
      if (userRating.getId() == ratingId) {
        user.getRatings().remove(userRating);
        break;
      }
    }
    Iterator<Rating> mediaRatingsIterator = media.getRatings().iterator();
    while (mediaRatingsIterator.hasNext()) {
      Rating mediaRating = mediaRatingsIterator.next();
      if (mediaRating.getId() == ratingId) {
        media.getRatings().remove(mediaRating);
        break;
      }
    }
    if(user.getType() == UserType.CRITIC){
      criticRatingRepository.deleteById(ratingId);
    }
    else{
      audRatingRepository.deleteAudienceRating(ratingId);
    }
    ratingRepository.deleteRating(ratingId);
    userRepository.save(user);
    return true;
  }
  public boolean updateRating(Media media, User user, int ratingNum, String review) {
    Rating rating = ratingRepository.findFirstByAuthorAndMedia(user, media);
    if(rating == null){
      return false;
    }
    rating.setScore(ratingNum);
    rating.setReview(review);
    ratingRepository.save(rating);
    return true;
  }

  

}
