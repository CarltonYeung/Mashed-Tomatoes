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
@Getter @Setter
public class Audience extends User {

    @Column(nullable = false)
    private String displayName;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "AudiencesWantToSeeMedia", joinColumns = {@JoinColumn(name = "audienceID")}, inverseJoinColumns = {@JoinColumn(name = "mediaID")})
    private Set<Media> wantToSee = new HashSet<>();

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "AudiencesNotInterestedMedia", joinColumns = {@JoinColumn(name = "audienceID")}, inverseJoinColumns = {@JoinColumn(name = "mediaID")})
    private Set<Media> notInterested = new HashSet<>();

    private String favoriteShow;
}