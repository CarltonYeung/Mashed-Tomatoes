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

    private long ID;
    private UserCredentials credentials;
    private UserVerification verification;
    private UserType type;
    private long birthDate;
    private long created;
    private long updated;
    private boolean banned;
    private Set<Rating> ratings = new HashSet<>();

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
        return "User(ID=" + this.getID() + ", type=" + this.getType() + ", birthDate=" + this.getBirthDate() + ", created=" + this.getCreated() + ", updated=" + this.getUpdated() + ", banned=" + this.isBanned() + ", ratings=" + this.getRatings() + ")";
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public long getID() {
        return ID;
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

    @Column(nullable = false, columnDefinition = "TINYINT(1)")
    public boolean isBanned() {
        return banned;
    }

    @OneToMany(mappedBy = "author", cascade = CascadeType.ALL)
    public Set<Rating> getRatings() {
        return ratings;
    }

    public void setID(long ID) {
        this.ID = ID;
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

    public void setBanned(boolean banned) {
        this.banned = banned;
    }

    public void setRatings(Set<Rating> ratings) {
        this.ratings = ratings;
    }
}