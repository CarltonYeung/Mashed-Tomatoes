package com.mashedtomatoes.media;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.mashedtomatoes.celebrity.Character;
import com.mashedtomatoes.rating.Rating;

import javax.persistence.*;
import java.util.Set;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "Media")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
@org.hibernate.annotations.DiscriminatorOptions(force=true)
public abstract class Media {
  protected long id;
  protected String title;
  protected String description;
  protected String posterPath;
  protected String productionCompany;
  protected Set<Character> characters;
  protected Set<Rating> ratings;
  protected Set<Genre> genres;

  public void setId(long id) {
    this.id = id;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public void setPosterPath(String posterPath) {
    this.posterPath = posterPath;
  }

  public void setProductionCompany(String productionCompany) {
    this.productionCompany = productionCompany;
  }

  public void setCharacters(Set<Character> characters) {
    this.characters = characters;
  }

  public void setRatings(Set<Rating> ratings) {
    this.ratings = ratings;
  }

  public void setGenres(Set<Genre> genres) {
    this.genres = genres;
  }

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @JsonProperty("id")
  public long getId() {
    return id;
  }

  @Column(nullable = false)
  public String getTitle() {
    return title;
  }

  @Column(columnDefinition = "TEXT")
  public String getDescription() {
    return description;
  }

  public String getPosterPath() {
    return posterPath;
  }

  public String getProductionCompany() {
    return productionCompany;
  }

  @OneToMany(mappedBy = "media", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
  public Set<Character> getCharacters() {
    return characters;
  }

  @OneToMany(mappedBy = "media", cascade = CascadeType.ALL)
  public Set<Rating> getRatings() {
    return ratings;
  }

  @ElementCollection(targetClass = Genre.class)
  @CollectionTable(
    name = "MediaGenres",
    joinColumns = {@JoinColumn(name = "mediaId")}
  )
  @Enumerated(EnumType.STRING)
  @Column(name = "genre", nullable = false, length = 32)
  public Set<Genre> getGenres() {
    return genres;
  }
}
