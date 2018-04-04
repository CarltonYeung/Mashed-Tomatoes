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

    @Column(nullable = false)
    private char[] password;

    public UserCredentials(String email, char[] password) {
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

    public char[] getPassword() {
        return password;
    }

    public void setPassword(char[] password) {
        this.password = password;
    }
}
