package com.mashedtomatoes.rating;

import com.mashedtomatoes.media.Media;
import com.mashedtomatoes.media.Movie;
import com.mashedtomatoes.user.User;
import com.mashedtomatoes.user.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Iterator;

@Service
public class RatingService {
  private AudienceRatingRepository audRatingRepository;
  private RatingRepository ratingRepository;
  private UserRepository userRepository;

  public RatingService(AudienceRatingRepository audRatingRepository,
                       RatingRepository ratingRepository,
                       UserRepository userRepository) {

    this.audRatingRepository = audRatingRepository;
    this.ratingRepository = ratingRepository;
    this.userRepository = userRepository;
  }

  public void submitAudienceRating(Movie movie, User user, int rating, String review) {
    AudienceRating audienceRating = new AudienceRating();
    audienceRating.setScore(rating);
    audienceRating.setReview(review);
    audienceRating.setMedia(movie);
    audienceRating.setAuthor(user);
    user.getRatings().add(audienceRating);
    movie.getRatings().add(audienceRating);
    audRatingRepository.save(audienceRating);
  }

  public boolean deleteAudienceRating(Media media, User user, long ratingId) {
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
  public boolean updateAudienceRating(Media media, User user, int rating, String review){
    return true;
  }

}
