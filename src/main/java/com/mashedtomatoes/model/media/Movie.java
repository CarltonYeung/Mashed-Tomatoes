package com.mashedtomatoes.model.media;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@NoArgsConstructor
@Table(name = "Movies")
@Getter
@Setter
public class Movie extends Media {

    private double boxOffice;
}