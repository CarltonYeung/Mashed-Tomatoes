package com.mashedtomatoes.model.celebrity;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.mashedtomatoes.model.media.Media;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "Celebrities")
public class Celebrity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long ID;

    @Column(nullable = false)
    private String name;

    private Date birthday;

    @JsonProperty("place_of_birth")
    private String birthplace;

    @Column(columnDefinition = "TEXT")
    private String biography;

    @JsonProperty("profile_path")
    private String profilePath;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "MediaCelebrities", joinColumns = {@JoinColumn(name = "celebrityID")}, inverseJoinColumns = {@JoinColumn(name = "mediaID")})
    private Set<Media> media = new HashSet<>();

    @OneToMany(mappedBy = "playedBy", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<Character> characters = new HashSet<>();

    @OneToMany(mappedBy = "directedBy", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<Media> directorOf = new HashSet<>();

    public long getID() {
        return ID;
    }

    public void setID(long ID) {
        this.ID = ID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public String getBirthplace() {
        return birthplace;
    }

    public void setBirthplace(String birthplace) {
        this.birthplace = birthplace;
    }

    public String getBiography() {
        return biography;
    }

    public void setBiography(String biography) {
        this.biography = biography;
    }

    public String getProfilePath() {
        return profilePath;
    }

    public void setProfilePath(String profilePath) {
        this.profilePath = profilePath;
    }

    public Set<Media> getMedia() {
        return media;
    }

    public void setMedia(Set<Media> media) {
        this.media = media;
    }

    public Set<Character> getCharacters() {
        return characters;
    }

    public void setCharacters(Set<Character> characters) {
        this.characters = characters;
    }

    public Set<Media> getDirectorOf() {
        return directorOf;
    }

    public void setDirectorOf(Set<Media> directorOf) {
        this.directorOf = directorOf;
    }
}