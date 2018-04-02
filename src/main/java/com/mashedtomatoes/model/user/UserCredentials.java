package com.mashedtomatoes.model.user;

import javax.persistence.*;

@Entity
@Table(name = "UserCredentials")
public class UserCredentials {

    @Id
    private long ID;

    @MapsId
    @OneToOne(mappedBy = "credentials")
    @JoinColumn(name = "userID")
    private User user;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private char[] password;
}
