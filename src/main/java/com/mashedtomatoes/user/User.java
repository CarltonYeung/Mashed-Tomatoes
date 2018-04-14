package com.mashedtomatoes.user;

import com.mashedtomatoes.rating.Rating;

import javax.persistence.*;
import java.time.Instant;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "Users")
public abstract class User {

    protected long id;
    protected UserCredentials credentials;
    protected UserVerification verification;
    protected UserType type;
    protected Date birthDate;
    protected Set<Rating> ratings;
    protected Set<User> following;
    protected Set<User> followers;
    protected long created;
    protected long updated;

    public User() {
    }

    public User(UserType type) {
        this.credentials = new UserCredentials(this);
        this.verification = new UserVerification(this);
        this.ratings = new HashSet<>();
        this.type = type;
    }

    @PrePersist
    protected void onCreate() {
        this.created = Instant.now().getEpochSecond();
        this.updated = this.created;
    }

    @PreUpdate
    protected void onUpdate() {
        this.updated = Instant.now().getEpochSecond();
    }

    public String toString() {
        return "User(id=" + this.getId()
                + ", type=" + this.getType()
                + ", birthDate=" + this.getBirthDate()
                + ", created=" + this.getCreated()
                + ", updated=" + this.getUpdated()
                + ", ratings=" + this.getRatings() + ")";
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    @PrimaryKeyJoinColumn
    public UserCredentials getCredentials() {
        return credentials;
    }

    public void setCredentials(UserCredentials credentials) {
        this.credentials = credentials;
    }

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    @PrimaryKeyJoinColumn
    public UserVerification getVerification() {
        return verification;
    }

    public void setVerification(UserVerification verification) {
        this.verification = verification;
    }

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 32)
    public UserType getType() {
        return type;
    }

    public void setType(UserType type) {
        this.type = type;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    @OneToMany(mappedBy = "author", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    public Set<Rating> getRatings() {
        return ratings;
    }

    public void setRatings(Set<Rating> ratings) {
        this.ratings = ratings;
    }

    @ManyToMany
    @JoinTable(name="Follows",
            joinColumns=@JoinColumn(name="followerId"),
            inverseJoinColumns=@JoinColumn(name="followingId"))
    public Set<User> getFollowing() {
        return following;
    }

    public void setFollowing(Set<User> following) {
        this.following = following;
    }

    @ManyToMany
    @JoinTable(name="Follows",
            joinColumns=@JoinColumn(name="followingId"),
            inverseJoinColumns=@JoinColumn(name="followerId"))
    public Set<User> getFollowers() {
        return followers;
    }

    public void setFollowers(Set<User> followers) {
        this.followers = followers;
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

    public void setUpdated(long updated) {
        this.updated = updated;
    }
}
