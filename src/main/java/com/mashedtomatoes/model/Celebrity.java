package com.mashedtomatoes.model;

import org.springframework.data.repository.cdi.Eager;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Celebrity {
    @Id
    @GeneratedValue
    @Column
    private Long Id;

    public Celebrity() {}
}
