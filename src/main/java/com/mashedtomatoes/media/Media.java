package com.mashedtomatoes.media;

import com.mashedtomatoes.celebrity.Celebrity;
import com.mashedtomatoes.rating.Rating;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "Media")
public abstract class Media {

    private long ID;
    private String title;
    private String slug;
    private String description;
    private String backdropPath;
    private String posterPath;
    private Date releaseDate;
    private int runTime;
    private Celebrity directedBy;
    private Set<Celebrity> celebrities = new HashSet<>();
    private Set<Rating> ratings = new HashSet<>();
    private Set<Genre> genres = new HashSet<>();

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public long getID() {
        return ID;
    }

    @Column(nullable = false)
    public String getTitle() {
        return title;
    }

    @Column(nullable = false)
    public String getSlug() {
        return slug;
    }

    @Column(columnDefinition = "TEXT")
    public String getDescription() {
        return description;
    }

    public Date getReleaseDate() {
        return releaseDate;
    }

    public int getRunTime() {
        return runTime;
    }

    public String getBackdropPath() {
        return backdropPath;
    }

    public String getPosterPath() {
        return posterPath;
    }

    @ManyToOne
    @JoinColumn(name = "directorID")
    public Celebrity getDirectedBy() {
        return directedBy;
    }

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "MediaCelebrities", joinColumns = {@JoinColumn(name = "mediaID")}, inverseJoinColumns = {@JoinColumn(name = "celebrityID")})
    public Set<Celebrity> getCelebrities() {
        return celebrities;
    }

    @OneToMany(mappedBy = "forMedia", cascade = CascadeType.ALL)
    public Set<Rating> getRatings() {
        return ratings;
    }

    @ElementCollection(targetClass = Genre.class)
    @CollectionTable(name = "MediaGenres", joinColumns = {@JoinColumn(name = "mediaID")})
    @Enumerated(EnumType.STRING)
    @Column(name = "genre", nullable = false, length = 32)
    public Set<Genre> getGenres() {
        return genres;
    }

    public void setID(long ID) {
        this.ID = ID;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setReleaseDate(Date releaseDate) {
        this.releaseDate = releaseDate;
    }

    public void setRunTime(int runTime) {
        this.runTime = runTime;
    }

    public void setBackdropPath(String backdropPath) {
        this.backdropPath = backdropPath;
    }

    public void setPosterPath(String posterPath) {
        this.posterPath = posterPath;
    }

    public void setDirectedBy(Celebrity directedBy) {
        this.directedBy = directedBy;
    }

    public void setCelebrities(Set<Celebrity> celebrities) {
        this.celebrities = celebrities;
    }

    public void setRatings(Set<Rating> ratings) {
        this.ratings = ratings;
    }

    public void setGenres(Set<Genre> genres) {
        this.genres = genres;
    }
}
