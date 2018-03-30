package com.mashedtomatoes.model;

import java.util.Set;
import java.util.HashSet;

import javax.persistence.*;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class Media {
    @Id
    @GeneratedValue
    @Column
    protected Long Id;

    @Column(nullable = false)
    protected String description;

    @OneToMany()
    @JoinTable(name="medias_ratings")
    protected Set<Rating> ratings = new HashSet<>();

    public Long getId() {
        return Id;
    }

    public void setId(Long id) {
        Id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Set<Rating> getRatings() {
        return ratings;
    }

    public void setRatings(Set<Rating> ratings) {
        this.ratings = ratings;
    }
}
