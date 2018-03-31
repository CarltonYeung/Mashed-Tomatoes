package com.mashedtomatoes.model;

import java.util.HashSet;
import java.util.Set;
import java.time.LocalDate;

import javax.persistence.*;

@Entity
public class Celebrity {

    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column
    private LocalDate birthday;

    @Column
    private String birthplace;

    @Column
    private String description;

    @Column
    private String biography;

    @Column
    private String profileImage;

    @OneToMany()
    @JoinTable(name="celebrities_character")
    protected Set<Character> characters = new HashSet<>();

    public Celebrity() {}
}
