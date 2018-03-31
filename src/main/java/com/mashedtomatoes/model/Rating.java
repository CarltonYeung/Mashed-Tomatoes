package com.mashedtomatoes.model;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
public class Rating {

    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false)
    private Integer score;

    @Column
    private String review;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="media_id", nullable = false)
    private Media media;

    @Column(nullable = false)
    private LocalDate updatedAt;

    public Rating() {}
}
