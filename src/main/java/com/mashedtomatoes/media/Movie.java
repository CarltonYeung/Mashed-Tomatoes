package com.mashedtomatoes.media;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.HashSet;

@Entity
@Table(name = "Movies")
public class Movie extends Media {

    private double boxOffice;

    public Movie() {
        this.celebrities = new HashSet<>();
        this.ratings = new HashSet<>();
        this.genres = new HashSet<>();
    }

    public double getBoxOffice() {
        return this.boxOffice;
    }

    public void setBoxOffice(double boxOffice) {
        this.boxOffice = boxOffice;
    }
}