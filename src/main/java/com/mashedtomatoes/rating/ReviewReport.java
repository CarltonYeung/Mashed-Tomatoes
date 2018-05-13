package com.mashedtomatoes.rating;

import com.mashedtomatoes.user.User;

import javax.persistence.*;

@Entity
@Table(name = "ReviewReports")
public class ReviewReport {
    private long id;
    private Rating rating;
    private String reason;

    public ReviewReport(){

    }

    @Id
    public long getId() { return id; }

    public void setId(long id) { this.id = id; }

    @MapsId
    @OneToOne
    @JoinColumn(name = "ratingId")
    public Rating getRating() { return rating; }

    public void setRating(Rating rating) { this.rating = rating; }

    public String getReason() { return reason; }

    public void setReason(String reason) { this.reason = reason; }

}
