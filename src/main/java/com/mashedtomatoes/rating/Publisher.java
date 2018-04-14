package com.mashedtomatoes.rating;

import javax.persistence.*;
import java.net.URL;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "Publishers")
public class Publisher {

    private long id;
    private String name;
    private URL website;
    private Set<CriticRating> ratings;

    public Publisher() {
        this.ratings = new HashSet<>();
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Column(nullable = false)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(nullable = false)
    public URL getWebsite() {
        return website;
    }

    public void setWebsite(URL website) {
        this.website = website;
    }

    @OneToMany(mappedBy = "publisher", cascade = CascadeType.ALL)
    public Set<CriticRating> getRatings() {
        return ratings;
    }

    public void setRatings(Set<CriticRating> ratings) {
        this.ratings = ratings;
    }
}
