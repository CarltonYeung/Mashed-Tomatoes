package com.mashedtomatoes.model;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "MT_Character")
public class Character {

    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false)
    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="media_id", nullable = false)
    private Media media;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "celebrity_id", nullable = false)
    private Celebrity celebrity;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "character")
    protected Set<Quote> quotes = new HashSet<>();
}
