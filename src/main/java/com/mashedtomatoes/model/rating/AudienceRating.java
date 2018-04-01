package com.mashedtomatoes.model.rating;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@NoArgsConstructor
@Table(name = "AudienceRatings")
@Getter
@Setter
public class AudienceRating extends Rating {

}
