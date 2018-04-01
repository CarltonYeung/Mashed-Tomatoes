package com.mashedtomatoes.model;

import javax.persistence.*;
import java.time.Instant;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "Users")
public abstract class User {

    public enum UserType {
        Audience,
        Critic,
        Administrator
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long ID;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 32)
    private UserType type;

    private long birthDate;

    @Column(nullable = false, updatable = false)
    private long created;

    @Column(nullable = false)
    private long updated;

    @Column(nullable = false)
    private boolean verified = false;

    @Column(nullable = false)
    private String verificationKey;

    @PrePersist
    protected void onCreate() {
        this.created = Instant.now().getEpochSecond();
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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

    public long getUpdated() {
        return updated;
    }

    public void setUpdated(long updated) {
        this.updated = updated;
    }

    public boolean isVerified() {
        return verified;
    }

    public void setVerified(boolean verified) {
        this.verified = verified;
    }

    public String getVerificationKey() {
        return verificationKey;
    }

    public void setVerificationKey(String verificationKey) {
        this.verificationKey = verificationKey;
    }
}