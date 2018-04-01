package com.mashedtomatoes.model.publication;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.net.URL;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "Publishers")
@Getter
@Setter
public class Publisher {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long ID;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private URL website;

    @OneToMany(mappedBy = "publisher", cascade = CascadeType.ALL)
    private Set<Publication> publications = new HashSet<>();
}
