package com.mashedtomatoes.model;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.GeneratedValue;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public class Celebrity {
    @Id
    @GeneratedValue
    @Column
    private Long Id;

    @Column
    private String name;


    public Celebrity() {
    }

    public Celebrity(Long Id, String name) {
        this.Id = Id;
        this.name = name;
    }

    public Long getId() {
        return this.Id;
    }

    public String getName() {
        return this.name;
    }
}