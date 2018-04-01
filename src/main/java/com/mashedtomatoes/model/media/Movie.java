package com.mashedtomatoes.model.media;

import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@NoArgsConstructor
@Table(name = "Movies")
public class Movie extends Media {

}