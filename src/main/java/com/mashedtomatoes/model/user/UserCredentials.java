package com.mashedtomatoes.model.user;

import javax.persistence.*;

@Entity
@Table(name = "UserCredentials")
public class UserCredentials {

    @Id
    private long ID;

    @MapsId
    @OneToOne
    @JoinColumn(name = "userID")
    private User user;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false, length = 60)
    private String password;

    public UserCredentials() {

    }

    public UserCredentials(String email, String password) {
        this.email = email;
        this.password = password;
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
}
