package com.mashedtomatoes.rating;

import java.net.URL;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "Publishers")
public class Publisher {
  private long id;
  private String name;
  private URL website;

  public Publisher() {}

  public void setId(long id) {
    this.id = id;
  }

  public void setName(String name) {
    this.name = name;
  }

  public void setWebsite(URL website) {
    this.website = website;
  }

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  public long getId() {
    return id;
  }

  @Column(nullable = false)
  public String getName() {
    return name;
  }

  @Column(nullable = false)
  public URL getWebsite() {
    return website;
  }
}
