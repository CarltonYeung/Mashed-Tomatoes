package com.mashedtomatoes.rating;

import java.net.URL;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "CriticRatings")
public class CriticRating extends Rating {
  private String title;
  private URL link;

  public CriticRating() {}

  public void setTitle(String title) {
    this.title = title;
  }

  public void setLink(URL link) {
    this.link = link;
  }

  public String getTitle() {
    return title;
  }

  public URL getLink() {
    return link;
  }
}
