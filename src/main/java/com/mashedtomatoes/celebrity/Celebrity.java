package com.mashedtomatoes.celebrity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import java.util.Date;
import java.util.Set;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Table;

@Entity
@Table(name = "Celebrities")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class Celebrity {
  private long id;
  private String name;
  private Date birthday;
  private Date deathday;
  private String birthplace;
  private String biography;
  private String profilePath;
  private Set<String> photos;

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

  public void setDeathday(Date deathday) {
    this.deathday = deathday;
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

  public void setPhotos(Set<String> photos) {
    this.photos = photos;
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

  public Date getDeathday() {
    return deathday;
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

  @ElementCollection(targetClass = String.class, fetch = FetchType.EAGER)
  @CollectionTable(
    name = "CelebrityPhotos",
    joinColumns = {@JoinColumn(name = "celebrityId")}
  )
  @Column(name = "photo", nullable = false)
  public Set<String> getPhotos() {
    return photos;
  }

  @Override
  public String toString() {
    return name;
  }
}
