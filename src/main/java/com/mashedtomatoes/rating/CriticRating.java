package com.mashedtomatoes.rating;

import com.mashedtomatoes.media.Media;
import com.mashedtomatoes.user.Critic;
import com.mashedtomatoes.user.User;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "CriticRatings")
public class CriticRating extends Rating {
  public CriticRating() {
  }
  public CriticRating(int score, String review, Media media, User author){
    super.setScore(score);
    super.setReview(review);
    super.setMedia(media);
    super.setAuthor(author);
  }

  public CriticRating(Rating rating, Critic critic) {
    super.setScore(rating.getScore());
    super.setReview(rating.getReview());
    super.setMedia(rating.getMedia());
    super.setAuthor(critic);
  }
}
