package com.mashedtomatoes.media;

import com.mashedtomatoes.celebrity.Celebrity;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "TVShows")
public class TVShow extends Media {
  private Date startDate;
  private Date endDate;
  private int episodeRunTime;
  private int seasons;
  private int episodes;
  private String network;
  private Celebrity creator;
  private Set<Date> airDates;

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

  public void setAirDates(Set<Date> airDates) {
    this.airDates = airDates;
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

  @ElementCollection(targetClass = Date.class, fetch = FetchType.EAGER)
  @CollectionTable(
    name = "TVShowAirDates",
    joinColumns = {@JoinColumn(name = "mediaId")}
  )
  @Column(name = "airDate", nullable = false)
  public Set<Date> getAirDates() {
    return airDates;
  }
}
