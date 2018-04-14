package com.mashedtomatoes.user;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "CriticApplications")
public class CriticApplication {

    private long id;
    private Audience applicant;
    private String firstName;
    private String lastName;
    private String body;
    private Date applied;

    public CriticApplication() {
    }

    @Id
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @MapsId
    @OneToOne
    @JoinColumn(name = "applicantId")
    public Audience getApplicant() {
        return applicant;
    }

    public void setApplicant(Audience applicant) {
        this.applicant = applicant;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public Date getApplied() {
        return applied;
    }

    public void setApplied(Date applied) {
        this.applied = applied;
    }
}
