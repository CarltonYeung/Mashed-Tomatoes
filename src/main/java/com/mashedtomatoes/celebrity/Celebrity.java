package com.mashedtomatoes.celebrity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "Celebrities")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class Celebrity {
  private long id;
  private String name;
  private Date birthday;
  private String birthplace;
  private String biography;
  private String profilePath;

  public Celebrity() {}

  public void setId(long id) {
    this.id = id;
  }

  public void setName(String name) {
    this.name = name;
  }

  public void setBirthday(Date birthday) {
    this.birthday = birthday;
  }

  public void setBirthplace(String birthplace) {
    this.birthplace = birthplace;
  }

  public void setBiography(String biography) {
    this.biography = biography;
  }

  public void setProfilePath(String profilePath) {
    this.profilePath = profilePath;
  }

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @JsonProperty("id")
  public long getId() {
    return id;
  }

  @Column(nullable = false)
  public String getName() {
    return name;
  }

  public Date getBirthday() {
    return birthday;
  }

  public String getBirthplace() {
    return birthplace;
  }

  @Column(columnDefinition = "TEXT")
  public String getBiography() {
    return biography;
  }

  public String getProfilePath() {
    return profilePath;
  }
}
