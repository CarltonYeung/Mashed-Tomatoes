package com.mashedtomatoes.media;

public class TVShowRatingQuery {

    private String title;

    private int id;

    private double avgRating;

    private String posterPath;

    public TVShowRatingQuery(String title, int id, double avgRating, String posterPath) {
        this.title = title;
        this.id = id;
        this.avgRating = avgRating;
        this.posterPath = posterPath;
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

    public String getPosterPath(){ return posterPath; }

    public void setPosterPath(String posterPath){ this.posterPath = posterPath; }
}
