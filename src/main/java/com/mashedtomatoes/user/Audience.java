package com.mashedtomatoes.user;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "Audiences")
public class Audience extends User {
  private String displayName;
  private boolean publicProfile;

  public Audience() {}

  public Audience(String displayName, String email, String hashedPassword) {
    super(UserType.AUDIENCE);
    super.getCredentials().setEmail(email);
    super.getCredentials().setPassword(hashedPassword);
    this.displayName = displayName;
    this.publicProfile = true;
  }

  public void setDisplayName(String displayName) {
    this.displayName = displayName;
  }

  public void setPublicProfile(boolean publicProfile) {
    this.publicProfile = publicProfile;
  }

  @Column(nullable = false, unique = true)
  public String getDisplayName() {
    return displayName;
  }

  @Column(nullable = false, columnDefinition = "TINYINT(1)")
  public boolean isPublicProfile() {
    return publicProfile;
  }
}
