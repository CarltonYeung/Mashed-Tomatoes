package com.mashedtomatoes.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.Instant;

@Entity
@NoArgsConstructor
@Table(name = "Ratings")
public class Rating {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter @Setter
    private long ID;

    @Column(nullable = false)
    @Getter @Setter
    private int score;

    @Column(nullable = false)
    @Getter @Setter
    private long created;

    @Column(nullable = false)
    @Getter @Setter
    private long updated;

    @Column
    @Getter @Setter
    private String review = null;

    /*
     * In the "Ratings" table, there will be a "mediaID" column.
     * Many Rating can reference the same Media and have the same value for the "mediaID" column.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "mediaID", nullable = false)
    @Getter @Setter
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
