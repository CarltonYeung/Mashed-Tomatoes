package com.mashedtomatoes.user;

import javax.persistence.*;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.UUID;

@Entity
@Table(name = "UserVerifications")
public class UserVerification {
  private static final long timeToLive = 1; // Cannot be imported from properties file
  private long ID;
  private User user;
  private boolean verified;
  private String verificationKey;
  private long expiration;

  UserVerification() {}

  UserVerification(User user) {
    this.user = user;
    this.verified = false;
    generateKey();
  }

  public boolean verify(String verificationKey) {
    if (this.verified) {
      return false;
    }
    if (Instant.now().isAfter(Instant.ofEpochSecond(this.expiration))) {
      return false;
    }
    if (this.verificationKey.equals(verificationKey)) {
      this.verified = true;
    }
    return this.verified;
  }

  void generateKey() {
    expiration = Instant.now().plus(timeToLive, ChronoUnit.DAYS).getEpochSecond();
    verificationKey = UUID.randomUUID().toString().replace("-", "");
  }

  @Id
  public long getID() {
    return ID;
  }

  public void setID(long ID) {
    this.ID = ID;
  }

  @MapsId
  @OneToOne
  @JoinColumn(name = "userID")
  public User getUser() {
    return user;
  }

  public void setUser(User user) {
    this.user = user;
  }

  @Column(nullable = false, columnDefinition = "TINYINT(1)")
  public boolean isVerified() {
    return verified;
  }

  public void setVerified(boolean verified) {
    this.verified = verified;
  }

  @Column(nullable = false)
  public String getVerificationKey() {
    return verificationKey;
  }

  public void setVerificationKey(String verificationKey) {
    this.verificationKey = verificationKey;
  }

  @Column(nullable = false)
  public long getExpiration() {
    return expiration;
  }

  public void setExpiration(long expiration) {
    this.expiration = expiration;
  }
}
