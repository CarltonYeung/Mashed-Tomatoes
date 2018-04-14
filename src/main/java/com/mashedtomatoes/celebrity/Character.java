package com.mashedtomatoes.celebrity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.mashedtomatoes.media.Media;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "Characters")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class Character {

    private long ID;
    private String name;
    private Celebrity playedBy;
    private int castOrder;
    protected Set<Quote> quotes;
    private Media forMedia;

    public Character() {
        this.quotes = new HashSet<>();
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonProperty("id")
    public long getID() {
        return ID;
    }

    @Column(nullable = false)
    public String getName() {
        return name;
    }

    @ManyToOne
    @JoinColumn(name = "celebrityID", nullable = false)
    public Celebrity getPlayedBy() {
        return playedBy;
    }

    @Column(nullable = false)
    public int getCastOrder() {
        return castOrder;
    }

    @OneToMany(mappedBy = "saidBy")
    public Set<Quote> getQuotes() {
        return quotes;
    }

    @ManyToOne
    @JoinColumn(name = "mediaID")
    public Media getForMedia() {
        return forMedia;
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

    public void setCastOrder(int castOrder) {
        this.castOrder = castOrder;
    }

    public void setQuotes(Set<Quote> quotes) {
        this.quotes = quotes;
    }

    public void setForMedia(Media forMedia) {
        this.forMedia = forMedia;
    }
}
