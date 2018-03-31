package com.mashedtomatoes.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@Table(name = "Ratings")
public class Rating {
    @Id
    @GeneratedValue
    @Getter @Setter
    private long ID;

    @Column(nullable = false)
    @Getter @Setter
    private int score;

    @Column
    @Getter @Setter
    private String review = null;

    @Column(nullable = false)
    @Getter @Setter
    private long created;

    @Column(nullable = false)
    @Getter @Setter
    private long updated;

    /*
     * In the "Ratings" table, there will be a "mediaID" column.
     * Many Rating can reference the same Media and have the same value for the "mediaID" column.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "mediaID", nullable = false)
    @Getter @Setter
    private Media forMedia;
}
