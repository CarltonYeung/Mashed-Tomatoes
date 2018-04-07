package com.mashedtomatoes.model.rating;

import com.mashedtomatoes.model.publication.CriticPublication;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "CriticRatings")
public class CriticRating extends Rating {

    @OneToOne
    @JoinColumn(name = "publicationID", nullable = false)
    private CriticPublication source;

    public CriticRating() {
    }

    public CriticPublication getSource() {
        return this.source;
    }

    public void setSource(CriticPublication source) {
        this.source = source;
    }
}
