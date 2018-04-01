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

    /*
     * In the "Quotes" table, there will be a "characterID" column.
     * Many Quote can reference the same Character and have the same value for the "characterID" column.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="characterID", nullable = false)
    private Character saidBy;
}
