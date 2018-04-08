package com.mashedtomatoes.celebrity;

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

    @Column(nullable = false)
    private int castOrder;

    @OneToMany(mappedBy = "saidBy")
    protected Set<Quote> quotes = new HashSet<>();

    public Character() {
    }

    public long getID() {
        return ID;
    }

    public void setID(long ID) {
        this.ID = ID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Celebrity getPlayedBy() {
        return playedBy;
    }

    public void setPlayedBy(Celebrity playedBy) {
        this.playedBy = playedBy;
    }

    public int getCastOrder() {
        return castOrder;
    }

    public void setCastOrder(int castOrder) {
        this.castOrder = castOrder;
    }

    public Set<Quote> getQuotes() {
        return quotes;
    }

    public void setQuotes(Set<Quote> quotes) {
        this.quotes = quotes;
    }
}
