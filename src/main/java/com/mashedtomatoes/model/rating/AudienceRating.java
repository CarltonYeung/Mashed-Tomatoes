package com.mashedtomatoes.model.rating;

import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@NoArgsConstructor
@Table(name = "AudienceRatings")
public class AudienceRating extends Rating {

}
