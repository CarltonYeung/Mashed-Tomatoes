package com.mashedtomatoes.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "Quotes")
@Getter @Setter
public class Quote {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long ID;

    private String text;

    @ManyToOne
    @JoinColumn(name="characterID", nullable = false)
    private Character saidBy;
}
