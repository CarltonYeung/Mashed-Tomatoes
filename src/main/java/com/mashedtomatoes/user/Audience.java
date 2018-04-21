package com.mashedtomatoes.user;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

@Entity
@Table(name = "Audiences")
public class Audience extends User {
  private String displayName;
  private AudienceFavorite favorites;
  private boolean publicProfile;

  public Audience() {}

  public Audience(String displayName, String email, String hashedPassword) {
    super(UserType.AUDIENCE);
    super.getCredentials().setEmail(email);
    super.getCredentials().setPassword(hashedPassword);
    this.displayName = displayName;
    this.favorites = new AudienceFavorite(this);
    this.publicProfile = true;
  }

  public void setDisplayName(String displayName) {
    this.displayName = displayName;
  }

  public void setFavorites(AudienceFavorite favorites) {
    this.favorites = favorites;
  }

  public void setPublicProfile(boolean publicProfile) {
    this.publicProfile = publicProfile;
  }

  @Column(nullable = false, unique = true)
  public String getDisplayName() {
    return displayName;
  }

  @OneToOne(mappedBy = "audience", cascade = CascadeType.ALL)
  @PrimaryKeyJoinColumn
  public AudienceFavorite getFavorites() {
    return favorites;
  }

  @Column(nullable = false, columnDefinition = "TINYINT(1)")
  public boolean isPublicProfile() {
    return publicProfile;
  }
}
