package com.mashedtomatoes.media;

public class TVShowRatingQuery {

    private String title;
    private int id;
    private double avgRating;

    public TVShowRatingQuery(String title, int id, double avgRating) {
        this.title = title;
        this.id = id;
        this.avgRating = avgRating;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getAvgRating() {
        return avgRating;
    }

    public void setAvgRating(double avgRating) {
        this.avgRating = avgRating;
    }
}
