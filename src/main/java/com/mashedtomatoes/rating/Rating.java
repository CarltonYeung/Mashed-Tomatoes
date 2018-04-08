package com.mashedtomatoes.rating;

import com.mashedtomatoes.media.Media;
import com.mashedtomatoes.user.User;

import javax.persistence.*;
import java.time.Instant;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "Ratings")
public abstract class Rating {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long ID;

    @Column(nullable = false)
    private int score;

    @Column(nullable = false, updatable = false)
    private long created;

    @Column(nullable = false)
    private long updated;

    private String review = null;

    @ManyToOne
    @JoinColumn(name = "mediaID", nullable = false)
    private Media forMedia;

    @ManyToOne
    @JoinColumn(name = "authorID", nullable = false)
    private User author;

    @PrePersist
    protected void onCreate() {
        this.created = Instant.now().getEpochSecond();
    }

    @PreUpdate
    protected void onUpdate() {
        this.updated = Instant.now().getEpochSecond();
    }

    public long getID() {
        return ID;
    }

    public void setID(long ID) {
        this.ID = ID;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public long getCreated() {
        return created;
    }

    public void setCreated(long created) {
        this.created = created;
    }

    public long getUpdated() {
        return updated;
    }

    public void setUpdated(long updated) {
        this.updated = updated;
    }

    public String getReview() {
        return review;
    }

    public void setReview(String review) {
        this.review = review;
    }

    public Media getForMedia() {
        return forMedia;
    }

    public void setForMedia(Media forMedia) {
        this.forMedia = forMedia;
    }

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }
}
