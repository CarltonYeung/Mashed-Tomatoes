package com.mashedtomatoes.media;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.mashedtomatoes.celebrity.Character;
import com.mashedtomatoes.rating.Rating;
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
import org.apache.lucene.analysis.core.LowerCaseFilterFactory;
import org.apache.lucene.analysis.ngram.NGramFilterFactory;
import org.apache.lucene.analysis.snowball.SnowballPorterFilterFactory;
import org.apache.lucene.analysis.standard.StandardFilterFactory;
import org.apache.lucene.analysis.standard.StandardTokenizerFactory;
import org.hibernate.search.annotations.Analyzer;
import org.hibernate.search.annotations.AnalyzerDef;
import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.Indexed;
import org.hibernate.search.annotations.Parameter;
import org.hibernate.search.annotations.TokenFilterDef;
import org.hibernate.search.annotations.TokenizerDef;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "Media")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
@Indexed
@AnalyzerDef(
  name = "searchAnalyzer",
  tokenizer = @TokenizerDef(factory = StandardTokenizerFactory.class),
  filters = {
    @TokenFilterDef(factory = StandardFilterFactory.class),
    @TokenFilterDef(factory = LowerCaseFilterFactory.class),
    @TokenFilterDef(
      factory = SnowballPorterFilterFactory.class,
      params = {@Parameter(name = "language", value = "English")}
    ),
    @TokenFilterDef(
      factory = NGramFilterFactory.class,
      params = {
        @Parameter(name = "minGramSize", value = "1"),
        @Parameter(name = "maxGramSize", value = "3")
      }
    )
  }
)
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
  @Field
  @Analyzer(definition = "searchAnalyzer")
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
