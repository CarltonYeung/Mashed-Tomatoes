package com.mashedtomatoes.celebrity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.mashedtomatoes.media.Media;

import javax.persistence.*;

@Entity
@Table(name = "Characters")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class Character {
  private long id;
  private String name;
  private Celebrity celebrity;
  private int castOrder;
  private Media media;

  public Character() {}

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
  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  @ManyToOne
  @JoinColumn(name = "celebrityID", nullable = false)
  public Celebrity getCelebrity() {
    return celebrity;
  }

  public void setCelebrity(Celebrity celebrity) {
    this.celebrity = celebrity;
  }

  @Column(nullable = false)
  public int getCastOrder() {
    return castOrder;
  }

  public void setCastOrder(int castOrder) {
    this.castOrder = castOrder;
  }

  @ManyToOne
  @JoinColumn(name = "mediaID")
  public Media getMedia() {
    return media;
  }

  public void setMedia(Media media) {
    this.media = media;
  }
}
