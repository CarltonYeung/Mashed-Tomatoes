package com.mashedtomatoes.model;

import java.util.List;
import java.util.Set;
import java.util.HashSet;

import javax.persistence.*;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class Media {

    @Id
    @GeneratedValue
    protected Long id;

    @Column(nullable = false)
    protected String description;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "media")
    protected Set<Rating> ratings = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "media")
    protected Set<Character> characters = new HashSet<>();
}

// ref: https://stackoverflow.com/questions/11938253/jpa-joincolumn-vs-mappedby/11938290
