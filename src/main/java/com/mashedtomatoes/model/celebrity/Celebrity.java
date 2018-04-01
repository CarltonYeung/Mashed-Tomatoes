package com.mashedtomatoes.model.celebrity;

import com.mashedtomatoes.model.media.Media;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.net.URL;
import java.util.HashSet;
import java.util.Set;

@Entity
@NoArgsConstructor
@Table(name = "Celebrities")
@Getter
@Setter
public class Celebrity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long ID;

    @Column(nullable = false)
    private String name;

    @Column(nullable = true)
    private long birthday;

    private String birthplace;

    private String biography;

    private URL profileImage;

    @ManyToMany
    @JoinTable(name = "MediaCelebrities", joinColumns = {@JoinColumn(name = "celebrityID")}, inverseJoinColumns = {@JoinColumn(name = "mediaID")})
    private Set<Media> media = new HashSet<>();

    @OneToMany(mappedBy = "playedBy", cascade = CascadeType.ALL)
    private Set<Character> characters = new HashSet<>();

    @OneToMany(mappedBy = "directedBy", cascade = CascadeType.ALL)
    protected Set<Media> directorOf = new HashSet<>();
}
