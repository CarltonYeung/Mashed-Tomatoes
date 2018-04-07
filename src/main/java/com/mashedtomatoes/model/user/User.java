package com.mashedtomatoes.model.user;

import com.mashedtomatoes.model.rating.Rating;

import javax.persistence.*;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "Users")
public abstract class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long ID;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    @PrimaryKeyJoinColumn
    private UserCredentials credentials;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    @PrimaryKeyJoinColumn
    private UserVerification verification;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 32)
    private UserType type;

    private long birthDate;

    @Column(nullable = false, updatable = false)
    private long created;

    @Column(nullable = false)
    private long updated;

    @Column(nullable = false, columnDefinition = "TINYINT(1)")
    private boolean banned;

    @OneToMany(mappedBy = "author", cascade = CascadeType.ALL)
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

    public long getID() {
        return ID;
    }

    public void setID(long ID) {
        this.ID = ID;
    }

    public UserCredentials getCredentials() {
        return credentials;
    }

    public void setCredentials(UserCredentials credentials) {
        this.credentials = credentials;
    }

    public UserVerification getVerification() {
        return verification;
    }

    public void setVerification(UserVerification verification) {
        this.verification = verification;
    }

    public UserType getType() {
        return type;
    }

    public void setType(UserType type) {
        this.type = type;
    }

    public long getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(long birthDate) {
        this.birthDate = birthDate;
    }

    public long getCreated() {
        return created;
    }

    public void setCreated(long created) {
        this.created = created;
    }

    public long getUpdated() {
        return updated;
    }

    public void setUpdated(long updated) {
        this.updated = updated;
    }

    public boolean isBanned() {
        return banned;
    }

    public void setBanned(boolean banned) {
        this.banned = banned;
    }

    public Set<Rating> getRatings() {
        return ratings;
    }

    public void setRatings(Set<Rating> ratings) {
        this.ratings = ratings;
    }

    public String toString() {
        return "User(ID=" + this.getID() + ", type=" + this.getType() + ", birthDate=" + this.getBirthDate() + ", created=" + this.getCreated() + ", updated=" + this.getUpdated() + ", banned=" + this.isBanned() + ", ratings=" + this.getRatings() + ")";
    }
}