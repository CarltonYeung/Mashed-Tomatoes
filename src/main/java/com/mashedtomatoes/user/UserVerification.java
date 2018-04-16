package com.mashedtomatoes.user;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "UserVerifications")
public class UserVerification {
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

  String generateKey() {
    expiration = Instant.now().plus(1, ChronoUnit.DAYS).getEpochSecond();
    verificationKey = UUID.randomUUID().toString().replace("-", "");
    return verificationKey;
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
