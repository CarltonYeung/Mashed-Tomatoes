package com.mashedtomatoes.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@NoArgsConstructor
@Table(name = "Characters")
@Getter @Setter
public class Character {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long ID;

    @Column(nullable = false)
    private String name;

    @ManyToOne
    @JoinColumn(name = "celebrityID", nullable = false)
    private Celebrity playedBy;

    @OneToMany(mappedBy = "saidBy")
    protected Set<Quote> quotes = new HashSet<>();
}
