package com.mashedtomatoes.publication;

import javax.persistence.*;
import java.net.URL;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "Publishers")
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

    public long getID() {
        return this.ID;
    }

    public void setID(long ID) {
        this.ID = ID;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public URL getWebsite() {
        return this.website;
    }

    public void setWebsite(URL website) {
        this.website = website;
    }

    public Set<Publication> getPublications() {
        return this.publications;
    }

    public void setPublications(Set<Publication> publications) {
        this.publications = publications;
    }
}
