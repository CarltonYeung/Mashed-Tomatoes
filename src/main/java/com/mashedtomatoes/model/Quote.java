package com.mashedtomatoes.model;

import javax.persistence.*;

@Entity
public class Quote {

    @Id
    @GeneratedValue
    private Long id;

    @Column
    private String text;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="character_id", nullable = false)
    private Character character;
}
