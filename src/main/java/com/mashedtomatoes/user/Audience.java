package com.mashedtomatoes.user;

import com.mashedtomatoes.media.Media;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

@Entity
@Table(name = "Audiences")
public class Audience extends User {
  private String displayName;
  private Set<Media> wantToSee;
  private Set<Media> notInterested;
  private AudienceFavorite favorites;
  private boolean superReviewer;
  private boolean publicProfile;

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
    return String.valueOf(this.getId());
  }

  @Column(nullable = false, unique = true)
  public String getDisplayName() {
    return displayName;
  }

  public void setDisplayName(String displayName) {
    this.displayName = displayName;
  }

  @ManyToMany
  @JoinTable(
    name = "AudiencesWantToSeeMedia",
    joinColumns = {@JoinColumn(name = "audienceID")},
    inverseJoinColumns = {@JoinColumn(name = "mediaID")}
  )
  public Set<Media> getWantToSee() {
    return wantToSee;
  }

  public void setWantToSee(Set<Media> wantToSee) {
    this.wantToSee = wantToSee;
  }

  @ManyToMany
  @JoinTable(
    name = "AudiencesNotInterestedMedia",
    joinColumns = {@JoinColumn(name = "audienceID")},
    inverseJoinColumns = {@JoinColumn(name = "mediaID")}
  )
  public Set<Media> getNotInterested() {
    return notInterested;
  }

  public void setNotInterested(Set<Media> notInterested) {
    this.notInterested = notInterested;
  }

  @OneToOne(mappedBy = "audience", cascade = CascadeType.ALL)
  @PrimaryKeyJoinColumn
  public AudienceFavorite getFavorites() {
    return favorites;
  }

  public void setFavorites(AudienceFavorite favorites) {
    this.favorites = favorites;
  }

  @Column(nullable = false, columnDefinition = "TINYINT(1)")
  public boolean isSuperReviewer() {
    return superReviewer;
  }

  public void setSuperReviewer(boolean superReviewer) {
    this.superReviewer = superReviewer;
  }

  @Column(nullable = false, columnDefinition = "TINYINT(1)")
  public boolean isPublicProfile() {
    return publicProfile;
  }

  public void setPublicProfile(boolean publicProfile) {
    this.publicProfile = publicProfile;
  }
}
