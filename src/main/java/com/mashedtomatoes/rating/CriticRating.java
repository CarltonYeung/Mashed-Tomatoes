package com.mashedtomatoes.rating;

import com.mashedtomatoes.publication.CriticPublication;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "CriticRatings")
public class CriticRating extends Rating {

    private CriticPublication source;

    public CriticRating() {
    }

    @OneToOne
    @JoinColumn(name = "publicationID", nullable = false)
    public CriticPublication getSource() {
        return this.source;
    }

    public void setSource(CriticPublication source) {
        this.source = source;
    }
}
