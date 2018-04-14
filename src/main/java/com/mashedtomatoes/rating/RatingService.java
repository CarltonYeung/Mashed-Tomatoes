package com.mashedtomatoes.rating;

import com.mashedtomatoes.media.Movie;
import com.mashedtomatoes.user.User;
import com.mashedtomatoes.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Iterator;

@Service
public class RatingService {

    @Autowired
    private AudienceRatingRepository audRatingRepository;

    @Autowired
    private RatingRepository ratingRepository;

    @Autowired
    private UserRepository userRepository;

    public void submitAudienceRating(Movie movie, User user, int rating, String review){
        AudienceRating audienceRating = new AudienceRating();
        audienceRating.setScore(rating);
        audienceRating.setReview(review);
        audienceRating.setForMedia(movie);
        audienceRating.setAuthor(user);
        user.getRatings().add(audienceRating);
        movie.getRatings().add(audienceRating);
        audRatingRepository.save(audienceRating);
    }

    public boolean deleteAudienceRating(Movie movie, User user, int ratingId){
        AudienceRating audienceRating = audRatingRepository.findFirstById((long)ratingId);
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
        Iterator<Rating> movieRatingsIterator = movie.getRatings().iterator();
        while (movieRatingsIterator.hasNext()) {
            Rating movieRating = movieRatingsIterator.next();
            if (movieRating.getId() == ratingId) {
                movie.getRatings().remove(movieRating);
                break;
            }
        }
        //audienceRating.setReview("Saved worked");
        audRatingRepository.deleteAudienceRating(audienceRating.getId());
        ratingRepository.deleteRating(audienceRating.getId());
        //ratingRepository.deleteFirstById(audienceRating.getId());
        userRepository.save(user);
        return true;
    }

}
