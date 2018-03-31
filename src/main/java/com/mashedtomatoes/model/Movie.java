package com.mashedtomatoes.model;

import javax.persistence.*;

@Entity
public class Movie extends Media {

    @Column
    private String title;

    @Column
    private String slug;

    public Movie() {}

    public String getTitle() {
        return title;
    }

    public String getSlug() {
        return slug;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }
}