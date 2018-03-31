package com.mashedtomatoes.model;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Audience extends User {
    @Column
    private String displayName;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name="audiences_want_to_see")
    private Set<Media> wantToSee = new HashSet<>();

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name="audiences_not_interested")
    private Set<Media> notInterested = new HashSet<>();

    @Column
    private String favoriteShow; 

    public Audience() {}
}