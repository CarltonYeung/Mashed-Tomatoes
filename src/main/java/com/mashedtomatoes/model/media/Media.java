package com.mashedtomatoes.model.media;

import com.mashedtomatoes.model.celebrity.Celebrity;
import com.mashedtomatoes.model.rating.Rating;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "Media")
public abstract class Media {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long ID;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String slug;

    private String description;

    private long releaseDate;

    @ManyToOne
    @JoinColumn(name = "directorID")
    private Celebrity directedBy;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "MediaCelebrities", joinColumns = {@JoinColumn(name = "mediaID")}, inverseJoinColumns = {@JoinColumn(name = "celebrityID")})
    private Set<Celebrity> celebrities = new HashSet<>();

    @OneToMany(mappedBy = "forMedia", cascade = CascadeType.ALL)
    private Set<Rating> ratings = new HashSet<>();

    public long getID() {
        return ID;
    }

    public void setID(long ID) {
        this.ID = ID;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSlug() {
        return slug;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public long getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(long releaseDate) {
        this.releaseDate = releaseDate;
    }

    public Celebrity getDirectedBy() {
        return directedBy;
    }

    public void setDirectedBy(Celebrity directedBy) {
        this.directedBy = directedBy;
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
}

// ref: https://stackoverflow.com/questions/11938253/jpa-joincolumn-vs-mappedby/11938290
