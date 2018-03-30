package com.mashedtomatoes.model;

import java.util.Set;
import java.util.HashSet;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.GeneratedValue;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class Media {
    @Id
    @GeneratedValue
    @Column
    private Long Id;

    @Column(nullable = false)
    private String description;

    @Column
    private String type;

    /*
    @ManyToMany()
    @JoinTable(name="medias_celebrities")
    private Set<Celebrity> celebrities = new HashSet<>();

    @ManyToMany()
    @JoinTable(name="medias_ratings")
    private Set<Rating> ratings = new HashSet<>();

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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Set<Celebrity> getCelebrities() {
        return celebrities;
    }

    public void setCelebrities(Set<Celebrity> celebrities) {
        this.celebrities = celebrities;
    }

    public Set<Rating> getRatings() {
        return ratings;
    }

    public void setRatings(Set<Rating> ratings) {
        this.ratings = ratings;
    }
    */
}
