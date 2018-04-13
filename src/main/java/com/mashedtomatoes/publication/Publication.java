package com.mashedtomatoes.publication;

import com.mashedtomatoes.user.User;

import javax.persistence.*;
import java.net.URL;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "Publications")
public abstract class Publication {

    private long ID;
    private Publisher publisher;
    private User author;
    private String title;
    private URL link;
    private long created;
    private long updated;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public long getID() {
        return ID;
    }

    @ManyToOne
    @JoinColumn(name = "publisherID", nullable = false)
    public Publisher getPublisher() {
        return publisher;
    }

    @ManyToOne
    @JoinColumn(name = "authorID", nullable = false)
    public User getAuthor() {
        return author;
    }

    @Column(nullable = false)
    public String getTitle() {
        return title;
    }

    @Column(nullable = false)
    public URL getLink() {
        return link;
    }

    @Column(nullable = false)
    public long getCreated() {
        return created;
    }

    @Column(nullable = false)
    public long getUpdated() {
        return updated;
    }

    public void setID(long ID) {
        this.ID = ID;
    }

    public void setPublisher(Publisher publisher) {
        this.publisher = publisher;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setLink(URL link) {
        this.link = link;
    }

    public void setCreated(long created) {
        this.created = created;
    }

    public void setUpdated(long updated) {
        this.updated = updated;
    }
}
