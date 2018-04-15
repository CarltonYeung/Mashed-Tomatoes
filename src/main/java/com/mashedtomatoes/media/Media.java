package com.mashedtomatoes.media;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.mashedtomatoes.celebrity.Celebrity;
import com.mashedtomatoes.celebrity.Character;
import com.mashedtomatoes.rating.Rating;
import java.util.Date;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "Media")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public abstract class Media {
  protected long id;
  protected String title;
  protected String slug;
  protected String description;
  protected String posterPath;
  protected Date releaseDate;
  protected int runTime;
  protected Celebrity director;
  protected Celebrity producer;
  protected String productionCompany;
  protected Celebrity writer;
  protected Set<Character> characters;
  protected Set<Rating> ratings;
  protected Set<Genre> genres;

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @JsonProperty("id")
  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  @Column(nullable = false)
  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  @Column(nullable = false)
  public String getSlug() {
    return slug;
  }

  public void setSlug(String slug) {
    this.slug = slug;
  }

  @Column(columnDefinition = "TEXT")
  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public String getPosterPath() {
    return posterPath;
  }

  public void setPosterPath(String posterPath) {
    this.posterPath = posterPath;
  }

  public Date getReleaseDate() {
    return releaseDate;
  }

  public void setReleaseDate(Date releaseDate) {
    this.releaseDate = releaseDate;
  }

  public int getRunTime() {
    return runTime;
  }

  public void setRunTime(int runTime) {
    this.runTime = runTime;
  }

  @ManyToOne
  @JoinColumn(name = "directorId")
  public Celebrity getDirector() {
    return director;
  }

  public void setDirector(Celebrity director) {
    this.director = director;
  }

  @ManyToOne
  @JoinColumn(name = "producerId")
  public Celebrity getProducer() {
    return producer;
  }

  public void setProducer(Celebrity producer) {
    this.producer = producer;
  }

  public String getProductionCompany() {
    return productionCompany;
  }

  public void setProductionCompany(String productionCompany) {
    this.productionCompany = productionCompany;
  }

  @ManyToOne
  @JoinColumn(name = "writerId")
  public Celebrity getWriter() {
    return writer;
  }

  public void setWriter(Celebrity writer) {
    this.writer = writer;
  }

  @OneToMany(mappedBy = "media", cascade = CascadeType.ALL)
  public Set<Character> getCharacters() {
    return characters;
  }

  public void setCharacters(Set<Character> characters) {
    this.characters = characters;
  }

  @OneToMany(mappedBy = "media", cascade = CascadeType.ALL)
  public Set<Rating> getRatings() {
    return ratings;
  }

  public void setRatings(Set<Rating> ratings) {
    this.ratings = ratings;
  }

  @ElementCollection(targetClass = Genre.class)
  @CollectionTable(
    name = "MediaGenres",
    joinColumns = {@JoinColumn(name = "mediaID")}
  )
  @Enumerated(EnumType.STRING)
  @Column(name = "genre", nullable = false, length = 32)
  public Set<Genre> getGenres() {
    return genres;
  }

  public void setGenres(Set<Genre> genres) {
    this.genres = genres;
  }
}
