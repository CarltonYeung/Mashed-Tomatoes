package com.mashedtomatoes.model.publication;

import com.mashedtomatoes.model.rating.CriticRating;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@NoArgsConstructor
@Table(name = "CriticPublications")
@Getter
@Setter
public class CriticPublication extends Publication {

    @OneToOne(mappedBy = "source")
    private CriticRating rating;
}
