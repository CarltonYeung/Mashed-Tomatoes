package com.mashedtomatoes.publication;

import com.mashedtomatoes.rating.CriticRating;

import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "CriticPublications")
public class CriticPublication extends Publication {

    private CriticRating rating;

    public CriticPublication() {
    }

    @OneToOne(mappedBy = "source")
    public CriticRating getRating() {
        return this.rating;
    }

    public void setRating(CriticRating rating) {
        this.rating = rating;
    }
}
