package com.mashedtomatoes.model.user;

import com.mashedtomatoes.model.publication.Publisher;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@Table(name = "Critics")
@Getter
@Setter
public class Critic extends User {

    @Column(nullable = false)
    private String firstName;

    @Column(nullable = false)
    private String lastName;

    @ManyToOne
    @JoinColumn(name = "publisherID", nullable = false)
    private Publisher publisher;

    private boolean topCritic;
}