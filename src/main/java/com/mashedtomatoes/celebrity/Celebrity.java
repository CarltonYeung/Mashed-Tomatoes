package com.mashedtomatoes.celebrity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "Celebrities")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class Celebrity {

    private long id;
    private String name;
    private Date birthday;
    private String birthplace;
    private String biography;
    private String profileImage;

    public Celebrity() {
    }

    public String toString() {
        return "Celebrity(id=" + this.getId()
                + ", name=" + this.getName()
                + ", birthday=" + this.getBirthday()
                + ", birthplace=" + this.getBirthplace()
                + ", biography=" + this.getBiography()
                + ", profileImage=" + this.getProfileImage() + ")";
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonProperty("id")
    public long getId() {
        return id;
    }

    @Column(nullable = false)
    public String getName() {
        return name;
    }

    public Date getBirthday() {
        return birthday;
    }

    @JsonProperty("place_of_birth")
    public String getBirthplace() {
        return birthplace;
    }

    @Column(columnDefinition = "TEXT")
    public String getBiography() {
        return biography;
    }

    @JsonProperty("profile_path")
    public String getProfileImage() {
        return profileImage;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public void setBirthplace(String birthplace) {
        this.birthplace = birthplace;
    }

    public void setBiography(String biography) {
        this.biography = biography;
    }

    public void setProfileImage(String profileImage) {
        this.profileImage = profileImage;
    }
}
