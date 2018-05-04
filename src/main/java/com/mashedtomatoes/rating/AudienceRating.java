package com.mashedtomatoes.rating;

import com.mashedtomatoes.media.Media;
import com.mashedtomatoes.user.User;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "AudienceRatings")
public class AudienceRating extends Rating {

  public AudienceRating() { }

  public AudienceRating(int score, String review, Media media, User author) {
    super.setScore(score);
    super.setReview(review);
    super.setMedia(media);
    super.setAuthor(author);
  }

}
