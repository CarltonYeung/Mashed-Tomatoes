package com.mashedtomatoes.celebrity;

import javax.persistence.*;

@Entity
@Table(name = "Quotes")
public class Quote {

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
