package com.mashedtomatoes.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@NoArgsConstructor
@Table(name = "Audiences")
public class Audience extends User {
    @Column(nullable = false)
    @Getter @Setter
    private String displayName;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "AudiencesWantToSeeMedia", joinColumns = {@JoinColumn(name = "audienceID")}, inverseJoinColumns = {@JoinColumn(name = "mediaID")})
    @Getter @Setter
    private Set<Media> wantToSee = new HashSet<>();

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "AudiencesNotInterestedMedia", joinColumns = {@JoinColumn(name = "audienceID")}, inverseJoinColumns = {@JoinColumn(name = "mediaID")})
    @Getter @Setter
    private Set<Media> notInterested = new HashSet<>();

    @Column
    @Getter @Setter
    private String favoriteShow;
}