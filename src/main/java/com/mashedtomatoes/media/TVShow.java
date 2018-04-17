package com.mashedtomatoes.media;

import org.hibernate.search.annotations.Indexed;

import java.util.Date;
import java.util.HashSet;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "TVShows")
@Indexed
public class TVShow extends Media {
	private Date endDate;
	private Integer seasons;
	private Integer episodes;

  public TVShow() {
    super.ratings = new HashSet<>();
    super.genres = new HashSet<>();
  }

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public Integer getSeasons() {
		return seasons;
	}

	public void setSeasons(Integer seasons) {
		this.seasons = seasons;
	}

	public Integer getEpisodes() {
		return episodes;
	}

	public void setEpisodes(Integer episodes) {
		this.episodes = episodes;
	}
}
