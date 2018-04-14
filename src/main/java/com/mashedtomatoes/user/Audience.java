package com.mashedtomatoes.user;

import com.mashedtomatoes.media.Media;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "Audiences")
public class Audience extends User {

    private String displayName;
    private Set<Media> wantToSee;
    private Set<Media> notInterested;
    private AudienceFavorite favorites;
    private boolean superReviewer;
    private boolean publicProfile;

    /**
     * Hibernate needs default constructor for entities.
     */
    public Audience() {}

    public Audience(String displayName, String email, String hashedPassword) {
        super(UserType.AUDIENCE);
        super.getCredentials().setEmail(email);
        super.getCredentials().setPassword(hashedPassword);
        this.displayName = displayName;
        this.wantToSee = new HashSet<>();
        this.notInterested = new HashSet<>();
        this.favorites = new AudienceFavorite(this);
        this.superReviewer = false;
        this.publicProfile = true;
    }

    @Override
    public String toString() {
        return String.valueOf(this.getID());
    }

    @Column(nullable = false, unique = true)
    public String getDisplayName() {
        return displayName;
    }

    @ManyToMany
    @JoinTable(name = "AudiencesWantToSeeMedia", joinColumns = {@JoinColumn(name = "audienceID")}, inverseJoinColumns = {@JoinColumn(name = "mediaID")})
    public Set<Media> getWantToSee() {
        return wantToSee;
    }

    @ManyToMany
    @JoinTable(name = "AudiencesNotInterestedMedia", joinColumns = {@JoinColumn(name = "audienceID")}, inverseJoinColumns = {@JoinColumn(name = "mediaID")})
    public Set<Media> getNotInterested() {
        return notInterested;
    }

    @OneToOne(mappedBy = "audience", cascade = CascadeType.ALL)
    @PrimaryKeyJoinColumn
    public AudienceFavorite getFavorites() {
        return favorites;
    }

    @Column(nullable = false, columnDefinition = "TINYINT(1)")
    public boolean isSuperReviewer() {
        return superReviewer;
    }

    @Column(nullable = false, columnDefinition = "TINYINT(1)")
    public boolean isPublicProfile() {
        return publicProfile;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public void setWantToSee(Set<Media> wantToSee) {
        this.wantToSee = wantToSee;
    }

    public void setNotInterested(Set<Media> notInterested) {
        this.notInterested = notInterested;
    }

    public void setFavorites(AudienceFavorite favorites) {
        this.favorites = favorites;
    }

    public void setSuperReviewer(boolean superReviewer) {
        this.superReviewer = superReviewer;
    }

    public void setPublicProfile(boolean publicProfile) {
        this.publicProfile = publicProfile;
    }
}