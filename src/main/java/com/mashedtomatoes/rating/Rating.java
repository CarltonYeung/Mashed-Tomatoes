package com.mashedtomatoes.rating;

import com.mashedtomatoes.media.Media;
import com.mashedtomatoes.user.User;

import javax.persistence.*;
import java.time.Instant;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "Ratings")
public abstract class Rating {

    private long ID;
    private int score;
    private long created;
    private long updated;
    private String review = null;
    private Media forMedia;
    private User author;

    @PrePersist
    protected void onCreate() {
        this.created = Instant.now().getEpochSecond();
    }

    @PreUpdate
    protected void onUpdate() {
        this.updated = Instant.now().getEpochSecond();
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public long getID() {
        return ID;
    }

    @Column(nullable = false)
    public int getScore() {
        return score;
    }

    @Column(nullable = false, updatable = false)
    public long getCreated() {
        return created;
    }

    @Column(nullable = false)
    public long getUpdated() {
        return updated;
    }

    public String getReview() {
        return review;
    }

    @ManyToOne
    @JoinColumn(name = "mediaID", nullable = false)
    public Media getForMedia() {
        return forMedia;
    }

    @ManyToOne
    @JoinColumn(name = "authorID", nullable = false)
    public User getAuthor() {
        return author;
    }

    public void setID(long ID) {
        this.ID = ID;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public void setCreated(long created) {
        this.created = created;
    }

    public void setUpdated(long updated) {
        this.updated = updated;
    }

    public void setReview(String review) {
        this.review = review;
    }

    public void setForMedia(Media forMedia) {
        this.forMedia = forMedia;
    }

    public void setAuthor(User author) {
        this.author = author;
    }
}
