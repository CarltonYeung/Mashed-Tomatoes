package com.mashedtomatoes.celebrity;

import javax.persistence.*;

@Entity
@Table(name = "Quotes")
public class Quote {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long ID;

    private String text;

    @ManyToOne
    @JoinColumn(name = "characterID", nullable = false)
    private Character saidBy;

    public long getID() {
        return this.ID;
    }

    public void setID(long ID) {
        this.ID = ID;
    }

    public String getText() {
        return this.text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Character getSaidBy() {
        return this.saidBy;
    }

    public void setSaidBy(Character saidBy) {
        this.saidBy = saidBy;
    }
}
