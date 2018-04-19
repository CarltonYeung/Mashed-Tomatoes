package com.mashedtomatoes.media;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.mashedtomatoes.celebrity.Character;
import com.mashedtomatoes.rating.Rating;
import java.util.List;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "Media")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public abstract class Media {
  protected long id;
  protected String title;
  protected String description;
  protected String posterPath;
  protected String productionCompany;
  protected List<Character> characters;
  protected Set<Rating> ratings;
  protected Set<Genre> genres;
  protected Set<String> photos;

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

  public void setCharacters(List<Character> characters) {
    this.characters = characters;
  }

  public void setRatings(Set<Rating> ratings) {
    this.ratings = ratings;
  }

  public void setGenres(Set<Genre> genres) {
    this.genres = genres;
  }

  public void setPhotos(Set<String> photos) {
    this.photos = photos;
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
  public List<Character> getCharacters() {
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

  @ElementCollection(targetClass = String.class)
  @CollectionTable(
    name = "MediaPhotos",
    joinColumns = {@JoinColumn(name = "mediaId")}
  )
  @Column(name = "photo", nullable = false)
  public Set<String> getPhotos() {
    return photos;
  }
}
