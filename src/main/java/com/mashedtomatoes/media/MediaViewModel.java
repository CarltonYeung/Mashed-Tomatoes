package com.mashedtomatoes.media;

import com.mashedtomatoes.celebrity.Celebrity;
import com.mashedtomatoes.celebrity.Character;
import com.mashedtomatoes.rating.AudienceRating;
import com.mashedtomatoes.rating.CriticRating;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;
import org.thymeleaf.util.StringUtils;

public class MediaViewModel extends Media {
  private final Double averageCriticRating;
  private final Long totalCriticRating;
  private final Long smashCount;
  private final Long passCount;
  private final Double averageAudienceRating;
  private final Long totalAudienceRating;
  private final Set<AudienceRating> audienceRatings;
  private final Set<CriticRating> criticRatings;

  public MediaViewModel(String fileUri, Integer smashThreshold, Media base) {
    super.setId(base.getId());
    super.setTitle(base.getTitle());
    super.setGenres(base.getGenres());
    super.setDescription(base.getDescription());
    super.setPosterPath(fileUri + base.getPosterPath());
    super.setCharacters(base.getCharacters());
    for (Character character : super.getCharacters()) {
      Celebrity c = character.getCelebrity();
      character.getCelebrity().setProfilePath(String.format("%s%s", fileUri, c.getProfilePath()));
    }
    super.setProductionCompany(super.getProductionCompany());

    if (getRatings() == null || getRatings().isEmpty()) {
      criticRatings = new HashSet<>();
      audienceRatings = new HashSet<>();
      this.averageCriticRating = 0.0;
      this.totalCriticRating = 0L;
      this.smashCount = 0L;
      this.passCount = 0L;
      this.averageAudienceRating = 0.0;
      this.totalAudienceRating = 0L;
    } else {
      criticRatings =
          getRatings()
              .stream()
              .filter(rating -> rating instanceof CriticRating)
              .map(rating -> (CriticRating) rating)
              .collect(Collectors.toSet());
      audienceRatings =
          getRatings()
              .stream()
              .filter(rating -> rating instanceof AudienceRating)
              .map(rating -> (AudienceRating) rating)
              .collect(Collectors.toSet());
      this.averageCriticRating =
          criticRatings.stream().mapToInt(CriticRating::getScore).average().getAsDouble();
      this.totalCriticRating = new Long(criticRatings.size());
      this.smashCount =
          criticRatings
              .stream()
              .filter(criticRating -> criticRating.getScore() > smashThreshold)
              .count();
      this.passCount = this.totalCriticRating - this.smashCount;
      this.averageAudienceRating =
          audienceRatings.stream().mapToInt(AudienceRating::getScore).average().getAsDouble();
      this.totalAudienceRating = new Long(audienceRatings.size());
    }
  }

  public Double getAverageCriticRating() {
    return averageCriticRating;
  }

  public Long getTotalCriticRating() {
    return totalCriticRating;
  }

  public Long getSmashCount() {
    return smashCount;
  }

  public Long getPassCount() {
    return passCount;
  }

  public Double getAverageAudienceRating() {
    return averageAudienceRating;
  }

  public Long getTotalAudienceRating() {
    return totalAudienceRating;
  }

  public Set<AudienceRating> getAudienceRatings() {
    return audienceRatings;
  }

  public Set<CriticRating> getCriticRatings() {
    return criticRatings;
  }

  public String getCommaSeperatedGenres() {
    return getGenres()
        .stream()
        .map(g -> StringUtils.capitalize(g.toString().toLowerCase()))
        .collect(Collectors.joining(","));
  }
}
