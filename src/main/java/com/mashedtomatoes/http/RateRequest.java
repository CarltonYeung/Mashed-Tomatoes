package com.mashedtomatoes.http;

import javax.validation.constraints.NotEmpty;
import org.hibernate.validator.constraints.Range;

public class RateRequest {
  private int rating;
  private String review;

  public RateRequest() {}

  @NotEmpty
  @Range(min = 1, max = 5)
  public int getRating() {
    return rating;
  }

  public String getReview() {
    return review;
  }

  public void setRating(int rating) {
    this.rating = rating;
  }

  public void setReview(String review) {
    this.review = review;
  }
}
