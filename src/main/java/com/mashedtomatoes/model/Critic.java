package com.mashedtomatoes.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@NoArgsConstructor
@Table(name = "Critics")
@Getter
@Setter
public class Critic extends User {

    private String firstName;

    private String lastName;

    private String publication;
}