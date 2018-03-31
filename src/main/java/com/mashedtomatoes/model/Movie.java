package com.mashedtomatoes.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@Table(name = "Movies")
@ToString(callSuper = true)
public class Movie extends Media {
    @Column
    @Getter @Setter
    private String title;

    @Column
    @Getter @Setter
    private String slug;
}