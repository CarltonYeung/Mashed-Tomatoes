package com.mashedtomatoes.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.*;

@Entity
public class Movie /* extends Media */ {

    @Id
    @GeneratedValue
    @Column
    private Long Id;

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