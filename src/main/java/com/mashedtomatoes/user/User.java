package com.mashedtomatoes.user;

import com.mashedtomatoes.rating.Rating;

import javax.persistence.*;
import java.time.Instant;
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
    protected long birthDate;
    protected long created;
    protected long updated;
    protected Set<Rating> ratings;
    private Set<User> following;
    private Set<User> followers;

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

    public long getBirthDate() {
        return birthDate;
    }

    @Column(nullable = false, updatable = false)
    public long getCreated() {
        return created;
    }

    @Column(nullable = false)
    public long getUpdated() {
        return updated;
    }

    @OneToMany(mappedBy = "author", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    public Set<Rating> getRatings() {
        return ratings;
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

    public void setBirthDate(long birthDate) {
        this.birthDate = birthDate;
    }

    public void setCreated(long created) {
        this.created = created;
    }

    public void setUpdated(long updated) {
        this.updated = updated;
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


    @ManyToMany
    @JoinTable(name="Follows",
            joinColumns=@JoinColumn(name="followingId"),
            inverseJoinColumns=@JoinColumn(name="followerId"))
    public Set<User> getFollowers() {
        return followers;
    }

    public void setFollowing(Set<User> following) {
        this.following = following;
    }

    public void setFollowers(Set<User> followers) {
        this.followers = followers;
    }
}