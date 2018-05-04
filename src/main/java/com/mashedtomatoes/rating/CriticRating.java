package com.mashedtomatoes.rating;

import com.mashedtomatoes.user.Critic;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "CriticRatings")
public class CriticRating extends Rating {
  public CriticRating() {
  }

  public CriticRating(Rating rating, Critic critic) {
    super.setScore(rating.getScore());
    super.setReview(rating.getReview());
    super.setMedia(rating.getMedia());
    super.setAuthor(critic);
  }
}
