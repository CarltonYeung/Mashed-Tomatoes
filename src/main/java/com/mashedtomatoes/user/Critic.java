package com.mashedtomatoes.user;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "Critics")
public class Critic extends User {
  private String firstName;
  private String lastName;
  private boolean topCritic;

  public Critic() {}

  public Critic(String firstName, String lastName) {
    super(UserType.CRITIC);
    this.firstName = firstName;
    this.lastName = lastName;
    this.topCritic = false;
  }

  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  public void setTopCritic(boolean topCritic) {
    this.topCritic = topCritic;
  }

  @Column(nullable = false)
  public String getFirstName() {
    return firstName;
  }

  @Column(nullable = false)
  public String getLastName() {
    return lastName;
  }

  @Column(nullable = false, columnDefinition = "TINYINT(1)")
  public boolean isTopCritic() {
    return topCritic;
  }
}
