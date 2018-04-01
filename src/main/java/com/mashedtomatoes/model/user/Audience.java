package com.mashedtomatoes.model.user;

import com.mashedtomatoes.model.favorite.AudienceFavorite;
import com.mashedtomatoes.model.media.Media;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@NoArgsConstructor
@Table(name = "Audiences")
@Getter
@Setter
public class Audience extends User {

    @Column(nullable = false)
    private String displayName;

    @ManyToMany
    @JoinTable(name = "AudiencesWantToSeeMedia", joinColumns = {@JoinColumn(name = "audienceID")}, inverseJoinColumns = {@JoinColumn(name = "mediaID")})
    private Set<Media> wantToSee = new HashSet<>();

    @ManyToMany
    @JoinTable(name = "AudiencesNotInterestedMedia", joinColumns = {@JoinColumn(name = "audienceID")}, inverseJoinColumns = {@JoinColumn(name = "mediaID")})
    private Set<Media> notInterested = new HashSet<>();

    @OneToOne
    @PrimaryKeyJoinColumn(name = "favoritesID")
    private AudienceFavorite favorites;

    private boolean superReviewer = false;

    private boolean publicProfile = true;


}