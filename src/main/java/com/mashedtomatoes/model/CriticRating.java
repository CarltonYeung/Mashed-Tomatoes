package com.mashedtomatoes.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.net.URL;

@Entity
@NoArgsConstructor
@Table(name = "CriticRatings")
@Getter
@Setter
public class CriticRating extends Rating {

    private URL originalReview;

    private URL publisherWebsite;

    private String publisherName;
}
