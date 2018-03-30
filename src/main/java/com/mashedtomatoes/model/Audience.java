package com.mashedtomatoes.model;

import javax.persistence.Column;
import javax.persistence.Entity;

@Entity
public class Audience extends User {
    @Column
    private String profileName;

    @Column
    private String favoriteShow; 

    public Audience() {}
}