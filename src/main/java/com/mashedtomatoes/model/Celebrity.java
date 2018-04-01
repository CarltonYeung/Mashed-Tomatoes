package com.mashedtomatoes.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@NoArgsConstructor
@Table(name = "Celebrities")
@Getter @Setter
public class Celebrity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long ID;

    @Column(nullable = false)
    private String name;

    private long birthday;

    private String birthplace;

    private String biography;

    private String profileImage;

    @ManyToMany
    @JoinTable(name = "MediaCelebrities", joinColumns = {@JoinColumn(name = "celebrityID")}, inverseJoinColumns = {@JoinColumn(name = "mediaID")})
    private Set<Media> media = new HashSet<>();

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "playedBy", cascade = CascadeType.ALL)
    private Set<Character> characters = new HashSet<>();

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "directedBy", cascade = CascadeType.ALL)
    protected Set<Media> directorOf = new HashSet<>();
}
