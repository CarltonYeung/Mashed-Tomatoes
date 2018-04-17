package com.mashedtomatoes.rating;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.mashedtomatoes.media.Media;
import com.mashedtomatoes.user.User;
import java.time.Instant;
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
  private long created;
  private long updated;

  @PrePersist
  protected void onCreate() {
    this.created = Instant.now().getEpochSecond();
    this.updated = this.created;
  }

  @PreUpdate
  protected void onUpdate() {
    this.updated = Instant.now().getEpochSecond();
  }

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
  @Range(min = 1, max = 5)
  public int getScore() {
    return score;
  }

  public void setScore(int score) {
    this.score = score;
  }

  public void setUpdated(long updated) {
    this.updated = updated;
  }

  public String getReview() {
    return review;
  }

  public void setReview(String review) {
    this.review = review;
  }

  @ManyToOne
  @JoinColumn(name = "mediaID", nullable = false)
  public Media getMedia() {
    return media;
  }

  public void setMedia(Media media) {
    this.media = media;
  }

  @ManyToOne
  @JoinColumn(name = "authorID", nullable = false)
  public User getAuthor() {
    return author;
  }

  public void setAuthor(User author) {
    this.author = author;
  }

  @Column(nullable = false, updatable = false)
  public long getCreated() {
    return created;
  }

  public void setCreated(long created) {
    this.created = created;
  }

  @Column(nullable = false)
  public long getUpdated() {
    return updated;
  }
}
