package com.mashedtomatoes.model.rating;

import com.mashedtomatoes.model.publication.CriticPublication;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@NoArgsConstructor
@Table(name = "CriticRatings")
@Getter
@Setter
public class CriticRating extends Rating {

    @OneToOne
    @JoinColumn(name = "publicationID", nullable = false)
    private CriticPublication source;
}
