package com.mashedtomatoes.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.Instant;

@Entity
@NoArgsConstructor
@Table(name = "Ratings")
@Getter @Setter
public class Rating {

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

    /*
     * In the "Ratings" table, there will be a "mediaID" column.
     * Many Rating can reference the same Media and have the same value for the "mediaID" column.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "mediaID", nullable = false)
    private Media forMedia;

    @PrePersist
    protected void onCreate() {
        this.created = Instant.now().getEpochSecond();
    }

    @PreUpdate
    protected void onUpdate() {
        this.updated = Instant.now().getEpochSecond();
    }
}
