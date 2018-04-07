package com.mashedtomatoes.model.celebrity;

import com.mashedtomatoes.model.media.Media;

import javax.persistence.*;
import java.net.URL;
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

    private long birthday;

    private String birthplace;

    private String biography;

    private URL profileImage;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "MediaCelebrities", joinColumns = {@JoinColumn(name = "celebrityID")}, inverseJoinColumns = {@JoinColumn(name = "mediaID")})
    private Set<Media> media = new HashSet<>();

    @OneToMany(mappedBy = "playedBy", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<Character> characters = new HashSet<>();

    @OneToMany(mappedBy = "directedBy", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    protected Set<Media> directorOf = new HashSet<>();

    public Celebrity() {
    }

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

    public long getBirthday() {
        return birthday;
    }

    public void setBirthday(long birthday) {
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

    public URL getProfileImage() {
        return profileImage;
    }

    public void setProfileImage(URL profileImage) {
        this.profileImage = profileImage;
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


    public String toString() {
        return "Celebrity(ID=" + this.getID() + ", name=" + this.getName() + ", birthday=" + this.getBirthday() + ", birthplace=" + this.getBirthplace() + ", biography=" + this.getBiography() + ", profileImage=" + this.getProfileImage() + ", media=" + this.getMedia() + ", characters=" + this.getCharacters() + ", directorOf=" + this.getDirectorOf() + ")";
    }
}
