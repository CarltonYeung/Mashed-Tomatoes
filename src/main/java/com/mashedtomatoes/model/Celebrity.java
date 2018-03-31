package com.mashedtomatoes.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@Table(name = "Celebrities")
public class Celebrity {
    @Id
    @GeneratedValue
    @Getter @Setter
    private long ID;

    @Column(nullable = false)
    @Getter @Setter
    private String name;

    @Column
    @Getter @Setter
    private long birthday;

    @Column
    @Getter @Setter
    private String birthplace;

    @Column
    @Getter @Setter
    private String biography;

    @Column
    @Getter @Setter
    private String profileImage;

    @ManyToMany
    @JoinTable(name = "MediaCelebrities", joinColumns = {@JoinColumn(name = "celebrityID")}, inverseJoinColumns = {@JoinColumn(name = "mediaID")})
    @Getter @Setter
    private Set<Media> media = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "playedBy")
    @Getter @Setter
    private Set<Character> characters = new HashSet<>();
}
