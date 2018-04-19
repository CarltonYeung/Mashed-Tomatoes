package com.mashedtomatoes.user;

import javax.persistence.*;

@Entity
@Table(name = "Audiences")
public class Audience extends User {
  private String displayName;
  private AudienceFavorite favorites;
  private boolean superReviewer;
  private boolean publicProfile;

  public Audience() {}

  public Audience(String displayName, String email, String hashedPassword) {
    super(UserType.AUDIENCE);
    super.getCredentials().setEmail(email);
    super.getCredentials().setPassword(hashedPassword);
    this.displayName = displayName;
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
