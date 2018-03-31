package com.mashedtomatoes.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

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
    @GeneratedValue
    @Getter @Setter
    private long ID;

    @Column(nullable = false)
    @Getter @Setter
    private String email;

    @Column(nullable = false)
    @Getter @Setter
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 32)
    @Getter @Setter
    private UserType type;

    @Column(nullable = false)
    @Getter @Setter
    private long birthDate;

    @Column(nullable = false)
    @Getter @Setter
    private long created;

    @Column(nullable = false)
    @Getter @Setter
    private boolean verified;

    @Column(nullable = false)
    @Getter @Setter
    private String verificationKey;
}