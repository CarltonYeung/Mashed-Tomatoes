package com.mashedtomatoes.media;

public class MediaRatingQuery {
    private double avgRating;
    private String title;
    private int id;
    private String description;
    private String posterPath;

    public MediaRatingQuery(double avgRating, String title, int id, String description, String posterPath) {
        this.avgRating = avgRating;
        this.title = title;
        this.id = id;
        this.description = description;
        this.posterPath = posterPath;
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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public void setPosterPath(String posterPath) {
        this.posterPath = posterPath;
    }
}
