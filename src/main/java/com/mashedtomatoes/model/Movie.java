package com.mashedtomatoes.model;

import lombok.*;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@Table(name = "Movies")
@ToString(callSuper = true)
public class Movie extends Media {

}