package com.mashedtomatoes.celebrity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.mashedtomatoes.media.Media;

import javax.persistence.*;

@Entity
@Table(name = "Characters")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class Character {

    private long id;
    private String name;
    private Celebrity celebrity;
    private int castOrder;
    private Media forMedia;

    public Character() {
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonProperty("id")
    public long getId() {
        return id;
    }

    @Column(nullable = false)
    public String getName() {
        return name;
    }

    @ManyToOne
    @JoinColumn(name = "celebrityID", nullable = false)
    public Celebrity getCelebrity() {
        return celebrity;
    }

    @Column(nullable = false)
    public int getCastOrder() {
        return castOrder;
    }

    @ManyToOne
    @JoinColumn(name = "mediaID")
    public Media getForMedia() {
        return forMedia;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCelebrity(Celebrity celebrity) {
        this.celebrity = celebrity;
    }

    public void setCastOrder(int castOrder) {
        this.castOrder = castOrder;
    }

    public void setForMedia(Media forMedia) {
        this.forMedia = forMedia;
    }
}
