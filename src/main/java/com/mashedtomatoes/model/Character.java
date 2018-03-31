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
public class Character {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter @Setter
    private long ID;

    @Column(nullable = false)
    @Getter @Setter
    private String name;

    /*
     * In the "Characters" table, there will be a "celebrityID" column.
     * Many Character can reference the same Celebrity and have the same value for the "celebrityID" column.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "celebrityID", nullable = false)
    @Getter @Setter
    private Celebrity playedBy;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "saidBy")
    @Getter @Setter
    protected Set<Quote> quotes = new HashSet<>();
}
