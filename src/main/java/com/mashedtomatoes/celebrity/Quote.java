package com.mashedtomatoes.celebrity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import javax.persistence.*;

@Entity
@Table(name = "Quotes")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class Quote {

    @JsonProperty("id")
    private long ID;
    private String text;
    private Character saidBy;

    public Quote() {
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public long getID() {
        return ID;
    }

    public String getText() {
        return text;
    }

    @ManyToOne
    @JoinColumn(name = "characterID", nullable = false)
    public Character getSaidBy() {
        return saidBy;
    }

    public void setID(long ID) {
        this.ID = ID;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setSaidBy(Character saidBy) {
        this.saidBy = saidBy;
    }
}
