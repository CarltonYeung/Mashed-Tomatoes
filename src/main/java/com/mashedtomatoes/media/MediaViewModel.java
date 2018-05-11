package com.mashedtomatoes.media;

import com.mashedtomatoes.celebrity.Celebrity;
import com.mashedtomatoes.celebrity.Character;
import com.mashedtomatoes.rating.AudienceRating;
import com.mashedtomatoes.rating.CriticRating;
import com.mashedtomatoes.util.Util;

import java.util.*;
import java.util.stream.Collectors;
import org.thymeleaf.util.StringUtils;

public class MediaViewModel extends Media {
  private static final Integer COMMA_SEPARATED_VALUE_LIMIT = 3;

  private final Double averageCriticRating;
  private final Long totalCriticRating;
  private final Double averageAudienceRating;
  private final Long totalAudienceRating;
  private final Set<AudienceRating> audienceRatings;
  private final Set<CriticRating> criticRatings;

  public MediaViewModel(String fileUri, Integer smashThreshold, Media base) {
    super.setId(base.getId());
    super.setTitle(base.getTitle());
    super.setGenres(base.getGenres());
    super.setDescription(base.getDescription());
    super.setPosterPath(Util.resolveFilesUrl(fileUri,base.getPosterPath()));
    super.setCharacters(
        base.getCharacters()
            .stream()
            .sorted(Comparator.comparing(Character::getCastOrder))
            .collect(Collectors.toList()));
    for (Character character : super.getCharacters()) {
      Celebrity c = character.getCelebrity();
      character.getCelebrity().setProfilePath(Util.resolveFilesUrl(fileUri, c.getProfilePath()));
    }
    super.setProductionCompany(base.getProductionCompany());
    super.setPhotos(
        base.getPhotos()
            .stream()
            .map(photo -> Util.resolveFilesUrl(fileUri, photo))
            .collect(Collectors.toSet()));
    super.setRatings(base.getRatings());
    if (getRatings() == null || getRatings().isEmpty()) {
      criticRatings = new HashSet<>();
      audienceRatings = new HashSet<>();
      this.averageCriticRating = 0D;
      this.totalCriticRating = 0L;
      this.averageAudienceRating = 0D;
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
      OptionalDouble optionalAvgCriticRating = criticRatings.stream().mapToInt(CriticRating::getScore).average();
      if (optionalAvgCriticRating.isPresent()) {
        this.averageCriticRating = optionalAvgCriticRating.getAsDouble();
      } else {
        this.averageCriticRating = 0D;
      }


      this.totalCriticRating = (long) criticRatings.size();
      OptionalDouble optionalAvgAudienceRating =
          audienceRatings.stream().mapToInt(AudienceRating::getScore).average();
      if (optionalAvgAudienceRating.isPresent()) {
        this.averageAudienceRating = optionalAvgAudienceRating.getAsDouble();
      } else {
        this.averageAudienceRating = 0D;
      }

      this.totalAudienceRating = (long) audienceRatings.size();
    }
  }

  public Double getAverageCriticRating() {
    return averageCriticRating;
  }

  public Long getTotalCriticRating() {
    return totalCriticRating;
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
        .limit(COMMA_SEPARATED_VALUE_LIMIT)
        .collect(Collectors.joining(", "));
  }
}
