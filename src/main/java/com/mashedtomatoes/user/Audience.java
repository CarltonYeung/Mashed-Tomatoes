package com.mashedtomatoes.user;

import javax.persistence.*;

@Entity
@Table(name = "Audiences")
public class Audience extends User {
  private CriticApplication criticApplication;

  public Audience() {}

  public Audience(String displayName, String email, String hashedPassword) {
    super(UserType.AUDIENCE);
    super.getCredentials().setEmail(email);
    super.getCredentials().setPassword(hashedPassword);
    super.setDisplayName(displayName);
  }

  @OneToOne(mappedBy = "applicant", cascade = CascadeType.ALL)
  @PrimaryKeyJoinColumn
  public CriticApplication getCriticApplication() {
    return criticApplication;
  }

  public void setCriticApplication(CriticApplication criticApplication) {
    this.criticApplication = criticApplication;
  }
}
