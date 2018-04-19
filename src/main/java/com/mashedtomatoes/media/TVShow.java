package com.mashedtomatoes.media;

import com.mashedtomatoes.celebrity.Celebrity;
import java.util.Date;
import java.util.HashSet;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import org.hibernate.search.annotations.Indexed;

@Entity
@Table(name = "TVShows")
@Indexed
public class TVShow extends Media {
  private Date startDate;
  private Date endDate;
  private int episodeRunTime;
  private int seasons;
  private int episodes;
  private String network;
  private Celebrity creator;

  public TVShow() {
    super.ratings = new HashSet<>();
    super.genres = new HashSet<>();
  }

  public void setStartDate(Date startDate) {
    this.startDate = startDate;
  }

  public void setEndDate(Date endDate) {
    this.endDate = endDate;
  }

  public void setEpisodeRunTime(int episodeRunTime) {
    this.episodeRunTime = episodeRunTime;
  }

  public void setSeasons(int seasons) {
    this.seasons = seasons;
  }

  public void setEpisodes(int episodes) {
    this.episodes = episodes;
  }

  public void setNetwork(String network) {
    this.network = network;
  }

  public void setCreator(Celebrity creator) {
    this.creator = creator;
  }

  public Date getStartDate() {
    return startDate;
  }

  public Date getEndDate() {
    return endDate;
  }

  public int getEpisodeRunTime() {
    return episodeRunTime;
  }

  public int getSeasons() {
    return seasons;
  }

  public int getEpisodes() {
    return episodes;
  }

  public String getNetwork() {
    return network;
  }

  @ManyToOne
  @JoinColumn(name = "creatorId")
  public Celebrity getCreator() {
    return creator;
  }
}
