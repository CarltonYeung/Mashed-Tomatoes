package com.mashedtomatoes.user;

import com.mashedtomatoes.media.Media;
import com.mashedtomatoes.rating.Rating;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "Users")
public abstract class User {
  private long id;
  private UserCredentials credentials;
  private UserVerification verification;
  private UserType type;
  private Date birthDate;
  private Set<Rating> ratings;
  private Set<User> following;
  private Set<User> followers;
  private Set<Media> wantToSee;
  private Set<Media> notInterested;
  private long profileViews;
  private Date created;
  private Date updated;
  private boolean publicProfile;
  private String displayName;
  private UserReport report;

  public User() {}

  public User(UserType type) {
    this.credentials = new UserCredentials(this);
    this.verification = new UserVerification(this);
    this.ratings = new HashSet<>();
    this.following = new HashSet<>();
    this.followers = new HashSet<>();
    this.wantToSee = new HashSet<>();
    this.notInterested = new HashSet<>();
    this.type = type;
    this.publicProfile = true;
  }

  public void setId(long id) {
    this.id = id;
  }

  public void setCredentials(UserCredentials credentials) {
    this.credentials = credentials;
  }

  public void setVerification(UserVerification verification) {
    this.verification = verification;
  }

  public void setType(UserType type) {
    this.type = type;
  }

  public void setBirthDate(Date birthDate) {
    this.birthDate = birthDate;
  }

  public void setRatings(Set<Rating> ratings) {
    this.ratings = ratings;
  }

  public void setFollowing(Set<User> following) {
    this.following = following;
  }

  public void setFollowers(Set<User> followers) {
    this.followers = followers;
  }

  public void setWantToSee(Set<Media> wantToSee) {
    this.wantToSee = wantToSee;
  }

  public void setNotInterested(Set<Media> notInterested) {
    this.notInterested = notInterested;
  }

  public void setProfileViews(long profileViews) {
    this.profileViews = profileViews;
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
    this.updated = created;
  }

  @PreUpdate
  private void onUpdate() {
    this.updated = new Date();
  }

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  public long getId() {
    return id;
  }

  @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
  @PrimaryKeyJoinColumn
  public UserCredentials getCredentials() {
    return credentials;
  }

  @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
  @PrimaryKeyJoinColumn
  public UserVerification getVerification() {
    return verification;
  }

  @Enumerated(EnumType.STRING)
  @Column(nullable = false, length = 32)
  public UserType getType() {
    return type;
  }

  public Date getBirthDate() {
    return birthDate;
  }

  @OneToMany(mappedBy = "author", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
  public Set<Rating> getRatings() {
    return ratings;
  }

  @ManyToMany(fetch = FetchType.EAGER)
  @JoinTable(
    name = "Follows",
    joinColumns = @JoinColumn(name = "followerId"),
    inverseJoinColumns = @JoinColumn(name = "followingId")
  )
  public Set<User> getFollowing() {
    return following;
  }

  @ManyToMany(mappedBy = "following", fetch = FetchType.EAGER)
  public Set<User> getFollowers() {
    return followers;
  }

  @ManyToMany(fetch = FetchType.EAGER)
  @JoinTable(
    name = "WantToSee",
    joinColumns = {@JoinColumn(name = "userID")},
    inverseJoinColumns = {@JoinColumn(name = "mediaID")}
  )
  public Set<Media> getWantToSee() {
    return wantToSee;
  }

  @ManyToMany(fetch = FetchType.EAGER)
  @JoinTable(
    name = "NotInterested",
    joinColumns = {@JoinColumn(name = "userID")},
    inverseJoinColumns = {@JoinColumn(name = "mediaID")}
  )
  public Set<Media> getNotInterested() {
    return notInterested;
  }

  public long getProfileViews() {
    return profileViews;
  }

  @Column(nullable = false, updatable = false)
  public Date getCreated() {
    return created;
  }

  @Column(nullable = false)
  public Date getUpdated() {
    return updated;
  }

  @Column(nullable = false, columnDefinition = "TINYINT(1)")
  public boolean isPublicProfile() {
    return publicProfile;
  }

  public void setPublicProfile(boolean publicProfile) {
    this.publicProfile = publicProfile;
  }

  public void setDisplayName(String displayName) {
    this.displayName = displayName;
  }

  @Column(nullable = false, unique = true)
  public String getDisplayName() {
    return displayName;
  }

  @OneToOne(mappedBy = "user", cascade = CascadeType.REMOVE)
  @PrimaryKeyJoinColumn
  public UserReport getReport() {
    return report;
  }

  public void setReport(UserReport report) {
    this.report = report;
  }
}
