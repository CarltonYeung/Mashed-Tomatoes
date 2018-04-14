package com.mashedtomatoes.user;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "UserCredentials")
public class UserCredentials {

    private long ID;
    private User user;
    private String email;
    private String password;
    private String forgetPasswordKey;

    UserCredentials() {
    }

    UserCredentials(User user) {
        this.user = user;
    }

    public String generateForgetPasswordKey() {
        this.forgetPasswordKey = UUID.randomUUID().toString().replace("-", "");
        return this.forgetPasswordKey;
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

    @Column(nullable = false, unique = true)
    public String getEmail() {
        return email;
    }

    @Column(nullable = false, length = 60)
    public String getPassword() {
        return password;
    }

    public void setID(long ID) {
        this.ID = ID;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getForgetPasswordKey() {
        return forgetPasswordKey;
    }

    public void setForgetPasswordKey(String forgetPasswordKey) {
        this.forgetPasswordKey = forgetPasswordKey;
    }
}
