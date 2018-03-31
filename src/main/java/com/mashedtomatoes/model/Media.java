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
    @GeneratedValue
    @Getter @Setter
    protected long ID;

    @Column(nullable = false)
    @Getter @Setter
    protected String description;

    @ManyToMany
    @JoinTable(name = "MediaCelebrities", joinColumns = {@JoinColumn(name = "mediaID")}, inverseJoinColumns = {@JoinColumn(name = "celebrityID")})
    @Getter @Setter
    private Set<Celebrity> celebrities = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "forMedia")
    @Getter @Setter
    protected Set<Rating> ratings = new HashSet<>();
}

// ref: https://stackoverflow.com/questions/11938253/jpa-joincolumn-vs-mappedby/11938290
