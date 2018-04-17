package com.mashedtomatoes.celebrity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import org.hibernate.search.annotations.Analyzer;
import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.Indexed;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "Celebrities")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
@Indexed
public class Celebrity {
  private long id;
  private String name;
  private Date birthday;
  private String birthplace;
  private String biography;
  private String profilePath;

  public Celebrity() {}

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @JsonProperty("id")
  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  @Column(nullable = false)
  @Field
  @Analyzer(definition = "searchAnalyzer")
  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Date getBirthday() {
    return birthday;
  }

  public void setBirthday(Date birthday) {
    this.birthday = birthday;
  }

  public String getBirthplace() {
    return birthplace;
  }

  public void setBirthplace(String birthplace) {
    this.birthplace = birthplace;
  }

  @Column(columnDefinition = "TEXT")
  public String getBiography() {
    return biography;
  }

  public void setBiography(String biography) {
    this.biography = biography;
  }

  public String getProfilePath() {
    return profilePath;
  }

  public void setProfilePath(String profilePath) {
    this.profilePath = profilePath;
  }
}
