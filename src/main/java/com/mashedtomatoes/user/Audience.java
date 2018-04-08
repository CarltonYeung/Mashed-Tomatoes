package com.mashedtomatoes.user;

import com.mashedtomatoes.media.Media;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "Audiences")
public class Audience extends User {

    @Column(nullable = false, unique = true)
    private String displayName;

    @ManyToMany
    @JoinTable(name = "AudiencesWantToSeeMedia", joinColumns = {@JoinColumn(name = "audienceID")}, inverseJoinColumns = {@JoinColumn(name = "mediaID")})
    private Set<Media> wantToSee;

    @ManyToMany
    @JoinTable(name = "AudiencesNotInterestedMedia", joinColumns = {@JoinColumn(name = "audienceID")}, inverseJoinColumns = {@JoinColumn(name = "mediaID")})
    private Set<Media> notInterested;

    @OneToOne
    @PrimaryKeyJoinColumn
    private AudienceFavorite favorites;

    @Column(nullable = false, columnDefinition = "TINYINT(1)")
    private boolean superReviewer;

    @Column(nullable = false, columnDefinition = "TINYINT(1)")
    private boolean publicProfile;

    public Audience() {

    }

    public Audience(String displayName) {
        this.setType(UserType.AUDIENCE);
        this.setBanned(false);
        this.displayName = displayName;
        this.wantToSee = new HashSet<>();
        this.notInterested = new HashSet<>();
        this.superReviewer = false;
        this.publicProfile = false;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public Set<Media> getWantToSee() {
        return wantToSee;
    }

    public void setWantToSee(Set<Media> wantToSee) {
        this.wantToSee = wantToSee;
    }

    public Set<Media> getNotInterested() {
        return notInterested;
    }

    public void setNotInterested(Set<Media> notInterested) {
        this.notInterested = notInterested;
    }

    public AudienceFavorite getFavorites() {
        return favorites;
    }

    public void setFavorites(AudienceFavorite favorites) {
        this.favorites = favorites;
    }

    public boolean isSuperReviewer() {
        return superReviewer;
    }

    public void setSuperReviewer(boolean superReviewer) {
        this.superReviewer = superReviewer;
    }

    public boolean isPublicProfile() {
        return publicProfile;
    }

    public void setPublicProfile(boolean publicProfile) {
        this.publicProfile = publicProfile;
    }

    @Override
    public String toString() {
        return String.valueOf(this.getID());
    }
}