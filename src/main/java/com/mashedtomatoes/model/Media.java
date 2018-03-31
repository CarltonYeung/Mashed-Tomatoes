package com.mashedtomatoes.model;

import lombok.Getter;
import lombok.Setter;

import java.util.Set;
import java.util.HashSet;

import javax.persistence.*;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "Media")
public abstract class Media {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter @Setter
    protected long ID;

    @Column(nullable = false)
    @Getter @Setter
    private String title;

    @Column(nullable = false)
    @Getter @Setter
    private String slug;

    @Column
    @Getter @Setter
    protected String description;

    @Column
    @Getter @Setter
    protected long releaseDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "directorID")
    @Getter @Setter
    protected Celebrity directedBy;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(name = "MediaCelebrities", joinColumns = {@JoinColumn(name = "mediaID")}, inverseJoinColumns = {@JoinColumn(name = "celebrityID")})
    @Getter @Setter
    protected Set<Celebrity> celebrities = new HashSet<>();

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "forMedia", cascade = CascadeType.ALL)
    @Getter @Setter
    protected Set<Rating> ratings = new HashSet<>();
}

// ref: https://stackoverflow.com/questions/11938253/jpa-joincolumn-vs-mappedby/11938290
