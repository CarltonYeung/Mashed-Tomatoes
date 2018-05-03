package com.mashedtomatoes.rating;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "CriticRatings")
public class CriticRating extends Rating {
  public CriticRating() {
  }
}
