package com.mashedtomatoes.rating;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.mashedtomatoes.media.Media;
import com.mashedtomatoes.user.User;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import org.hibernate.validator.constraints.Range;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "Ratings")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public abstract class Rating {
  private long id;
  private int score;
  private String review;
  private Media media;
  private User author;
  private Date created;
  private Date updated;

  public void setId(long id) {
    this.id = id;
  }

  public void setScore(int score) {
    this.score = score;
  }

  public void setReview(String review) {
    this.review = review;
  }

  public void setMedia(Media media) {
    this.media = media;
  }

  public void setAuthor(User author) {
    this.author = author;
  }

  public void setCreated(Date created) {
    this.created = created;
  }

  public void setUpdated(Date updated) {
    this.updated = updated;
  }

  @PrePersist
  private void onCreate() {
    this.created = new Date();
    this.updated = this.created;
  }

  @PreUpdate
  private void onUpdate() {
    this.updated = new Date();
  }

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @JsonProperty("id")
  public long getId() {
    return id;
  }

  @Column(nullable = false)
  @Range(min = 1, max = 5)
  public int getScore() {
    return score;
  }

  public String getReview() {
    return review;
  }

  @ManyToOne
  @JoinColumn(name = "mediaID", nullable = false)
  public Media getMedia() {
    return media;
  }

  @ManyToOne
  @JoinColumn(name = "authorID", nullable = false)
  public User getAuthor() {
    return author;
  }

  @Column(nullable = false, updatable = false)
  public Date getCreated() {
    return created;
  }

  @Column(nullable = false)
  public Date getUpdated() {
    return updated;
  }
}
