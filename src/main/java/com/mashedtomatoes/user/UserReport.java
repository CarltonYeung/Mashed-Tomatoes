package com.mashedtomatoes.user;

import javax.persistence.*;

@Entity
@Table(name = "UserReports")
public class UserReport {
  private long id;
  private User user;
  private String reason;

  public UserReport() {
  }

  @Id
  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  @MapsId
  @OneToOne
  @JoinColumn(name = "userId")
  public User getUser() {
    return user;
  }

  public void setUser(User user) {
    this.user = user;
  }

  public String getReason() {
    return reason;
  }

  public void setReason(String reason) {
    this.reason = reason;
  }
}
