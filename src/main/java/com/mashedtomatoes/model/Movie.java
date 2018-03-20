package com.mashedtomatoes.model;

import javax.persistence.*;

@Entity
public class Movie {
    @Id
    @GeneratedValue
    @Column
    private Long ID;

    @Column
    private String title;

    @Column
    private String slug;

    public Movie() {}

    public Long getID() {
        return ID;
    }

    public String getTitle() {
        return title;
    }

    public String getSlug() {
        return slug;
    }

    public void setId(Long ID) {
        this.ID = ID;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }
}