package com.mashedtomatoes.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "Quotes")
public class Quote {
    @Id
    @GeneratedValue
    @Getter @Setter
    private long ID;

    @Column
    @Getter @Setter
    private String text;

    /*
     * In the "Quotes" table, there will be a "characterID" column.
     * Many Quote can reference the same Character and have the same value for the "characterID" column.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="characterID", nullable = false)
    @Getter @Setter
    private Character saidBy;
}
