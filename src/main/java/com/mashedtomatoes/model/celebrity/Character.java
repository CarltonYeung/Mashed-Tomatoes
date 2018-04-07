package com.mashedtomatoes.model.celebrity;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "Characters")
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

    public Character() {
    }

    public long getID() {
        return this.ID;
    }

    public String getName() {
        return this.name;
    }

    public Celebrity getPlayedBy() {
        return this.playedBy;
    }

    public Set<Quote> getQuotes() {
        return this.quotes;
    }

    public void setID(long ID) {
        this.ID = ID;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPlayedBy(Celebrity playedBy) {
        this.playedBy = playedBy;
    }

    public void setQuotes(Set<Quote> quotes) {
        this.quotes = quotes;
    }
}
