package com.mashedtomatoes.model;

import javax.persistence.Column;
import javax.persistence.Entity;

@Entity
public class Critic extends User {

    @Column
    private String publication;

    public Critic() {}
}