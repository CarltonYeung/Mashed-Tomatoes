package com.mashedtomatoes.rating;

import com.mashedtomatoes.publication.Publisher;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.net.URL;

@Entity
@Table(name = "CriticRatings")
public class CriticRating extends Rating {

    private Publisher publisher;
    private String title;
    private URL link;

    public CriticRating() {
    }

    @ManyToOne
    @JoinColumn(name = "publisherId")
    public Publisher getPublisher() {
        return publisher;
    }

    public void setPublisher(Publisher publisher) {
        this.publisher = publisher;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public URL getLink() {
        return link;
    }

    public void setLink(URL link) {
        this.link = link;
    }
}
