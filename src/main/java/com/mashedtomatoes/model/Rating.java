package com.mashedtomatoes.model;

import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Date;

@Entity
public class Rating {

    @Id
    @GeneratedValue
    @Column
    private Long Id;

    /*
    @Column
    private Integer rating;
    */

    /*
    @Column
    private String review;
    */

    /*
    @Embedded
    @Column
    private User author;

    @Embedded
    @Column
    private Media target;
*/

    @DateTimeFormat(pattern = "dd.MM.yy")
    @Temporal(value=TemporalType.TIMESTAMP)
    private LocalDateTime submitted;

    /*
    @Column
    private String linkToCriticReview;
    */

    public Rating() {}

}
