package com.mashedtomatoes.http;

import org.hibernate.validator.constraints.Range;
import org.springframework.beans.factory.annotation.Value;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

public class RateRequest {

    private int rating;
    private String review;

    public RateRequest() {
    }

    @NotEmpty
    @Range(min=1,max=5)
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
