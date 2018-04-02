package com.mashedtomatoes.model.user;

import javax.persistence.*;

@Entity
@Table(name = "UserVerifications")
public class UserVerification {

    @Id
    private long ID;

    @MapsId
    @OneToOne(mappedBy = "verification")
    @JoinColumn(name = "userID")
    private User user;

    @Column(nullable = false)
    private boolean verified = false;

    @Column(nullable = false)
    private String verificationKey;
}
