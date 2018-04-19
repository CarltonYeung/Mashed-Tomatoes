package com.mashedtomatoes.media;

import com.mashedtomatoes.celebrity.Celebrity;
import java.util.Date;
import java.util.Set;

public class TVShowViewModel extends MediaViewModel {
  private final Date startDate;
  private final Date endDate;
  private final int episodeRunTime;
  private final int seasons;
  private final int episodes;
  private final String network;
  private final Celebrity creator;
  private final Set<Date> airDates;

  public TVShowViewModel(String fileUri, Integer smashThreshold, TVShow base) {
    super(fileUri, smashThreshold, base);
    this.startDate = base.getStartDate();
    this.endDate = base.getEndDate();
    this.episodeRunTime = base.getEpisodeRunTime();
    this.seasons = base.getSeasons();
    this.episodes = base.getEpisodes();
    this.network = base.getNetwork();
    this.airDates = base.getAirDates();
	  if (base.getCreator() != null) {
		  base.getCreator()
				  .setProfilePath(String.format("%s%s", fileUri, base.getCreator().getProfilePath()));
	  }
	  this.creator = base.getCreator();
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

  public Celebrity getCreator() {
    return creator;
  }

  public Set<Date> getAirDates() {
    return airDates;
  }
}
