package com.mashedtomatoes.user;

import javax.persistence.*;

@Entity
@Table(name = "UserCredentials")
public class UserCredentials {
  private long ID;
  private User user;
  private String email;
  private String password;

  UserCredentials() {}

  UserCredentials(User user) {
    this.user = user;
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

  @Column(nullable = false, unique = true)
  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  @Column(nullable = false, length = 60)
  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }
}
