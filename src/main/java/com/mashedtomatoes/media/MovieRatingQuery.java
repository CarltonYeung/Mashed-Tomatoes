package com.mashedtomatoes.media;

import java.util.Date;

public class MovieRatingQuery {

    private double avgRating;

    private String title;

    private long id;

    private double boxOffice;

    private Date releaseDate;

    public MovieRatingQuery(double avgRating, String title, long id, double boxOffice, Date releaseDate) {
        this.avgRating = avgRating;
        this.title = title;
        this.id = id;
        this.boxOffice = boxOffice;
        this.releaseDate = releaseDate;
    }

    public double getAvgRating() {
        return avgRating;
    }

    public void setAvgRating(double avgRating) {
        this.avgRating = avgRating;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public double getBoxOffice() {
        return boxOffice;
    }

    public void setBoxOffice(double boxOffice) {
        this.boxOffice = boxOffice;
    }

    public Date getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(Date releaseDate) {
        this.releaseDate = releaseDate;
    }
}
