package com.mashedtomatoes.user;

import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;
import javax.persistence.PrePersist;
import javax.persistence.Table;

@Entity
@Table(name = "CriticApplications")
public class CriticApplication {
  private long id;
  private Audience applicant;
  private String firstName;
  private String lastName;
  private String body;
  private Date applied;

  public CriticApplication() {}

  public void setId(long id) {
    this.id = id;
  }

  public void setApplicant(Audience applicant) {
    this.applicant = applicant;
  }

  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  public void setBody(String body) {
    this.body = body;
  }

  public void setApplied(Date applied) {
    this.applied = applied;
  }

  @PrePersist
  private void onCreate() {
    this.applied = new Date();
  }

  @Id
  public long getId() {
    return id;
  }

  @MapsId
  @OneToOne
  @JoinColumn(name = "applicantId")
  public Audience getApplicant() {
    return applicant;
  }

  public String getFirstName() {
    return firstName;
  }

  public String getLastName() {
    return lastName;
  }

  public String getBody() {
    return body;
  }

  public Date getApplied() {
    return applied;
  }
}
