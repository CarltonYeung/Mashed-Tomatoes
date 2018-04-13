package com.mashedtomatoes.user;

import com.mashedtomatoes.publication.Publisher;

import javax.persistence.*;

@Entity
@Table(name = "Critics")
public class Critic extends User {

    private String firstName;
    private String lastName;
    private Publisher publisher;
    private boolean topCritic;
    private String slug;

    /**
     * Hibernate needs default constructor for entities.
     */
    public Critic() {}

    public Critic(String firstName, String lastName) {
        super(UserType.CRITIC);
        this.firstName = firstName;
        this.lastName = lastName;
        this.topCritic = false;
    }

    @Column(nullable = false)
    public String getFirstName() {
        return firstName;
    }

    @Column(nullable = false)
    public String getLastName() {
        return lastName;
    }

    @ManyToOne
    @JoinColumn(name = "publisherID", nullable = false)
    public Publisher getPublisher() {
        return publisher;
    }

    @Column(nullable = false, columnDefinition = "TINYINT(1)")
    public boolean isTopCritic() {
        return topCritic;
    }

    public String getSlug() {
        return slug;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setPublisher(Publisher publisher) {
        this.publisher = publisher;
    }

    public void setTopCritic(boolean topCritic) {
        this.topCritic = topCritic;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }
}