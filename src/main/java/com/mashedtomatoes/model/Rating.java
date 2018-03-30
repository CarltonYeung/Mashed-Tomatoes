package com.mashedtomatoes.model;

import javax.persistence.*;

@Entity
public class Rating {
    @Id
    @GeneratedValue
    @Column
    private Long Id;

    @Column(nullable = false)
    private Integer rating;

    @Column
    private String review;

    @ManyToOne()
    @JoinColumn(name="media_id", nullable = false)
    private Media target;

    public Rating() {
    }

    public Rating(Integer rating, String review) {
        this.rating = rating;
        this.review = review;
    }

    public Long getId() {
        return Id;
    }

    public void setId(Long id) {
        Id = id;
    }

    public Integer getRating() {
        return rating;
    }

    public void setRating(Integer rating) {
        this.rating = rating;
    }

    public String getReview() {
        return review;
    }

    public void setReview(String review) {
        this.review = review;
    }

    /*
    public Media getTarget() {
        return target;
    }

    public void setTarget(Media target) {
        this.target = target;
    }
    */

    //- date: int
//- linkToCriticReview: URL
}
