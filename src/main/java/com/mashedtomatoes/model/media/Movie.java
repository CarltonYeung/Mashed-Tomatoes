package com.mashedtomatoes.model.media;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "Movies")
public class Movie extends Media {

    private double boxOffice;

    public Movie() {
    }

    public double getBoxOffice() {
        return this.boxOffice;
    }

    public void setBoxOffice(double boxOffice) {
        this.boxOffice = boxOffice;
    }
}