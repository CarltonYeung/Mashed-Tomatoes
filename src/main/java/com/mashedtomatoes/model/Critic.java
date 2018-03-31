package com.mashedtomatoes.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@NoArgsConstructor
@Table(name = "Critics")
public class Critic extends User {
    @Column
    @Getter @Setter
    private String firstName;

    @Column
    @Getter @Setter
    private String lastName;

    @Column
    @Getter @Setter
    private String publication;
}