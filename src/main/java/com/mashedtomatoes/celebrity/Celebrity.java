package com.mashedtomatoes.celebrity;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.mashedtomatoes.media.Media;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "Celebrities")
public class Celebrity {

    private long ID;
    private String name;
    private Date birthday;
    private String birthplace;
    private String biography;
    private String profileImage;
    private Set<Media> media = new HashSet<>();
    private Set<Character> characters = new HashSet<>();
    private Set<Media> directorOf = new HashSet<>();

    public Celebrity() {
    }

    public String toString() {
        return "Celebrity(ID=" + this.getID() + ", name=" + this.getName() + ", birthday=" + this.getBirthday() + ", birthplace=" + this.getBirthplace() + ", biography=" + this.getBiography() + ", profileImage=" + this.getProfileImage() + ", media=" + this.getMedia() + ", characters=" + this.getCharacters() + ", directorOf=" + this.getDirectorOf() + ")";
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

    public Date getBirthday() {
        return birthday;
    }

    @JsonProperty("place_of_birth")
    public String getBirthplace() {
        return birthplace;
    }

    @Column(columnDefinition = "TEXT")
    public String getBiography() {
        return biography;
    }

    @JsonProperty("profile_path")
    public String getProfileImage() {
        return profileImage;
    }

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "MediaCelebrities", joinColumns = {@JoinColumn(name = "celebrityID")}, inverseJoinColumns = {@JoinColumn(name = "mediaID")})
    public Set<Media> getMedia() {
        return media;
    }

    @OneToMany(mappedBy = "playedBy", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    public Set<Character> getCharacters() {
        return characters;
    }

    @OneToMany(mappedBy = "directedBy", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    public Set<Media> getDirectorOf() {
        return directorOf;
    }

    public void setID(long ID) {
        this.ID = ID;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public void setBirthplace(String birthplace) {
        this.birthplace = birthplace;
    }

    public void setBiography(String biography) {
        this.biography = biography;
    }

    public void setProfileImage(String profileImage) {
        this.profileImage = profileImage;
    }

    public void setMedia(Set<Media> media) {
        this.media = media;
    }

    public void setCharacters(Set<Character> characters) {
        this.characters = characters;
    }

    public void setDirectorOf(Set<Media> directorOf) {
        this.directorOf = directorOf;
    }
}
