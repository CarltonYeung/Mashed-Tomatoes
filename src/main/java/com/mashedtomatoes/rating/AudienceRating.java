package com.mashedtomatoes.rating;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "AudienceRatings")
public class AudienceRating extends Rating {
  public AudienceRating() {}
}
