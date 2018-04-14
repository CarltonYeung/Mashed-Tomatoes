package com.mashedtomatoes.publication;

import com.mashedtomatoes.rating.CriticRating;

import javax.persistence.*;
import java.net.URL;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "Publishers")
public class Publisher {

    private long ID;
    private String name;
    private URL website;
    private Set<CriticRating> ratings;

    public Publisher() {
        this.ratings = new HashSet<>();
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public long getID() {
        return ID;
    }

    @Column(nullable = false)
    public String getName() {
        return name;
    }

    @Column(nullable = false)
    public URL getWebsite() {
        return website;
    }

    @OneToMany(mappedBy = "publisher", cascade = CascadeType.ALL)
    public Set<CriticRating> getRatings() {
        return ratings;
    }

    public void setID(long ID) {
        this.ID = ID;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setWebsite(URL website) {
        this.website = website;
    }

    public void setRatings(Set<CriticRating> ratings) {
        this.ratings = ratings;
    }
}
