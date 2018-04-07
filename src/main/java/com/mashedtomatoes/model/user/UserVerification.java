package com.mashedtomatoes.model.user;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "UserVerifications")
public class UserVerification {

    @Id
    private long ID;

    @MapsId
    @OneToOne
    @JoinColumn(name = "userID")
    private User user;

    @Column(nullable = false, columnDefinition = "TINYINT(1)")
    private boolean verified;

    @Column(nullable = false)
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

    public long getID() {
        return ID;
    }

    public void setID(long ID) {
        this.ID = ID;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
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
