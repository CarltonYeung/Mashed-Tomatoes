package com.mashedtomatoes.user;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "UserVerifications")
public class UserVerification {

    private long ID;
    private User user;
    private boolean verified;
    private String verificationKey;

    public UserVerification() {
        this.verified = false;
        this.verificationKey = UUID.randomUUID().toString().replace("-", "");
    }

    public boolean verify(String verificationKey) {
        if (this.verified) {
            return false;
        }

        if (this.verificationKey.equals(verificationKey)) {
            this.verified = true;
        }

        return this.verified;
    }

    @Id
    public long getID() {
        return ID;
    }

    @MapsId
    @OneToOne
    @JoinColumn(name = "userID")
    public User getUser() {
        return user;
    }

    @Column(nullable = false, columnDefinition = "TINYINT(1)")
    public boolean isVerified() {
        return verified;
    }

    @Column(nullable = false)
    public String getVerificationKey() {
        return verificationKey;
    }

    public void setID(long ID) {
        this.ID = ID;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setVerified(boolean verified) {
        this.verified = verified;
    }

    public void setVerificationKey(String verificationKey) {
        this.verificationKey = verificationKey;
    }
}
