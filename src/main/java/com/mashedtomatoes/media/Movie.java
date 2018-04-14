package com.mashedtomatoes.media;

import java.util.List;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "Movies")
public class Movie extends Media {

    private double boxOffice;
    private String productionCompany;

    public Movie() {
    }

    public double getBoxOffice() {
        return this.boxOffice;
    }

    public void setBoxOffice(double boxOffice) {
        this.boxOffice = boxOffice;
    }

    public String getProductionCompany() {
        return productionCompany;
    }

    public void setProductionCompany(String productionCompany) {
        this.productionCompany = productionCompany;
    }
}