package com.mashedtomatoes.user;

import com.mashedtomatoes.publication.Publisher;

import javax.persistence.*;

@Entity
@Table(name = "Critics")
public class Critic extends User {

    @Column(nullable = false)
    private String firstName;

    @Column(nullable = false)
    private String lastName;

    @ManyToOne
    @JoinColumn(name = "publisherID", nullable = false)
    private Publisher publisher;

    @Column(nullable = false, columnDefinition = "TINYINT(1)")
    private boolean topCritic;

    private String slug;

    public Critic(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.topCritic = false;
    }

    public Critic() {
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Publisher getPublisher() {
        return publisher;
    }

    public void setPublisher(Publisher publisher) {
        this.publisher = publisher;
    }

    public boolean isTopCritic() {
        return topCritic;
    }

    public void setTopCritic(boolean topCritic) {
        this.topCritic = topCritic;
    }
}