package com.mashedtomatoes.publication;

import javax.persistence.*;
import java.net.URL;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "Publishers")
public class Publisher {

    private long ID;
    private String name;
    private URL website;
    private Set<Publication> publications = new HashSet<>();

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public long getID() {
        return ID;
    }

    @Column(nullable = false)
    public String getName() {
        return name;
    }

    @Column(nullable = false)
    public URL getWebsite() {
        return website;
    }

    @OneToMany(mappedBy = "publisher", cascade = CascadeType.ALL)
    public Set<Publication> getPublications() {
        return publications;
    }

    public void setID(long ID) {
        this.ID = ID;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setWebsite(URL website) {
        this.website = website;
    }

    public void setPublications(Set<Publication> publications) {
        this.publications = publications;
    }
}
